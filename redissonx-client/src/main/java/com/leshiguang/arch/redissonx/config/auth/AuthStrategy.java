package com.leshiguang.arch.redissonx.config.auth;

import com.leshiguang.arch.redissonx.client.StoreKey;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-04-16 14:59
 * @Modify
 */
public interface AuthStrategy {
    boolean auth(StoreKey storeKey);
}
