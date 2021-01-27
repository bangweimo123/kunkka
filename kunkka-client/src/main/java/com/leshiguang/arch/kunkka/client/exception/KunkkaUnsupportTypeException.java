package com.leshiguang.arch.kunkka.client.exception;

import com.leshiguang.arch.kunkka.common.exception.KunkkaException;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-27 12:55
 * @Description
 */
public class KunkkaUnsupportTypeException  extends KunkkaException {

    public KunkkaUnsupportTypeException() {
        super(ClientErrorCode.UNSUPPORT_TYPE_ERROR);
    }

    public KunkkaUnsupportTypeException(Throwable cause) {
        super(ClientErrorCode.UNSUPPORT_TYPE_ERROR, cause);
    }
}
