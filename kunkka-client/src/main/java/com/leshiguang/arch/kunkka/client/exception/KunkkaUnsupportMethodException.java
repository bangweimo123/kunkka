package com.leshiguang.arch.kunkka.client.exception;

import com.leshiguang.arch.kunkka.common.exception.KunkkaException;

/**
 * @Author bangwei.mo
 * @Date 2021-01-11 14:31
 * @Description
 */
public class KunkkaUnsupportMethodException extends KunkkaException {

    public KunkkaUnsupportMethodException() {
        super(ClientErrorCode.UNSUPPORT_METHOD_ERROR);
    }

    public KunkkaUnsupportMethodException(Throwable cause) {
        super(ClientErrorCode.UNSUPPORT_METHOD_ERROR, cause);
    }
}
