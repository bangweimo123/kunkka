package com.leshiguang.arch.redissonx.server.service.impl;

import com.alibaba.fastjson.JSON;
import com.leshiguang.arch.redissonx.client.StoreKey;
import com.leshiguang.arch.redissonx.client.TenantStoreKey;
import com.leshiguang.arch.redissonx.server.domain.rediskey.MValueBO;
import com.leshiguang.arch.redissonx.server.domain.rediskey.RedisKeyValueVO;
import com.leshiguang.arch.redissonx.server.service.CategoryService;
import com.leshiguang.arch.redissonx.server.service.RedisKeyService;
import com.leshiguang.redissonx.common.base.RedissonxResponse;
import com.leshiguang.redissonx.common.base.RedissonxResponseBuilder;
import com.leshiguang.redissonx.common.enums.RedisKeyType;
import org.apache.commons.lang3.StringUtils;
import org.redisson.RedissonxClient;
import org.redisson.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-25 12:45
 * @Modify
 */
@Service
public class RedisKeyServiceImpl implements RedisKeyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedisKeyServiceImpl.class);
    @Resource
    private CategoryService categoryService;

    @Override
    public RedissonxResponse<RedisKeyValueVO> keyvalue(String clusterName, String region, String category, String key, String type) {
        RedissonxClient redissonxClient = categoryService.getClientByClusterName(clusterName, region);
        RedisKeyValueVO valueVO = new RedisKeyValueVO();
        if (StringUtils.isBlank(type)) {
            type = "string";
        }
        RedisKeyType redisKeyType = RedisKeyType.parse(type);
        if (null == redisKeyType) {
            return RedissonxResponseBuilder.fail(402, "不支持的数据类型");
        }
        long remainTimeToLive = -1l;
        try {
            switch (redisKeyType) {
                case string:
                    RBucket bucket = redissonxClient.getBucket(key);
                    remainTimeToLive = bucket.remainTimeToLive();
                    Object data = bucket.get();
                    valueVO.setData(JSON.toJSONString(data));
                    valueVO.setRemainTimeToLive(remainTimeToLive);
                    break;
                case list:
                    RList list = redissonxClient.getList(key);
                    remainTimeToLive = list.remainTimeToLive();
                    List<Object> listData = list.readAll();
                    valueVO.setData(JSON.toJSONString(listData));
                    valueVO.setRemainTimeToLive(remainTimeToLive);
                    break;
                case set:
                    RSet set = redissonxClient.getSet(key);
                    remainTimeToLive = set.remainTimeToLive();
                    Set<Object> setData = set.readAll();
                    valueVO.setData(JSON.toJSONString(setData));
                    valueVO.setRemainTimeToLive(remainTimeToLive);
                    break;
                case hash:
                    RMap map = redissonxClient.getMap(key);
                    remainTimeToLive = map.remainTimeToLive();
                    Map mapData = map.readAllMap();
                    valueVO.setData(JSON.toJSONString(mapData));
                    valueVO.setRemainTimeToLive(remainTimeToLive);
                    break;
                case sortSet:
                    RSortedSet sortedSet = redissonxClient.getSortedSet(key);
                    Collection sortedSetData = sortedSet.readAll();
                    valueVO.setData(JSON.toJSONString(sortedSetData));
                    valueVO.setRemainTimeToLive(remainTimeToLive);
                    break;
            }
        } catch (Exception e) {
            LOGGER.warn("value parse error", e);
            return RedissonxResponseBuilder.fail(405, "数据类型错误");
        }
        return RedissonxResponseBuilder.success(valueVO);
    }

    @Override
    public RedissonxResponse<Boolean> keyValueSave(String clusterName, String region, String category, Integer tenantId, MValueBO value, Object... params) {
        RedissonxClient redissonxClient = categoryService.getClientByClusterName(clusterName, region);
        if (redissonxClient == null) {
            return RedissonxResponseBuilder.fail(405, "该区域未找到client连接，请查看是否绑定连接!");
        }
        StoreKey storeKey;
        if (tenantId != null && tenantId > 0) {
            storeKey = new TenantStoreKey(category, tenantId, params);
        } else {
            storeKey = new StoreKey(category, params);
        }
        RedisKeyType redisKeyType = RedisKeyType.parse(value.getKeyType());
        switch (redisKeyType) {
            case string:
                RBucket bucket = redissonxClient.getBucket(storeKey);
                bucket.set(value.parse());
                break;
            case list:
                RList list = redissonxClient.getList(storeKey);
                if (value.getSingle()) {
                    list.add(value.parse());
                } else {
                    list.addAll(value.parseAll());
                }
                break;
            case set:
                RSet set = redissonxClient.getSet(storeKey);
                if (value.getSingle()) {
                    set.add(value.parse());
                } else {
                    set.addAll(value.parseAll());
                }
                break;
            case hash:
                RMap map = redissonxClient.getMap(storeKey);
                map.putAll(value.parseAll());
                break;
            case sortSet:
                RSortedSet sortedSet = redissonxClient.getSortedSet(storeKey);
                if (value.getSingle()) {
                    sortedSet.add(value.parse());
                } else {
                    sortedSet.addAll(value.parseAll());
                }
                break;
        }
        return RedissonxResponseBuilder.success(true);
    }

    @Override
    public RedissonxResponse<Boolean> deleteKey(String clusterName, String region, String category, String key) {
        RedissonxClient redissonxClient = categoryService.getClientByClusterName(clusterName, region);
        RBucket<Object> bucket = redissonxClient.getBucket(key);
        boolean deleteResult = bucket.delete();
        return RedissonxResponseBuilder.success(deleteResult);
    }
}
