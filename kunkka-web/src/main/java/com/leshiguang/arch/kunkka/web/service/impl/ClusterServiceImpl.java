package com.leshiguang.arch.kunkka.web.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.TypeReference;
import com.leshiguang.arch.kunkka.client.configure.IConfigureClient;
import com.leshiguang.arch.kunkka.client.configure.zookeeper.ConfigureClientFactory;
import com.leshiguang.arch.kunkka.common.entity.cluster.ClusterAuthBO;
import com.leshiguang.arch.kunkka.common.entity.cluster.ClusterBO;
import com.leshiguang.arch.kunkka.common.entity.cluster.ClusterConnectParamsBO;
import com.leshiguang.arch.kunkka.common.entity.connect.ConnectBO;
import com.leshiguang.arch.kunkka.common.exception.KunkkaException;
import com.leshiguang.arch.kunkka.db.entity.gen.*;
import com.leshiguang.arch.kunkka.db.mapper.gen.ClusterAuthMappingMapper;
import com.leshiguang.arch.kunkka.db.mapper.gen.ClusterConnectMappingMapper;
import com.leshiguang.arch.kunkka.db.mapper.gen.ClusterMapper;
import com.leshiguang.arch.kunkka.db.mapper.gen.ClusterRegionMappingMapper;
import com.leshiguang.arch.kunkka.web.domain.cluster.ClusterAuthVO;
import com.leshiguang.arch.kunkka.web.domain.cluster.ClusterConnectVO;
import com.leshiguang.arch.kunkka.web.domain.cluster.ClusterQueryReq;
import com.leshiguang.arch.kunkka.web.domain.cluster.ClusterVO;
import com.leshiguang.arch.kunkka.web.exception.ServerErrorCode;
import com.leshiguang.arch.kunkka.web.operate.log.OperateLogBuilder;
import com.leshiguang.arch.kunkka.web.service.CategoryService;
import com.leshiguang.arch.kunkka.web.service.ClusterService;
import com.leshiguang.arch.kunkka.web.service.ConnectService;
import com.leshiguang.arch.kunkka.web.service.enums.StatusEnum;
import com.leshiguang.arch.kunkka.web.utils.Transfer;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-19 13:27
 * @Modify
 */
@Service
public class ClusterServiceImpl implements ClusterService {
    @Resource
    private ClusterMapper clusterMapper;
    @Resource
    private ConnectService connectService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private ClusterConnectMappingMapper clusterConnectMappingMapper;
    @Resource
    private ClusterRegionMappingMapper clusterRegionMappingMapper;
    @Resource
    private ClusterAuthMappingMapper clusterAuthMappingMapper;

    @Resource
    private ConfigureClientFactory kunkkaWebConfigureClientFactory;

    private Transfer.ClusterTransfer TRS = new Transfer.ClusterTransfer() {
        @Override
        public Cluster VO2PO(ClusterVO core) {
            Cluster target = new Cluster();
            target.setClusterEngine(core.getEngine());
            target.setClusterMode(core.getMode());
            target.setClusterName(core.getClusterName());
            if (CollectionUtils.isNotEmpty(core.getOwnerList())) {
                target.setOwnerList(StringUtils.join(core.getOwnerList(), ","));
            }
            if (CollectionUtils.isNotEmpty(core.getMemberList())) {
                target.setMemberList(StringUtils.join(core.getMemberList(), ","));
            }
            return target;
        }

        public ClusterVO PO2VO(Cluster core, List<ClusterRegionMapping> regionMappings) {
            ClusterVO target = new ClusterVO();
            target.setClusterName(core.getClusterName());
            target.setCreateTime(core.getCreateTime());
            target.setUpdateTime(core.getUpdateTime());
            target.setStatus(core.getcStatus());
            target.setEngine(core.getClusterEngine());
            target.setMode(core.getClusterMode());
            List<String> regionList = new ArrayList<>();
            for (ClusterRegionMapping regionMapping : regionMappings) {
                String region = regionMapping.getRegion();
                regionList.add(region);
            }
            target.setRegionList(regionList);
            if (StringUtils.isNotBlank(core.getMemberList())) {
                target.setMemberList(Arrays.asList(StringUtils.split(core.getMemberList(), ",")));
            }
            if (StringUtils.isNotBlank(core.getOwnerList())) {
                target.setOwnerList(Arrays.asList(StringUtils.split(core.getOwnerList(), ",")));
            }
            return target;
        }
    };

