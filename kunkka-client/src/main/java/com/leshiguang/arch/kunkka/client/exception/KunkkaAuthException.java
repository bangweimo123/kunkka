package com.leshiguang.arch.kunkka.client.exception;

import com.leshiguang.arch.kunkka.common.exception.KunkkaException;

/**
 * @Author bangwei.mo
 * @Date 2021-01-11 20:12
 * @Description
 */
public class KunkkaAuthException extends KunkkaException {
    public KunkkaAuthException() {
        super(ClientErrorCode.AUTH_ERROR);
    }

    public KunkkaAuthException(Throwable cause) {
        super(ClientErrorCode.AUTH_ERROR, cause);
    }
}
