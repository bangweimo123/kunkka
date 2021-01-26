package com.leshiguang.arch.kunkka.web.service.impl;

import com.leshiguang.arch.kunkka.db.entity.gen.ClusterConnectMapping;
import com.leshiguang.arch.kunkka.db.entity.gen.ClusterConnectMappingCondition;
import com.leshiguang.arch.kunkka.db.entity.gen.Connect;
import com.leshiguang.arch.kunkka.db.entity.gen.ConnectCondition;
import com.leshiguang.arch.kunkka.db.mapper.gen.ClusterConnectMappingMapper;
import com.leshiguang.arch.kunkka.db.mapper.gen.ConnectMapper;
import com.leshiguang.arch.kunkka.web.domain.cluster.ClusterVO;
import com.leshiguang.arch.kunkka.web.domain.connect.ConnectQueryReq;
import com.leshiguang.arch.kunkka.web.domain.connect.ConnectVO;
import com.leshiguang.arch.kunkka.web.exception.ServerErrorCode;
import com.leshiguang.arch.kunkka.web.operate.log.OperateLogBuilder;
import com.leshiguang.arch.kunkka.web.service.ClusterService;
import com.leshiguang.arch.kunkka.web.service.ConnectService;
import com.leshiguang.arch.kunkka.web.service.enums.StatusEnum;
import com.leshiguang.arch.kunkka.web.utils.Transfer;
import com.leshiguang.arch.kunkka.common.entity.connect.ConnectBO;
import com.leshiguang.arch.kunkka.common.exception.KunkkaException;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-04-15 15:07
 * @Modify
 */
@Service
public class ConnectServiceImpl implements ConnectService {

    @Resource
    private ConnectMapper connectMapper;
    @Resource
    private ClusterService clusterService;
    @Resource
    private ClusterConnectMappingMapper clusterConnectMappingMapper;

