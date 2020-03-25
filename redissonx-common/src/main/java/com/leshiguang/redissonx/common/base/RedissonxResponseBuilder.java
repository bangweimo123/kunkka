package com.leshiguang.redissonx.common.base;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2019-10-15 15:28
 * @Modify
 */
public class RedissonxResponseBuilder {
    public static <T> RedissonxResponse<T> success(T data) {
        return new RedissonxResponse<T>(data);
    }

    public static <T> RedissonxResponse<T> fail(Integer errorCode, String errorMsg) {
        return new RedissonxResponse<>(errorCode, errorMsg);
    }

    public static <T> RedissonxResponse<T> fail(Integer errorCode) {
        return new RedissonxResponse<>(errorCode, String.format("统一错误返回，返回码:%d", errorCode));
    }

    public static <T> RedissonxResponse<T> fail(RedissonxResponseErrorCode errorCode) {
        return new RedissonxResponse<>(errorCode.getErrorCode(), errorCode.getErrorMsg());
    }
}
