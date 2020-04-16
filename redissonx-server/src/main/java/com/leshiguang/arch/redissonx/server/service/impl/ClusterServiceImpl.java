package com.leshiguang.arch.redissonx.server.service.impl;

import com.leshiguang.arch.redissonx.core.entity.gen.*;
import com.leshiguang.arch.redissonx.core.mapper.gen.ClusterMapper;
import com.leshiguang.arch.redissonx.core.mapper.gen.ConnectMapper;
import com.leshiguang.arch.redissonx.server.domain.cluster.ClusterVO;
import com.leshiguang.arch.redissonx.server.domain.request.ClusterQueryRequest;
import com.leshiguang.arch.redissonx.server.service.ClusterService;
import com.leshiguang.redissonx.common.base.RedissonxPaging;
import com.leshiguang.redissonx.common.base.RedissonxResponse;
import com.leshiguang.redissonx.common.base.RedissonxResponseBuilder;
import com.leshiguang.redissonx.common.base.RedissonxTable;
import com.leshiguang.redissonx.common.entity.category.CategoryBO;
import com.leshiguang.redissonx.common.entity.cluster.ClusterBO;
import com.leshiguang.redissonx.common.entity.connect.ConnectBO;
import com.leshiguang.redissonx.common.zookeeper.ZookeeperClient;
import com.leshiguang.redissonx.common.zookeeper.ZookeeperClientImpl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-19 13:27
 * @Modify
 */
