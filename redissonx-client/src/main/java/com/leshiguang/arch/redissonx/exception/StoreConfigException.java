package com.leshiguang.arch.redissonx.exception;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-02-12 14:17
 * @Modify
 */
public class StoreConfigException extends StoreException {
    public StoreConfigException(Throwable cause) {
        super(cause);
    }

    public StoreConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public StoreConfigException(String message) {
        super(message);
    }

    public StoreConfigException() {
        super();
    }
}