    private Transfer.ClusterAuthTransfer TARS = new Transfer.ClusterAuthTransfer() {

        @Override
        public ClusterAuthVO PO2VO(ClusterAuthMapping core) {
            ClusterAuthVO target = new ClusterAuthVO();
            target.setAuthId(core.getId());
            target.setClusterName(core.getClusterName());
            target.setData(JSONArray.parseArray(core.getData()));
            target.setField(core.getField());
            target.setOperate(core.getOperate());
            return target;
        }

        @Override
        public ClusterAuthMapping VO2PO(ClusterAuthVO core) {
            ClusterAuthMapping target = new ClusterAuthMapping();
            target.setId(core.getAuthId());
            target.setClusterName(core.getClusterName());
            target.setData(JSON.toJSONString(core.getData()));
            target.setField(core.getField());
            target.setOperate(core.getOperate());
            return target;
        }

        @Override
        public ClusterAuthBO PO2BO(ClusterAuthMapping core) {
            ClusterAuthBO target = new ClusterAuthBO();
            target.setData(JSONArray.parseArray(core.getData()));
            target.setField(core.getField());
            target.setOperate(core.getOperate());
            return target;
        }

        @Override
        public List<ClusterAuthBO> PO2BO(List<ClusterAuthMapping> cores) {
            List<ClusterAuthBO> targetList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(cores)) {
                for (ClusterAuthMapping core : cores) {
                    targetList.add(this.PO2BO(core));
                }
            }
            return targetList;
        }

