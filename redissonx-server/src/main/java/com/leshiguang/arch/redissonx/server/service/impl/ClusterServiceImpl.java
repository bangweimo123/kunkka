package com.leshiguang.arch.redissonx.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.leshiguang.arch.redissonx.core.entity.gen.*;
import com.leshiguang.arch.redissonx.core.mapper.gen.CategoryMapper;
import com.leshiguang.arch.redissonx.core.mapper.gen.ClusterConnectMappingMapper;
import com.leshiguang.arch.redissonx.core.mapper.gen.ClusterMapper;
import com.leshiguang.arch.redissonx.core.mapper.gen.ConnectMapper;
import com.leshiguang.arch.redissonx.server.domain.cluster.ClusterConnectVO;
import com.leshiguang.arch.redissonx.server.domain.cluster.ClusterPublishOption;
import com.leshiguang.arch.redissonx.server.domain.cluster.ClusterVO;
import com.leshiguang.arch.redissonx.server.domain.request.ClusterQueryRequest;
import com.leshiguang.arch.redissonx.server.service.ClusterService;
import com.leshiguang.redissonx.common.base.RedissonxPaging;
import com.leshiguang.redissonx.common.base.RedissonxResponse;
import com.leshiguang.redissonx.common.base.RedissonxResponseBuilder;
import com.leshiguang.redissonx.common.base.RedissonxTable;
import com.leshiguang.redissonx.common.entity.cluster.ClusterAuthStrategyBO;
import com.leshiguang.redissonx.common.entity.cluster.ClusterBO;
import com.leshiguang.redissonx.common.entity.cluster.ClusterConnectBO;
import com.leshiguang.redissonx.common.entity.cluster.ClusterSimpleBO;
import com.leshiguang.redissonx.common.enums.StrategySource;
import com.leshiguang.redissonx.common.zookeeper.ZookeeperClient;
import com.leshiguang.redissonx.common.zookeeper.ZookeeperClientFactory;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

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
    @Resource
    private CategoryMapper categoryMapper;
    @Resource
    private ClusterConnectMappingMapper clusterConnectMappingMapper;

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
            ClusterConnectMappingCondition mappingCondition = new ClusterConnectMappingCondition();
            mappingCondition.createCriteria().andClusterNameEqualTo(clusterName);
            List<ClusterConnectMapping> mappingList = clusterConnectMappingMapper.selectByCondition(mappingCondition);
            if (CollectionUtils.isNotEmpty(mappingList)) {
                List<ClusterConnectVO> connectList = new ArrayList<>();
                for (ClusterConnectMapping mapping : mappingList) {
                    ClusterConnectVO clusterConnect = new ClusterConnectVO();
                    clusterConnect.setRegion(mapping.getRegion());
                    clusterConnect.setDatabase(mapping.getDs());
                    clusterConnect.setConnectName(mapping.getConnectName());
                    connectList.add(clusterConnect);
                }
                clusterVO.setConnectList(connectList);
            }
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
    public RedissonxResponse<Boolean> saveConnects(ClusterVO cluster, String operator) {
        //批量添加mapping关系
        if (CollectionUtils.isNotEmpty(cluster.getConnectList())) {
            ClusterConnectMappingCondition mappingCondition = new ClusterConnectMappingCondition();
            mappingCondition.createCriteria().andClusterNameEqualTo(cluster.getClusterName());
            clusterConnectMappingMapper.deleteByCondition(mappingCondition);
            List<ClusterConnectMapping> mappingList = new ArrayList<>();
            for (ClusterConnectVO clusterConnect : cluster.getConnectList()) {
                ClusterConnectMapping clusterConnectMapping = new ClusterConnectMapping();
                clusterConnectMapping.setDs(clusterConnect.getDatabase());
                clusterConnectMapping.setClusterName(cluster.getClusterName());
                clusterConnectMapping.setConnectName(clusterConnect.getConnectName());
                clusterConnectMapping.setRegion(clusterConnect.getRegion());
                mappingList.add(clusterConnectMapping);
            }
            clusterConnectMappingMapper.batchInsert(mappingList);
        }
        return RedissonxResponseBuilder.success(true);
    }

    @Override
    public RedissonxResponse<Boolean> saveAuths(ClusterVO cluster, String operator) {
        ClusterCondition condition = new ClusterCondition();
        condition.createCriteria().andClusterNameEqualTo(cluster.getClusterName());
        Cluster existCluster = clusterMapper.selectOneByCondition(condition);
        Boolean result = false;
        Cluster operationCluster = toDBAuthEntity(cluster);
        if (null != existCluster) {
            operationCluster.setUpdateTime(new Date());
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
    public RedissonxResponse<Boolean> hardDelete(String clusterName, String operator) {
        ClusterCondition condition = new ClusterCondition();
        condition.createCriteria().andClusterNameEqualTo(clusterName);
        int deleteCount = clusterMapper.deleteByCondition(condition);
        boolean deleteStatus = deleteCount > 0;
        return RedissonxResponseBuilder.success(deleteStatus);
    }

    @Override
    public RedissonxResponse<Boolean> isOnline(String clusterName) {
        ClusterCondition condition = new ClusterCondition();
        condition.createCriteria().andClusterNameEqualTo(clusterName).andStatusEqualTo(2);
        long onlineCount = clusterMapper.countByCondition(condition);
        return RedissonxResponseBuilder.success(onlineCount > 0);
    }

    @Override
    public RedissonxResponse<Boolean> reset(String clusterName, String operator) {
        ClusterCondition condition = new ClusterCondition();
        condition.createCriteria().andClusterNameEqualTo(clusterName);
        Cluster statusEntity = new Cluster();
        statusEntity.setStatus(1);
        statusEntity.setUpdateTime(new Date());
        int updateCount = clusterMapper.updateByConditionSelective(statusEntity, condition);
        boolean updateStatus = updateCount > 0;
        return RedissonxResponseBuilder.success(updateStatus);
    }

    @Override
    public RedissonxResponse<Boolean> publish(String clusterName, String operator, ClusterPublishOption option) {
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
                ClusterBO toZkEntity = toBO(newestEntity, option);
                ClusterConnectMappingCondition mappingCondition = new ClusterConnectMappingCondition();
                mappingCondition.createCriteria().andClusterNameEqualTo(clusterName);
                List<ClusterConnectMapping> mappingList = clusterConnectMappingMapper.selectByCondition(mappingCondition);
                Map<String, List<ClusterConnectBO>> connectsMapping = new HashMap<>();
                if (CollectionUtils.isNotEmpty(mappingList)) {
                    for (ClusterConnectMapping mapping : mappingList) {
                        String region = mapping.getRegion();
                        if (!connectsMapping.containsKey(region)) {
                            List<ClusterConnectBO> connects = new ArrayList<>();
                            connectsMapping.put(region, connects);

                        }
                        ClusterConnectBO clusterConnect = new ClusterConnectBO();
                        clusterConnect.setDatabase(mapping.getDs());
                        ConnectCondition connectCondition = new ConnectCondition();
                        connectCondition.createCriteria().andConnectNameEqualTo(mapping.getConnectName());
                        Connect connectItem = connectMapper.selectOneByCondition(connectCondition);
                        clusterConnect.setConnect(ConnectServiceImpl.toBO(connectItem));
                        connectsMapping.get(region).add(clusterConnect);
                    }
                }
                if (MapUtils.isNotEmpty(connectsMapping)) {
                    for (Map.Entry<String, List<ClusterConnectBO>> connectMapping : connectsMapping.entrySet()) {
                        String region = connectMapping.getKey();
                        List<ClusterConnectBO> connectList = connectMapping.getValue();
                        toZkEntity.setConnects(connectList);
                        ZookeeperClient zookeeperClient = ZookeeperClientFactory.getInstance(region);
                        if (zookeeperClient == null) {
                            LOGGER.error("no exist zookeeperClient for region:[" + region + "],please config apollo first!");
                            continue;
                        }
                        boolean setResult = zookeeperClient.setCluster(clusterName, toZkEntity);
                        if (!setResult) {
                            LOGGER.error("publish to zk error for cluster:" + clusterName);
                        }
                    }
                }
                result = true;
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
            //判断是否有在线的category
            CategoryCondition categoryCondition = new CategoryCondition();
            categoryCondition.createCriteria().andClusterNameEqualTo(clusterName).andCStatusEqualTo(2);
            Long onlineCategory = categoryMapper.countByCondition(categoryCondition);
            if (onlineCategory > 0) {
                return RedissonxResponseBuilder.fail(405, "exist online category");
            }
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
                ClusterConnectMappingCondition mappingCondition = new ClusterConnectMappingCondition();
                mappingCondition.createCriteria().andClusterNameEqualTo(clusterName);
                List<ClusterConnectMapping> mappingList = clusterConnectMappingMapper.selectByCondition(mappingCondition);
                Set<String> regionList = new HashSet<>();
                for (ClusterConnectMapping clusterConnectMapping : mappingList) {
                    regionList.add(clusterConnectMapping.getRegion());
                }
                for (String region : regionList) {
                    ZookeeperClient zookeeperClient = ZookeeperClientFactory.getInstance(region);
                    zookeeperClient.deleteCluster(clusterName);
                }
                result = true;
            } else {
                result = false;
            }
        } catch (Exception e) {
            LOGGER.error("exception for offline cluster", e);
            result = false;
        }
        return RedissonxResponseBuilder.success(result);
    }

    @Override
    public RedissonxResponse<List<ClusterConnectVO>> loadConnectsByCluster(String clusterName) {
        List<ClusterConnectVO> connects = new ArrayList<>();
        try {
            ClusterConnectMappingCondition condition = new ClusterConnectMappingCondition();
            condition.createCriteria().andClusterNameEqualTo(clusterName);
            List<ClusterConnectMapping> mappingList = clusterConnectMappingMapper.selectByCondition(condition);
            for (ClusterConnectMapping mapping : mappingList) {
                ClusterConnectVO clusterConnect = new ClusterConnectVO();
                String connect = mapping.getConnectName();
                ConnectCondition connectCondition = new ConnectCondition();
                connectCondition.createCriteria().andConnectNameEqualTo(connect);
                Connect connectItem = connectMapper.selectOneByCondition(connectCondition);
                clusterConnect.setConnectName(connectItem.getConnectName());
                clusterConnect.setDatabase(mapping.getDs());
                clusterConnect.setRegion(mapping.getRegion());
                connects.add(clusterConnect);
            }
        } catch (Exception e) {
            LOGGER.error("exception for loadConnectsByCluster", e);
            return RedissonxResponseBuilder.fail(407, "exception to load");
        }
        return RedissonxResponseBuilder.success(connects);
    }


    private void buildCriteria(ClusterCondition.Criteria criteria, ClusterQueryRequest request) {
        if (StringUtils.isNotEmpty(request.getUserId())) {
            criteria.andOwnerLike("%" + request.getUserId() + "%");
            criteria.andMemberLike("%" + request.getUserId() + "%");
        }
        if (StringUtils.isNotEmpty(request.getKeyword())) {
            criteria.andClusterNameLike("%" + request.getKeyword() + "%");
        }
        if (StringUtils.isNotEmpty(request.getClusterMode())) {
            criteria.andClusterModeEqualTo(request.getClusterMode());
        }
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
        if (StringUtils.isNotEmpty(source.getStrategys())) {
            List<ClusterAuthStrategyBO> strategyList = JSON.parseObject(source.getStrategys(), new TypeReference<List<ClusterAuthStrategyBO>>() {
            });
            for (ClusterAuthStrategyBO strategy : strategyList) {
                StrategySource strategySource = StrategySource.parse(strategy.getSource());
                switch (strategySource) {
                    case application:
                        target.setApplicationList((List<String>) strategy.getData());
                        break;
                    case tenant:
                        target.setTenantList((List<Integer>) strategy.getData());
                }
            }
        }
        target.setClusterName(source.getClusterName());
        target.setMode(source.getClusterMode());
        return target;
    }

    private ClusterBO toBO(Cluster source, ClusterPublishOption option) {
        ClusterBO target = new ClusterBO();
        if (option.getAuth() || option.getAll()) {
            if (StringUtils.isNotEmpty(source.getStrategys())) {
                List<ClusterAuthStrategyBO> strategyList = JSON.parseObject(source.getStrategys(), new TypeReference<List<ClusterAuthStrategyBO>>() {
                });
                target.setAuthStrategies(strategyList);
            }
        }
        if (option.getAll()) {
            ClusterSimpleBO clusterSimple = new ClusterSimpleBO();
            clusterSimple.setClusterName(source.getClusterName());
            clusterSimple.setMode(source.getClusterMode());
            target.setCluster(clusterSimple);
        }
        return target;
    }

    private Cluster toDBEntity(ClusterVO source) {
        Cluster target = new Cluster();
        if (CollectionUtils.isNotEmpty(source.getMemberList())) {
            target.setMember(StringUtils.join(source.getMemberList(), ","));
        }
        if (CollectionUtils.isNotEmpty(source.getOwnerList())) {
            target.setOwner(StringUtils.join(source.getOwnerList(), ","));
        }
        target.setStatus(source.getStatus());
        target.setClusterName(source.getClusterName());
        target.setClusterMode(source.getMode());
        return target;
    }

    private Cluster toDBAuthEntity(ClusterVO source) {
        Cluster target = new Cluster();
        target.setClusterName(source.getClusterName());
        List<ClusterAuthStrategyBO> clusterStrategyList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(source.getApplicationList())) {
            ClusterAuthStrategyBO s1 = new ClusterAuthStrategyBO();
            s1.setSource(StrategySource.application.getDesc());
            s1.setOperator("in");
            s1.setData(source.getApplicationList());
            clusterStrategyList.add(s1);
        }
        if (CollectionUtils.isNotEmpty(source.getTenantList())) {
            ClusterAuthStrategyBO s2 = new ClusterAuthStrategyBO();
            s2.setSource(StrategySource.tenant.getDesc());
            s2.setOperator("in");
            s2.setData(source.getTenantList());
            clusterStrategyList.add(s2);
        }
        target.setStrategys(JSON.toJSONString(clusterStrategyList));
        return target;
    }
}
