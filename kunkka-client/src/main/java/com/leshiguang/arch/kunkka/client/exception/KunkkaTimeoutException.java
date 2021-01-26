package com.leshiguang.arch.kunkka.client.exception;

import com.leshiguang.arch.kunkka.common.exception.KunkkaException;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-12 14:38
 * @Description
 */
public class KunkkaTimeoutException extends KunkkaException {

    /**
     *
     */
    private static final long serialVersionUID = 1l;

    public KunkkaTimeoutException() {
        super(ClientErrorCode.TIMEOUT_ERROR);
    }

    public KunkkaTimeoutException(Throwable cause) {
        super(ClientErrorCode.TIMEOUT_ERROR, cause);
    }

}