        @Override
        public List<ClusterAuthVO> PO2VO(List<ClusterAuthMapping> cores) {
            List<ClusterAuthVO> targetList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(cores)) {
                for (ClusterAuthMapping core : cores) {
                    targetList.add(this.PO2VO(core));
                }
            }
            return targetList;
        }
    };

    @Override
    public Page<ClusterVO> query(ClusterQueryReq request, PageRequest page) throws KunkkaException {
        ClusterCondition condition = new ClusterCondition();
        condition.setMysqlOffset(Long.valueOf(page.getOffset()).intValue());
        condition.setMysqlLength(page.getPageSize());
        buildCriteria(condition.createCriteria(), request);
        long counts = clusterMapper.countByCondition(condition);
        Page<ClusterVO> result = null;
        if (counts > 0) {
            List<Cluster> clusterList = clusterMapper.selectByCondition(condition);
            List<ClusterVO> targetList = new ArrayList<>();
            if (CollectionUtils.isNotEmpty(clusterList)) {
                for (Cluster cluster : clusterList) {
                    List<ClusterRegionMapping> regionMappingList = selectRegionMappingByClusterName(cluster.getClusterName());
                    ClusterVO target = TRS.PO2VO(cluster, regionMappingList);
                    targetList.add(target);
                }
            }
            result = new PageImpl<>(targetList, page, counts);
        } else {
            result = new PageImpl<>(new ArrayList<>(), page, counts);
        }
        return result;
    }

    private List<ClusterRegionMapping> selectRegionMappingByClusterName(String clusterName) {
        ClusterRegionMappingCondition condition = new ClusterRegionMappingCondition();
        condition.createCriteria().andClusterNameEqualTo(clusterName);
        return clusterRegionMappingMapper.selectByCondition(condition);
    }

    @Override
    public ClusterVO load(String clusterName) {
        ClusterCondition condition = buildCondition(clusterName);
        Cluster cluster = clusterMapper.selectOneByCondition(condition);
        if (null != cluster) {
            List<ClusterRegionMapping> regionMappingList = selectRegionMappingByClusterName(clusterName);
            ClusterVO clusterVO = TRS.PO2VO(cluster, regionMappingList);
            return clusterVO;
        } else {
            throw new KunkkaException(ServerErrorCode.CLUSTER_NOT_EXISTS);
        }
    }

    @Override
    public Boolean save(ClusterVO cluster, String operator) {
        ClusterCondition condition = buildCondition(cluster.getClusterName());
        Cluster existCluster = clusterMapper.selectOneByCondition(condition);
        Cluster operationCluster = TRS.VO2PO(cluster);
        if (null == existCluster) {
            operationCluster.setcStatus(StatusEnum.CREATED.getCode());
            int insertCount = clusterMapper.insertSelective(operationCluster);
            this.saveRegionMapping(cluster);
            OperateLogBuilder.createOpt().of(OperateLogBuilder.RelationType.CLUSTER, cluster.getClusterName()).with(operator).content("新建").log();
            return insertCount > 0;
        } else {
            if (existCluster.getcStatus().equals(StatusEnum.ONLINE.getCode())) {
                throw new KunkkaException(ServerErrorCode.CLUSTER_ONLINE_CAN_NOT_EDIT_ERROR);
            }
            operationCluster.setCreateTime(existCluster.getCreateTime());
            operationCluster.setcStatus(existCluster.getcStatus());
            operationCluster.setId(existCluster.getId());
            int updateCount = clusterMapper.updateByIdSelective(operationCluster);
            this.saveRegionMapping(cluster);
            OperateLogBuilder.modifyOpt().of(OperateLogBuilder.RelationType.CLUSTER, cluster.getClusterName()).with(operator).content("修改").log();
            return updateCount > 0;
        }
    }

    private Boolean saveRegionMapping(ClusterVO cluster) {
        ClusterCondition condition = buildCondition(cluster.getClusterName());
        Cluster existCluster = clusterMapper.selectOneByCondition(condition);
        if (existCluster.getcStatus().equals(StatusEnum.ONLINE.getCode())) {
            throw new KunkkaException(ServerErrorCode.CLUSTER_ONLINE_CAN_NOT_EDIT_ERROR);
        }
        ClusterRegionMappingCondition regionMappingCondition = new ClusterRegionMappingCondition();
        regionMappingCondition.createCriteria().andClusterNameEqualTo(cluster.getClusterName());
        List<ClusterRegionMapping> allList = clusterRegionMappingMapper.selectByCondition(regionMappingCondition);
        List<String> regionList = cluster.getRegionList();
        List<String> deleteList = allList.stream().filter(item -> !regionList.contains(item.getRegion())).map(item -> item.getRegion()).collect(Collectors.toList());
        List<ClusterRegionMapping> addList = regionList.stream().filter(item -> {
            boolean exist = false;
            for (ClusterRegionMapping i : allList) {
                if (i.getRegion().equalsIgnoreCase(item)) {
                    exist = true;
                }
            }
            return !exist;
        }).map(item -> {
            ClusterRegionMapping clusterRegionMapping = new ClusterRegionMapping();
            clusterRegionMapping.setRegion(item);
            clusterRegionMapping.setClusterName(cluster.getClusterName());
            return clusterRegionMapping;
        }).collect(Collectors.toList());
        if (CollectionUtils.isNotEmpty(deleteList)) {
            ClusterRegionMappingCondition deleteCondition = new ClusterRegionMappingCondition();
            deleteCondition.createCriteria().andClusterNameEqualTo(cluster.getClusterName()).andRegionIn(deleteList);
            clusterRegionMappingMapper.deleteByCondition(deleteCondition);
            //删除无效的connect
            ClusterConnectMappingCondition connectMappingCondition = new ClusterConnectMappingCondition();
            connectMappingCondition.createCriteria().andClusterNameEqualTo(cluster.getClusterName()).andRegionIn(deleteList);
            clusterConnectMappingMapper.deleteByCondition(connectMappingCondition);
        }
        if (CollectionUtils.isNotEmpty(addList)) {
            clusterRegionMappingMapper.batchInsert(addList);
        }
        return true;
    }

    @Override
    public Boolean saveConnect(ClusterConnectVO clusterConnect, String operator) throws KunkkaException {
        ClusterCondition condition = buildCondition(clusterConnect.getClusterName());
        Cluster existCluster = clusterMapper.selectOneByCondition(condition);
        if (existCluster.getcStatus().equals(StatusEnum.ONLINE.getCode())) {
            throw new KunkkaException(ServerErrorCode.CLUSTER_ONLINE_CAN_NOT_EDIT_ERROR);
        }
        ClusterConnectMappingCondition connectMappingCondition = new ClusterConnectMappingCondition();
        connectMappingCondition.createCriteria().andRegionEqualTo(clusterConnect.getRegion()).andClusterNameEqualTo(clusterConnect.getClusterName());
        ClusterConnectMapping existClusterConnect = clusterConnectMappingMapper.selectOneByCondition(connectMappingCondition);
        ClusterConnectMapping operationClusterConnect = new ClusterConnectMapping();
        operationClusterConnect.setRegion(clusterConnect.getRegion());
        operationClusterConnect.setClusterName(clusterConnect.getClusterName());
        operationClusterConnect.setDb(clusterConnect.getDb());
        operationClusterConnect.setMasterNode(clusterConnect.getMasterNode());
        operationClusterConnect.setSlaveNodes(StringUtils.join(clusterConnect.getSlaveNodes(), ","));
        operationClusterConnect.setPasswordMode(clusterConnect.getPasswordMode());
        operationClusterConnect.setPassword(clusterConnect.getPassword());

        if (null == existClusterConnect) {
            int insertCount = clusterConnectMappingMapper.insertSelective(operationClusterConnect);
            changeStatus(clusterConnect.getClusterName(), StatusEnum.READY.getCode(), Arrays.asList(StatusEnum.CREATED.getCode(), StatusEnum.READY.getCode()));
            OperateLogBuilder.modifyOpt().of(OperateLogBuilder.RelationType.CLUSTER, clusterConnect.getClusterName()).with(operator).content("新建连接,区域:[%s]", clusterConnect.getRegion()).log();
            return insertCount > 0;
        } else {
            int updateCount = clusterConnectMappingMapper.updateByConditionSelective(operationClusterConnect, connectMappingCondition);
            OperateLogBuilder.modifyOpt().of(OperateLogBuilder.RelationType.CLUSTER, clusterConnect.getClusterName()).with(operator).content("修改连接:区域[%s]", clusterConnect.getRegion()).log();
            return updateCount > 0;

        }
    }

    @Override
    public ClusterConnectVO loadConnect(String clusterName, String region) throws KunkkaException {
        if (StringUtils.isBlank(clusterName)) {
            throw new KunkkaException(ServerErrorCode.PARAM_CHECK_ERROR, "clusterName", clusterName);
        }
        if (StringUtils.isBlank(region)) {
            throw new KunkkaException(ServerErrorCode.PARAM_CHECK_ERROR, "region", region);
        }
        ClusterConnectMappingCondition condition = new ClusterConnectMappingCondition();
        condition.createCriteria().andRegionEqualTo(region).andClusterNameEqualTo(clusterName);
        ClusterConnectMapping clusterConnectMapping = clusterConnectMappingMapper.selectOneByCondition(condition);
        if (null != clusterConnectMapping) {
            ClusterConnectVO clusterConnect = new ClusterConnectVO();
            clusterConnect.setRegion(clusterConnectMapping.getRegion());
            clusterConnect.setClusterName(clusterConnectMapping.getClusterName());
            clusterConnect.setDb(clusterConnectMapping.getDb());
            clusterConnect.setMasterNode(clusterConnectMapping.getMasterNode());
            clusterConnect.setSlaveNodes(Arrays.asList(StringUtils.split(clusterConnectMapping.getSlaveNodes(), ",")));
            clusterConnect.setPasswordMode(clusterConnect.getPasswordMode());
            clusterConnect.setPassword(clusterConnect.getPassword());
            return clusterConnect;
        } else {
            return null;
        }
    }

    @Override
    public Boolean saveAuth(ClusterAuthVO clusterAuth, String operator) throws KunkkaException {
        ClusterAuthMapping clusterAuthMapping = TARS.VO2PO(clusterAuth);
        if (null == clusterAuth.getAuthId()) {
            int insertCount = clusterAuthMappingMapper.insertSelective(clusterAuthMapping);
            OperateLogBuilder.modifyOpt().of(OperateLogBuilder.RelationType.CLUSTER, clusterAuth.getClusterName()).with(operator).content("新增权限限制,%s %s %s", clusterAuth.getField(), clusterAuth.getOperate(), JSON.toJSONString(clusterAuth.getData())).log();
            return insertCount > 0;
        } else {
            int updateCount = clusterAuthMappingMapper.updateById(clusterAuthMapping);
            OperateLogBuilder.modifyOpt().of(OperateLogBuilder.RelationType.CLUSTER, clusterAuth.getClusterName()).with(operator).content("修改权限限制,%s %s %s", clusterAuth.getField(), clusterAuth.getOperate(), JSON.toJSONString(clusterAuth.getData())).log();
            return updateCount > 0;
        }
    }

    @Override
    public List<ClusterAuthVO> loadAuths(String clusterName) throws KunkkaException {
        ClusterAuthMappingCondition condition = new ClusterAuthMappingCondition();
        condition.createCriteria().andClusterNameEqualTo(clusterName);
        List<ClusterAuthMapping> clusterAuthMappingList = clusterAuthMappingMapper.selectByCondition(condition);
        List<ClusterAuthVO> clusterAuthVOList = TARS.PO2VO(clusterAuthMappingList);
        return clusterAuthVOList;
    }

    @Override
    public Boolean deleteAuth(Integer authId) throws KunkkaException {
        int deleteCount = clusterAuthMappingMapper.deleteById(authId);
        return deleteCount > 0;
    }

    @Override
    public Boolean delete(String clusterName, String operator) {
        boolean deleteStatus = changeStatus(clusterName, StatusEnum.DELETED.getCode(), Arrays.asList(StatusEnum.CREATED.getCode(), StatusEnum.READY.getCode()));
        OperateLogBuilder.deleteOpt().of(OperateLogBuilder.RelationType.CLUSTER, clusterName).with(operator).content("删除").log();
        return deleteStatus;
    }

    @Override
    public Boolean hardDelete(String clusterName, String operator) {
        ClusterCondition condition = new ClusterCondition();
        condition.createCriteria().andClusterNameEqualTo(clusterName);
        int deleteCount = clusterMapper.deleteByCondition(condition);
        boolean deleteStatus = deleteCount > 0;
        return deleteStatus;
    }

    @Override
    public Boolean reset(String clusterName, String operator) {
        ClusterConnectMappingCondition condition = new ClusterConnectMappingCondition();
        condition.createCriteria().andClusterNameEqualTo(clusterName);
        Integer targetStatus = StatusEnum.CREATED.getCode();
        if (clusterConnectMappingMapper.countByCondition(condition) > 0) {
            targetStatus = StatusEnum.READY.getCode();
        }
        boolean resetStatus = changeStatus(clusterName, targetStatus, Arrays.asList(StatusEnum.DELETED.getCode()));
        OperateLogBuilder.resetOpt().of(OperateLogBuilder.RelationType.CLUSTER, clusterName).with(operator).content("恢复").log();
        return resetStatus;
    }

    @Override
    public Boolean publish(String clusterName, String operator) throws KunkkaException {
        ClusterCondition condition = buildCondition(clusterName);
        Cluster existCluster = clusterMapper.selectOneByCondition(condition);
        if (null == existCluster) {
            throw new KunkkaException(ServerErrorCode.CLUSTER_NOT_EXISTS);
        }
        if (existCluster.getcStatus() != StatusEnum.READY.getCode()) {
            throw new KunkkaException(ServerErrorCode.CLUSTER_MAST_BE_READY);
        }
        ClusterConnectMappingCondition clusterConnectMappingCondition = new ClusterConnectMappingCondition();
        clusterConnectMappingCondition.createCriteria().andClusterNameEqualTo(clusterName);
        List<ClusterConnectMapping> clusterConnectMappings = clusterConnectMappingMapper.selectByCondition(clusterConnectMappingCondition);
        if (CollectionUtils.isEmpty(clusterConnectMappings)) {
            throw new KunkkaException(ServerErrorCode.CLUSTER_MAST_BE_READY);
        }
        Map<String, ClusterConnectMapping> regionMap = new HashMap<>();
        for (ClusterConnectMapping clusterConnectMapping : clusterConnectMappings) {
            regionMap.put(clusterConnectMapping.getRegion(), clusterConnectMapping);
        }
        List<String> regions = loadRegions(clusterName);
        for (String region : regions) {
            if (!regionMap.containsKey(region)) {
                throw new KunkkaException(ServerErrorCode.CLUSTER_PUBLISH_ERROR_HAS_NO_CONFIG_REGION_CONNECT, region);
            }
        }
        ClusterAuthMappingCondition clusterAuthMappingCondition = new ClusterAuthMappingCondition();
        clusterAuthMappingCondition.createCriteria().andClusterNameEqualTo(clusterName);
        List<ClusterAuthMapping> authMappingList = clusterAuthMappingMapper.selectByCondition(clusterAuthMappingCondition);
        List<ClusterAuthBO> clusterAuthList = TARS.PO2BO(authMappingList);
        for (Map.Entry<String, ClusterConnectMapping> regionEntry : regionMap.entrySet()) {
            String region = regionEntry.getKey();
            ClusterBO zkClusterBO = new ClusterBO();
            zkClusterBO.setClusterName(existCluster.getClusterName());
            zkClusterBO.setEngine(existCluster.getClusterEngine());
            zkClusterBO.setMode(existCluster.getClusterMode());
            zkClusterBO.setStrategys(clusterAuthList);
            ClusterConnectMapping clusterConnectMapping = regionEntry.getValue();
            if (StringUtils.isNotBlank(clusterConnectMapping.getMasterNode())) {
                ConnectBO masterNode = connectService.loadBOByName(clusterConnectMapping.getMasterNode());
                zkClusterBO.setMasterNode(masterNode);
            }
            zkClusterBO.setDatabase(clusterConnectMapping.getDb());
            if (clusterConnectMapping.getPasswordMode() > 0) {
                zkClusterBO.setPassword(clusterConnectMapping.getPassword());
            }
            if (StringUtils.isNotBlank(clusterConnectMapping.getSlaveNodes())) {
                List<ConnectBO> slaveNodes = new ArrayList<>();
                List<String> slaveNodeNames = Arrays.asList(StringUtils.split(clusterConnectMapping.getSlaveNodes(), ","));
                for (String slaveNodeName : slaveNodeNames) {
                    ConnectBO slaveNode = connectService.loadBOByName(slaveNodeName);
                    slaveNodes.add(slaveNode);
                }
                zkClusterBO.setSlaveNodes(slaveNodes);
            }
            if (StringUtils.isNotBlank(clusterConnectMapping.getConnectParams())) {
                ClusterConnectParamsBO connectParams = JSON.parseObject(clusterConnectMapping.getConnectParams(), new TypeReference<ClusterConnectParamsBO>() {
                });
                zkClusterBO.setConnectParams(connectParams);
            }
            IConfigureClient zookeeperClient = kunkkaWebConfigureClientFactory.getInstance(region);
            boolean publishClusterResult = zookeeperClient.setCluster(clusterName, zkClusterBO);
            if (!publishClusterResult) {
                throw new KunkkaException(ServerErrorCode.CLUSTER_MAST_BE_READY, clusterName, region);
            }
        }
        boolean publishStatus = changeStatus(clusterName, StatusEnum.ONLINE.getCode(), Arrays.asList(StatusEnum.READY.getCode()));
        OperateLogBuilder.onlineOpt().of(OperateLogBuilder.RelationType.CLUSTER, clusterName).with(operator).content("发布").log();
        return publishStatus;
    }

    @Override
    public Boolean offline(String clusterName, String operator) {
        List<String> onlineCategorys = categoryService.loadOnlineCategorys(clusterName);
        if (CollectionUtils.isNotEmpty(onlineCategorys)) {
            throw new KunkkaException(ServerErrorCode.CLUSTER_OFFLINE_ERROR_HAS_ONLINE_CATEGORY, JSON.toJSONString(onlineCategorys));
        }
        ClusterConnectMappingCondition mappingCondition = new ClusterConnectMappingCondition();
        mappingCondition.createCriteria().andClusterNameEqualTo(clusterName);
        List<ClusterConnectMapping> mappingList = clusterConnectMappingMapper.selectByCondition(mappingCondition);
        Set<String> regionList = new HashSet<>();
        for (ClusterConnectMapping clusterConnectMapping : mappingList) {
            regionList.add(clusterConnectMapping.getRegion());
        }
        for (String region : regionList) {
            IConfigureClient zookeeperClient = kunkkaWebConfigureClientFactory.getInstance(region);
            zookeeperClient.deleteCluster(clusterName);
        }
        //修改状态
        boolean offlineStatus = changeStatus(clusterName, StatusEnum.READY.getCode(), Arrays.asList(StatusEnum.ONLINE.getCode()));
        if (!offlineStatus) {
            throw new KunkkaException(ServerErrorCode.CLUSTER_HAS_OFFLINE_ERROR);
        }
        OperateLogBuilder.offlineOpt().of(OperateLogBuilder.RelationType.CLUSTER, clusterName).with(operator).content("下线").log();
        return true;
    }

    @Override
    public List<String> loadRegions(String clusterName) throws KunkkaException {
        ClusterRegionMappingCondition clusterRegionMappingCondition = new ClusterRegionMappingCondition();
        clusterRegionMappingCondition.createCriteria().andClusterNameEqualTo(clusterName);
        List<ClusterRegionMapping> clusterRegionMappings = clusterRegionMappingMapper.selectByCondition(clusterRegionMappingCondition);
        List<String> regions = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(clusterRegionMappings)) {
            for (ClusterRegionMapping clusterRegionMapping : clusterRegionMappings) {
                regions.add(clusterRegionMapping.getRegion());
            }
        }
        return regions;
    }


    private ClusterCondition buildCondition(String clusterName) {
        ClusterCondition condition = new ClusterCondition();
        condition.createCriteria().andClusterNameEqualTo(clusterName);
        return condition;
    }

    private boolean changeStatus(String clusterName, Integer status, List<Integer> preValidStatus) {
        ClusterCondition condition = new ClusterCondition();
        condition.createCriteria().andClusterNameEqualTo(clusterName).andCStatusIn(preValidStatus);
        Cluster statusEntity = new Cluster();
        statusEntity.setcStatus(status);
        statusEntity.setUpdateTime(new Date());
        int updateCount = clusterMapper.updateByConditionSelective(statusEntity, condition);
        return updateCount > 0;
    }

    private void buildCriteria(ClusterCondition.Criteria criteria, ClusterQueryReq request) {
        if (StringUtils.isNotEmpty(request.getKeyword())) {
            criteria.andClusterNameLike("%" + request.getKeyword() + "%");
        }
        if (StringUtils.isNotEmpty(request.getMode())) {
            criteria.andClusterModeEqualTo(request.getMode());
        }
    }
}