    private Transfer.ConnectTransfer TRS = new Transfer.ConnectTransfer() {

        @Override
        public ConnectVO BO2VO(ConnectBO core) {
            throw new KunkkaException(ServerErrorCode.UN_SUPPORT_CONVERT_ERROR);
        }

        @Override
        public Connect BO2PO(ConnectBO core) {
            throw new KunkkaException(ServerErrorCode.UN_SUPPORT_CONVERT_ERROR);
        }

        @Override
        public ConnectVO PO2VO(Connect core) {
            ConnectVO target = new ConnectVO();
            target.setRegion(core.getRegion());
            target.setPort(core.getPort());
            target.setHostName(core.getHostName());
            target.setConnectName(core.getConnectName());
            target.setCreateTime(core.getCreateTime());
            target.setUpdateTime(core.getUpdateTime());
            target.setStatus(core.getcStatus());
            return target;
        }

        @Override
        public Connect VO2PO(ConnectVO core) {
            Connect target = new Connect();
            target.setRegion(core.getRegion());
            target.setPort(core.getPort());
            target.setHostName(core.getHostName());
            target.setConnectName(core.getConnectName());
            target.setCreateTime(core.getCreateTime());
            target.setUpdateTime(core.getUpdateTime());
            target.setcStatus(core.getStatus());
            return target;
        }

        @Override
        public ConnectBO PO2BO(Connect core) {
            ConnectBO target = new ConnectBO();
            target.setConnectName(core.getConnectName());
            target.setHostName(core.getHostName());
            target.setPort(core.getPort());
            return target;
        }

        @Override
        public List<ConnectVO> BO2VO(List<ConnectBO> cores) {
            throw new KunkkaException(ServerErrorCode.UN_SUPPORT_CONVERT_ERROR);
        }

        @Override
        public List<ConnectVO> PO2VO(List<Connect> cores) {
            List<ConnectVO> targetList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(cores)) {
                for (Connect core : cores) {
                    ConnectVO target = this.PO2VO(core);
                    targetList.add(target);
                }
            }
            return targetList;
        }
    };

    @Override
    public Page<ConnectVO> query(ConnectQueryReq request, PageRequest page) {
        ConnectCondition condition = new ConnectCondition();
        condition.setMysqlOffset(Long.valueOf(page.getOffset()).intValue());
        condition.setMysqlLength(page.getPageSize());
        buildCriteria(condition.createCriteria(), request);
        long counts = connectMapper.countByCondition(condition);
        Page<ConnectVO> result = null;
        if (counts > 0) {
            List<Connect> connectList = connectMapper.selectByCondition(condition);
            List<ConnectVO> resultList = TRS.PO2VO(connectList);
            result = new PageImpl<>(resultList, page, counts);
        } else {
            result = new PageImpl<>(new ArrayList<>(), page, counts);
        }
        return result;
    }

    @Override
    public ConnectVO loadVOByName(String connectName) throws KunkkaException {
        ConnectCondition condition = buildCondition(connectName);
        Connect connect = connectMapper.selectOneByCondition(condition);
        return TRS.PO2VO(connect);
    }

    @Override
    public ConnectBO loadBOByName(String connectName) throws KunkkaException {
        ConnectCondition condition = buildCondition(connectName);
        Connect connect = connectMapper.selectOneByCondition(condition);
        return TRS.PO2BO(connect);
    }

    @Override
    public Boolean save(ConnectVO connect, String operator) {
        //连接测试
        Jedis jedis = null;
        try {
            jedis = new Jedis(connect.getHostName(), connect.getPort());
            String result = jedis.ping();
        } catch (Exception e) {
            throw new KunkkaException(ServerErrorCode.CONNECT_PING_ERROR, connect.getHostName(), Integer.toString(connect.getPort()));
        } finally {
            if (null != jedis) {
                jedis.close();
            }
        }
        ConnectCondition condition = buildCondition(connect.getConnectName());
        Connect existConnect = connectMapper.selectOneByCondition(condition);
        Connect operationConnect = TRS.VO2PO(connect);
        if (null == existConnect) {
            operationConnect.setcStatus(StatusEnum.CREATED.getCode());
            operationConnect.setCreateTime(new Date());
            int insertCount = connectMapper.insertSelective(operationConnect);
            OperateLogBuilder.createOpt().of(OperateLogBuilder.RelationType.CONNECT, connect.getConnectName()).with(operator).content("新建").log();
            return insertCount > 0;
        } else {
            checkCanOptionConnect(connect.getConnectName());
            operationConnect.setUpdateTime(new Date());
            operationConnect.setId(existConnect.getId());
            int updateCount = connectMapper.updateByIdSelective(operationConnect);
            OperateLogBuilder.modifyOpt().of(OperateLogBuilder.RelationType.CONNECT, connect.getConnectName()).with(operator).content("修改").log();
            return updateCount > 0;
        }
    }

    private void checkCanOptionConnect(String connectName) {
        List<String> relationClusters = this.loadRelationClusters(connectName);
        if (CollectionUtils.isNotEmpty(relationClusters)) {
            for (String relationCluster : relationClusters) {
                ClusterVO clusterVO = clusterService.load(relationCluster);
                if (clusterVO.getStatus() == StatusEnum.ONLINE.getCode()) {
                    throw new KunkkaException(ServerErrorCode.CLUSTER_ONLINE_CAN_NOT_EDIT_ERROR);
                }
            }
        }
    }

    @Override
    public Boolean delete(String connectName, String operator) {
        checkCanOptionConnect(connectName);
        Boolean resetStatus = changeStatus(connectName, StatusEnum.DELETED.getCode(), Arrays.asList(StatusEnum.CREATED.getCode()));
        OperateLogBuilder.deleteOpt().of(OperateLogBuilder.RelationType.CONNECT, connectName).with(operator).content("删除").log();
        return resetStatus;
    }

    @Override
    public Boolean hardDelete(String connectName, String operator) throws KunkkaException {
        checkCanOptionConnect(connectName);
        ConnectCondition condition = buildCondition(connectName);
        Integer deleteCount = connectMapper.deleteByCondition(condition);
        return deleteCount > 0;
    }

    @Override
    public Boolean reset(String connectName, String operator) throws KunkkaException {
        Boolean resetStatus = changeStatus(connectName, StatusEnum.CREATED.getCode(), Arrays.asList(StatusEnum.DELETED.getCode()));
        OperateLogBuilder.resetOpt().of(OperateLogBuilder.RelationType.CONNECT, connectName).with(operator).content("重置").log();
        return resetStatus;
    }

    @Override
    public List<String> loadRelationClusters(String connectName) throws KunkkaException {
        ClusterConnectMappingCondition connectMappingCondition = new ClusterConnectMappingCondition();
        connectMappingCondition.or(connectMappingCondition.createCriteria().andMasterNodeEqualTo(connectName));
        connectMappingCondition.or(connectMappingCondition.createCriteria().andSlaveNodesLike("%" + connectName + "%"));
        List<ClusterConnectMapping> clusterConnectMappingList = clusterConnectMappingMapper.selectByCondition(connectMappingCondition);
        List<String> clusterList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(clusterConnectMappingList)) {
            for (ClusterConnectMapping clusterConnectMapping : clusterConnectMappingList) {
                clusterList.add(clusterConnectMapping.getClusterName());
            }
        }
        return clusterList;
    }

    private boolean changeStatus(String connectName, Integer status, List<Integer> filterStatusList) {
        ConnectCondition condition = new ConnectCondition();
        condition.createCriteria().andConnectNameEqualTo(connectName).andCStatusIn(filterStatusList);
        Connect toDB = new Connect();
        toDB.setcStatus(status);
        int updateCount = connectMapper.updateByConditionSelective(toDB, condition);
        return updateCount > 0;
    }

    private ConnectCondition buildCondition(String connectName) {
        ConnectCondition condition = new ConnectCondition();
        condition.createCriteria().andConnectNameEqualTo(connectName);
        return condition;
    }

    private void buildCriteria(ConnectCondition.Criteria criteria, ConnectQueryReq request) {
        if (StringUtils.isNotEmpty(request.getKeyword())) {
            criteria.andConnectNameLike("%" + request.getKeyword() + "%");
        }
        if (StringUtils.isNotEmpty(request.getRegion())) {
            criteria.andRegionEqualTo(request.getRegion());
        }
        if (StringUtils.isNotEmpty(request.getAddress())) {
            criteria.andHostNameLike("%" + request.getAddress() + "%");
        }
    }
}
