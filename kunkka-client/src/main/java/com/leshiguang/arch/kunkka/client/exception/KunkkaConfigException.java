package com.leshiguang.arch.kunkka.client.exception;

import com.leshiguang.arch.kunkka.common.exception.KunkkaException;

/**
 * @Author bangwei.mo
 * @Date 2021-01-12 15:47
 * @Description
 */
public class KunkkaConfigException extends KunkkaException {

    /**
     *
     */
    private static final long serialVersionUID = 1l;

    public KunkkaConfigException() {
        super(ClientErrorCode.CONFIG_ERROR);
    }

    public KunkkaConfigException(Throwable cause) {
        super(ClientErrorCode.CONFIG_ERROR, cause);
    }

    public KunkkaConfigException(String message) {
        super(ClientErrorCode.CONFIG_ERROR, message);
    }

    public KunkkaConfigException(String message, Throwable cause) {
        super(cause, ClientErrorCode.CONFIG_ERROR, message);
    }
}
