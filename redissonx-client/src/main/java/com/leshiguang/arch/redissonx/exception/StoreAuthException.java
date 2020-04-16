package com.leshiguang.arch.redissonx.exception;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-04-16 15:07
 * @Modify
 */
public class StoreAuthException extends StoreException {
    public StoreAuthException(Throwable cause) {
        super(cause);
    }

    public StoreAuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public StoreAuthException(String message) {
        super(message);
    }

    public StoreAuthException() {
        super();
    }
}
