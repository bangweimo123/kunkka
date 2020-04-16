package com.leshiguang.arch.redissonx.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.leshiguang.arch.redissonx.core.entity.gen.ClusterCondition;
import com.leshiguang.arch.redissonx.core.entity.gen.Connect;
import com.leshiguang.arch.redissonx.core.entity.gen.ConnectCondition;
import com.leshiguang.arch.redissonx.core.mapper.gen.ClusterMapper;
import com.leshiguang.arch.redissonx.core.mapper.gen.ConnectMapper;
import com.leshiguang.arch.redissonx.server.domain.connect.ConnectVO;
import com.leshiguang.arch.redissonx.server.domain.request.ConnectQueryRequest;
import com.leshiguang.arch.redissonx.server.service.ConnectService;
import com.leshiguang.redissonx.common.base.RedissonxPaging;
import com.leshiguang.redissonx.common.base.RedissonxResponse;
import com.leshiguang.redissonx.common.base.RedissonxResponseBuilder;
import com.leshiguang.redissonx.common.base.RedissonxTable;
import com.leshiguang.redissonx.common.entity.connect.ConnectBO;
import com.leshiguang.redissonx.common.entity.connect.ConnectPasswordBO;
import com.leshiguang.redissonx.common.entity.connect.ConnectSSHBO;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

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
    private ClusterMapper clusterMapper;

    @Override
    public RedissonxResponse<RedissonxTable<ConnectVO>> query(ConnectQueryRequest request, RedissonxPaging paging) {
        ConnectCondition condition = new ConnectCondition();
        condition.setMysqlOffset((paging.getPageIndex() - 1) * paging.getPageSize());
        condition.setMysqlLength(paging.getPageSize());
        buildCriteria(condition.createCriteria(), request);
        List<Connect> connectList = connectMapper.selectByCondition(condition);
        long counts = connectMapper.countByCondition(condition);
        List<ConnectVO> resultList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(connectList)) {
            for (Connect connect : connectList) {
                ConnectVO connectVO = toVO(connect);
                resultList.add(connectVO);
            }
        }
        RedissonxTable<ConnectVO> result = new RedissonxTable<>(paging.getPageIndex(), paging.getPageSize(), resultList, counts);
        return RedissonxResponseBuilder.success(result);
    }

    @Override
    public RedissonxResponse<Boolean> save(ConnectVO connect, String operator) {
        ConnectCondition condition = new ConnectCondition();
        condition.createCriteria().andConnectNameEqualTo(connect.getConnectName());
        Connect existConnect = connectMapper.selectOneByCondition(condition);
        Boolean result = false;
        Connect operationConnect = toDBEntity(connect);
        if (null == existConnect) {
            operationConnect.setOperator(operator);
            operationConnect.setCreator(operator);
            operationConnect.setCreateTime(new Date());
            operationConnect.setUpdateTime(new Date());
            int insertCount = connectMapper.insertSelective(operationConnect);
            result = insertCount > 0;
        } else {
            operationConnect.setUpdateTime(new Date());
            operationConnect.setCreateTime(existConnect.getCreateTime());
            operationConnect.setId(existConnect.getId());
            int updateCount = connectMapper.updateByIdSelective(operationConnect);
            result = updateCount > 0;
        }
        return RedissonxResponseBuilder.success(result);
    }

    @Override
    public RedissonxResponse<Boolean> delete(String connectName, String operator) {
        //判断连接是否被使用
        ClusterCondition clusterCondition = new ClusterCondition();
        clusterCondition.createCriteria().andConnectNameEqualTo(connectName);
        long existConenctCluster = clusterMapper.countByCondition(clusterCondition);
        if (existConenctCluster > 0) {
            return RedissonxResponseBuilder.fail(453, "该连接正在被使用，请先删除对应的集群");
        } else {
            ConnectCondition condition = new ConnectCondition();
            condition.createCriteria().andConnectNameEqualTo(connectName);
            Integer deleteCount = connectMapper.deleteByCondition(condition);
            if (deleteCount > 0) {
                return RedissonxResponseBuilder.success(true);
            } else {
                return RedissonxResponseBuilder.fail(465, "删除对应连接失败!");

            }
        }
    }

    private void buildCriteria(ConnectCondition.Criteria criteria, ConnectQueryRequest request) {
        if (StringUtils.isNotEmpty(request.getKeyword())) {
            criteria.andConnectNameLike("%" + request.getKeyword() + "%");
        }
        if (StringUtils.isNotEmpty(request.getSource())) {
            criteria.andSourceEqualTo(request.getSource());
        }
    }

    public static ConnectBO toBO(Connect source) {
        ConnectBO target = new ConnectBO();
        target.setConnectName(source.getConnectName());
        String address = source.getAddress();
        if (StringUtils.isNotBlank(address)) {
            target.setAddressList(new ArrayList<>(Arrays.asList(StringUtils.split(address, ","))));
        }
        target.setUseHttpsMode(source.getUseHttpsMode() == 1);
        target.setSource(source.getSource());
        target.setAuthMode(source.getAuthMode());
        if (StringUtils.isNotBlank(source.getAuthInfo())) {
            switch (source.getAuthMode()) {
                case "password":
                    target.setPassword(JSON.parseObject(source.getAuthInfo(), new TypeReference<ConnectPasswordBO>() {
                    }));
                    break;
                case "ssh":
                    target.setSsh(JSON.parseObject(source.getAuthInfo(), new TypeReference<ConnectSSHBO>() {
                    }));
                    break;
                case "none":
                    break;
            }
        }
        return target;
    }

    public static ConnectVO toVO(Connect source) {
        ConnectVO target = new ConnectVO();
        target.setConnectName(source.getConnectName());
        String address = source.getAddress();
        if (StringUtils.isNotBlank(address)) {
            target.setAddressList(Arrays.asList(StringUtils.split(address, ",")));
        }
        target.setUseHttpsMode(source.getUseHttpsMode() == 1);
        target.setAddress(source.getAddress());
        target.setSource(source.getSource());
        target.setAuthMode(source.getAuthMode());
        if (StringUtils.isNotBlank(source.getAuthInfo())) {
            switch (source.getAuthMode()) {
                case "password":
                    target.setPassword(JSON.parseObject(source.getAuthInfo(), new TypeReference<ConnectPasswordBO>() {
                    }));
                    break;
                case "ssh":
                    target.setSsh(JSON.parseObject(source.getAuthInfo(), new TypeReference<ConnectSSHBO>() {
                    }));
                    break;
                case "none":
                    break;
            }
        }
        return target;
    }

    public static Connect toDBEntity(ConnectVO source) {
        Connect target = new Connect();
        target.setConnectName(source.getConnectName());
        target.setAddress(source.getAddress());
        target.setUseHttpsMode(source.getUseHttpsMode() ? 1 : 0);
        target.setSource(source.getSource());
        target.setAuthMode(source.getAuthMode());
        if (StringUtils.isNotBlank(source.getAuthMode())) {
            switch (source.getAuthMode()) {
                case "password":
                    if (null != source.getPassword()) {
                        target.setAuthInfo(JSON.toJSONString(source.getPassword()));
                    }
                    break;
                case "ssh":
                    if (null != source.getSsh()) {
                        target.setAuthInfo(JSON.toJSONString(source.getSsh()));
                    }
                    break;
                case "none":
                    break;

            }
        }
        return target;
    }
}
