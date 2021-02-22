package com.leshiguang.arch.kunkka.client.exception;

import com.leshiguang.arch.kunkka.common.exception.KunkkaException;

/**
 * @Author bangwei.mo
 * @Date 2021-01-12 20:03
 * @Description
 */
public class KunkkaUnValidKeyException extends KunkkaException {
    public KunkkaUnValidKeyException() {
        super(ClientErrorCode.UNVALID_KEY_ERROR);
    }

    public KunkkaUnValidKeyException(Throwable cause) {
        super(ClientErrorCode.UNVALID_KEY_ERROR, cause);
    }
}
