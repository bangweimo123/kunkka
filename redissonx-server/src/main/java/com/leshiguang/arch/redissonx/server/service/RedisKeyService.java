package com.leshiguang.arch.redissonx.server.service;

import com.leshiguang.arch.redissonx.server.domain.rediskey.RedisKeyValueVO;
import com.leshiguang.redissonx.common.base.RedissonxResponse;

import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-25 12:44
 * @Modify
 */
public interface RedisKeyService {
    RedissonxResponse<RedisKeyValueVO> keyvalue(String clusterName, String region, String category, String key);

    RedissonxResponse<Boolean> keyValueSave(String clusterName, String region, String category, Integer tenantId, Object value, Object... params);

    RedissonxResponse<Boolean> deleteKey(String clusterName, String region, String category, String key);
}
