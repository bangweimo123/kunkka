package com.leshiguang.arch.kunkka.client.exception;

import com.leshiguang.arch.kunkka.common.exception.KunkkaException;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-26 15:57
 * @Description
 */
public class KunkkaChangeDurationException extends KunkkaException {

    public KunkkaChangeDurationException() {
        super(ClientErrorCode.CHANGE_DURATION_ERROR);
    }

    public KunkkaChangeDurationException(Throwable cause) {
        super(ClientErrorCode.CHANGE_DURATION_ERROR, cause);
    }
}
