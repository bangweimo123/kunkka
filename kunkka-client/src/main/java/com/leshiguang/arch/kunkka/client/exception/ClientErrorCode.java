package com.leshiguang.arch.kunkka.client.exception;

import com.leshiguang.arch.kunkka.common.exception.ErrorCode;

/**
 * @Author bangwei.mo
 * @Date 2021-01-14 14:45
 * @Description
 */
public enum ClientErrorCode implements ErrorCode {
    AUTH_ERROR(502, "权限错误"),
    CONFIG_ERROR(503, "配置项错误:[%s]"),
    TIMEOUT_ERROR(701, "超时错误"),
    UNSUPPORT_METHOD_ERROR(703, "不支持的方法类型"),
    UNVALID_KEY_ERROR(705, "不合法的storekey"),
    CHANGE_DURATION_ERROR(710, "不支持修改失效时间"),
    UNSUPPORT_TYPE_ERROR(721, "不支持的redis类型");

    ClientErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;

    private String msg;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMsg() {
        return msg;
    }
}