@Service
public class ClusterServiceImpl implements ClusterService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ClusterServiceImpl.class);
    @Resource
    private ClusterMapper clusterMapper;
    @Resource
    private ConnectMapper connectMapper;
    private ZookeeperClient zookeeperClient = new ZookeeperClientImpl();

    private RedissonxResponse validatorCluster(ClusterBO cluster) {
        try {
            Validate.notBlank(cluster.getClusterName(), "集群名不能为空");
        } catch (IllegalArgumentException iae) {
            return RedissonxResponseBuilder.fail(604, iae.getMessage());
        } catch (NullPointerException npe) {
            return RedissonxResponseBuilder.fail(605, "空指针异常");
        }
        return null;
    }

    @Override
    public RedissonxResponse<RedissonxTable<ClusterVO>> query(ClusterQueryRequest request, RedissonxPaging paging) {
        ClusterCondition condition = new ClusterCondition();
        condition.setMysqlOffset((paging.getPageIndex() - 1) * paging.getPageSize());
        condition.setMysqlLength(paging.getPageSize());
        buildCriteria(condition.createCriteria(), request);
        List<Cluster> clusterList = clusterMapper.selectByCondition(condition);
        long counts = clusterMapper.countByCondition(condition);
        List<ClusterVO> resultList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(clusterList)) {
            for (Cluster cluster : clusterList) {
                ClusterVO clusterVO = toVO(cluster);
                resultList.add(clusterVO);
            }
        }
        RedissonxTable<ClusterVO> result = new RedissonxTable<>(paging.getPageIndex(), paging.getPageSize(), resultList, counts);
        return RedissonxResponseBuilder.success(result);
    }

    @Override
    public RedissonxResponse<ClusterVO> load(String clusterName) {
        ClusterCondition condition = new ClusterCondition();
        condition.createCriteria().andClusterNameEqualTo(clusterName);
        Cluster cluster = clusterMapper.selectOneByCondition(condition);
        if (null != cluster) {
            ClusterVO clusterVO = toVO(cluster);
            return RedissonxResponseBuilder.success(clusterVO);
        } else {
            return RedissonxResponseBuilder.success(null);
        }
    }

    @Override
    public RedissonxResponse<Boolean> save(ClusterVO cluster, String operator) {
        ClusterCondition condition = new ClusterCondition();
        condition.createCriteria().andClusterNameEqualTo(cluster.getClusterName());
        Cluster existCluster = clusterMapper.selectOneByCondition(condition);
        Boolean result = false;
        Cluster operationCluster = toDBEntity(cluster);
        if (null == existCluster) {
            operationCluster.setCreateTime(new Date());
            operationCluster.setUpdateTime(new Date());
            operationCluster.setStatus(1);
            int insertCount = clusterMapper.insertSelective(operationCluster);
            result = insertCount > 0;
        } else {
            operationCluster.setUpdateTime(new Date());
            operationCluster.setCreateTime(existCluster.getCreateTime());
            operationCluster.setId(existCluster.getId());
            int updateCount = clusterMapper.updateByIdSelective(operationCluster);
            result = updateCount > 0;
        }
        return RedissonxResponseBuilder.success(result);
    }

    @Override
    public RedissonxResponse<Boolean> delete(String clusterName, String operator) {
        ClusterCondition condition = new ClusterCondition();
        condition.createCriteria().andClusterNameEqualTo(clusterName);
        Cluster statusEntity = new Cluster();
        statusEntity.setStatus(4);
        statusEntity.setUpdateTime(new Date());
        int updateCount = clusterMapper.updateByConditionSelective(statusEntity, condition);
        boolean updateStatus = updateCount > 0;
        return RedissonxResponseBuilder.success(updateStatus);
    }

    @Override
    public RedissonxResponse<Boolean> publish(String clusterName, String operator) {
        Boolean result = false;
        try {
            ClusterCondition condition = new ClusterCondition();
            condition.createCriteria().andClusterNameEqualTo(clusterName);
            Cluster statusEntity = new Cluster();
            statusEntity.setStatus(2);
            statusEntity.setUpdateTime(new Date());
            int updateCount = clusterMapper.updateByConditionSelective(statusEntity, condition);
            boolean updateStatus = updateCount > 0;
            if (updateStatus) {
                Cluster newestEntity = clusterMapper.selectOneByCondition(condition);
                ClusterBO toZkEntity = toBO(newestEntity);
                boolean setResult = zookeeperClient.setCluster(toZkEntity);
                if (!setResult) {
                    //rollback
                    result = false;
                }
                result = setResult;
            } else {
                result = false;
            }
        } catch (Exception e) {
            LOGGER.error("exception for publish cluster", e);
            result = false;
        }
        return RedissonxResponseBuilder.success(result);
    }

    @Override
    public RedissonxResponse<Boolean> offline(String clusterName, String operator) {
        Boolean result = false;
        try {
            //同步到zk
            //修改状态
            ClusterCondition condition = new ClusterCondition();
            condition.createCriteria().andClusterNameEqualTo(clusterName);
            Cluster statusEntity = new Cluster();
            statusEntity.setStatus(3);
            statusEntity.setUpdateTime(new Date());
            int updateCount = clusterMapper.updateByConditionSelective(statusEntity, condition);
            boolean updateStatus = updateCount > 0;
            if (updateStatus) {
                Cluster newestEntity = clusterMapper.selectOneByCondition(condition);
                ClusterBO toZkEntity = toBO(newestEntity);
                boolean setResult = zookeeperClient.deleteCluster(clusterName);
                if (!setResult) {
                    //rollback
                    result = false;
                }
                result = setResult;
            } else {
                result = false;
            }
        } catch (Exception e) {
            LOGGER.error("exception for offline cluster", e);
            result = false;
        }
        return RedissonxResponseBuilder.success(result);
    }


    private void buildCriteria(ClusterCondition.Criteria criteria, ClusterQueryRequest request) {
        if (StringUtils.isNotEmpty(request.getUserId())) {
            criteria.andOwnerLike("%" + request.getUserId() + "%");
            criteria.andMemberLike("%" + request.getUserId() + "%");
        }
        if (StringUtils.isNotEmpty(request.getKeyword())) {
            criteria.andClusterNameLike("%" + request.getKeyword() + "%");
        }
        if (StringUtils.isNotEmpty(request.getMode())) {
            criteria.andModeEqualTo(request.getMode());
        }
        if (StringUtils.isNotEmpty(request.getTenant())) {
            criteria.andTenantListLike("%" + request.getTenant() + "%");
        }
        if (StringUtils.isNotEmpty(request.getApplication())) {
            criteria.andApplicationListLike("%" + request.getApplication() + "%");
        }
    }

    private ClusterBO toBO(Cluster source) {
        ClusterBO target = new ClusterBO();
        if (StringUtils.isNotEmpty(source.getApplicationList())) {
            target.setApplicationList(new ArrayList<>(Arrays.asList(StringUtils.split(source.getApplicationList(), ","))));
        }
        if (StringUtils.isNotEmpty(source.getTenantList())) {
            target.setTenantList(new ArrayList<>(Arrays.asList(StringUtils.split(source.getTenantList(), ","))));
        }
        target.setClusterName(source.getClusterName());
        String connectName = source.getConnectName();
        if (StringUtils.isNotEmpty(connectName)) {
            ConnectCondition connectCondition = new ConnectCondition();
            connectCondition.createCriteria().andConnectNameEqualTo(connectName);
            Connect connect = connectMapper.selectOneByCondition(connectCondition);
            if (null != connect) {
                ConnectBO connectBO = ConnectServiceImpl.toBO(connect);
                target.setConnect(connectBO);
            }
        }
        target.setDatabase(source.getDs());
        target.setMode(source.getMode());
        return target;
    }

    private ClusterVO toVO(Cluster source) {
        ClusterVO target = new ClusterVO();
        target.setCreateTime(source.getCreateTime());
        target.setUpdateTime(source.getUpdateTime());
        if (StringUtils.isNotEmpty(source.getMember())) {
            target.setMemberList(Arrays.asList(StringUtils.split(source.getMember(), ",")));
        }
        if (StringUtils.isNotEmpty(source.getOwner())) {
            target.setOwnerList(Arrays.asList(StringUtils.split(source.getOwner(), ",")));
        }
        target.setStatus(source.getStatus());
        if (StringUtils.isNotEmpty(source.getApplicationList())) {
            target.setApplicationList(Arrays.asList(StringUtils.split(source.getApplicationList(), ",")));
        }
        if (StringUtils.isNotEmpty(source.getTenantList())) {
            target.setTenantList(Arrays.asList(StringUtils.split(source.getTenantList(), ",")));
        }
        target.setClusterName(source.getClusterName());
        target.setConnectName(source.getConnectName());
        target.setDatabase(source.getDs());
        target.setMode(source.getMode());
        return target;
    }

    private Cluster toDBEntity(ClusterVO source) {
        Cluster target = new Cluster();
        target.setCreateTime(source.getCreateTime());
        target.setUpdateTime(source.getUpdateTime());
        if (CollectionUtils.isNotEmpty(source.getMemberList())) {
            target.setMember(StringUtils.join(source.getMemberList(), ","));
        }
        if (CollectionUtils.isNotEmpty(source.getOwnerList())) {
            target.setOwner(StringUtils.join(source.getOwnerList(), ","));
        }
        target.setStatus(source.getStatus());
        if (CollectionUtils.isNotEmpty(source.getApplicationList())) {
            target.setApplicationList(StringUtils.join(source.getApplicationList(), ","));
        }
        if (CollectionUtils.isNotEmpty(source.getTenantList())) {
            target.setTenantList(StringUtils.join(source.getTenantList(), ","));
        }
        target.setClusterName(source.getClusterName());
        target.setConnectName(source.getConnectName());
        target.setDs(source.getDatabase());
        target.setMode(source.getMode());
        return target;
    }
}
