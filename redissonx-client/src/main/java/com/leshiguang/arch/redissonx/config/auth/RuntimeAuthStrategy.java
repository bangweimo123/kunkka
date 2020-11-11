package com.leshiguang.arch.redissonx.config.auth;

import com.leshiguang.arch.redissonx.client.StoreKey;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-11-10 19:14
 * @Description
 */
public interface RuntimeAuthStrategy extends Authable {
    boolean authorize(StoreKey storeKey);
}
