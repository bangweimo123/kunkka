package com.leshiguang.arch.redissonx.exception;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-09-02 17:19
 * @Description
 */
public class ConfigException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1l;

    public ConfigException(Throwable cause) {
        super(cause);
    }

    public ConfigException(String message, Throwable cause) {
        super(message, cause);
    }

    public ConfigException(String message) {
        super(message);
    }

    public ConfigException() {
        super();
    }

}
