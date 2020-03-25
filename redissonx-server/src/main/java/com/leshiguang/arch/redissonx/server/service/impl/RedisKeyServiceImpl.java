package com.leshiguang.arch.redissonx.server.service.impl;

import com.leshiguang.arch.redissonx.client.StoreKey;
import com.leshiguang.arch.redissonx.server.domain.rediskey.RedisKeyValueVO;
import com.leshiguang.arch.redissonx.server.service.CategoryService;
import com.leshiguang.arch.redissonx.server.service.RedisKeyService;
import com.leshiguang.redissonx.common.base.RedissonxResponse;
import com.leshiguang.redissonx.common.base.RedissonxResponseBuilder;
import org.redisson.RedissonxClient;
import org.redisson.api.RBucket;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-25 12:45
 * @Modify
 */
@Service
public class RedisKeyServiceImpl implements RedisKeyService {
    @Resource
    private CategoryService categoryService;

    @Override
    public RedissonxResponse<RedisKeyValueVO> keyvalue(String clusterName, String category, String key) {
        RedissonxClient redissonxClient = categoryService.getClientByClusterName(clusterName);
        RBucket<Object> bucket = redissonxClient.getBucket(key);
        long remainTimeToLive = bucket.remainTimeToLive();
        Object data = bucket.get();
        RedisKeyValueVO valueVO = new RedisKeyValueVO();
        valueVO.setData(data);
        valueVO.setRemainTimeToLive(remainTimeToLive);
        return RedissonxResponseBuilder.success(valueVO);
    }

    @Override
    public RedissonxResponse<Boolean> keyValueSave(String clusterName, String category, Object value, Object... params) {
        RedissonxClient redissonxClient = categoryService.getClientByClusterName(clusterName);
        StoreKey storeKey = new StoreKey(category, params);
        RBucket<Object> bucket = redissonxClient.getBucket(storeKey);
        bucket.set(value);
        return RedissonxResponseBuilder.success(true);
    }

    @Override
    public RedissonxResponse<Boolean> deleteKey(String clusterName, String category, String key) {
        RedissonxClient redissonxClient = categoryService.getClientByClusterName(clusterName);
        RBucket<Object> bucket = redissonxClient.getBucket(key);
        boolean deleteResult = bucket.delete();
        return RedissonxResponseBuilder.success(deleteResult);
    }
}
