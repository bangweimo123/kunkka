package com.leshiguang.arch.redissonx.config.auth;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-04-16 14:59
 * @Modify
 */
public interface InitAuthStrategy extends Authable {
    boolean initAuthorize();
}
