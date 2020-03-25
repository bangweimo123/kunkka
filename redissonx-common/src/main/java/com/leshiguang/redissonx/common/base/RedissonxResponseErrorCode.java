package com.leshiguang.redissonx.common.base;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2019-10-15 15:42
 * @Modify
 */
public enum RedissonxResponseErrorCode {
    COMMON_EXCEPTION_ERROR(402, "统一异常返回错误"),
    PARAM_ERROR(500,"参数异常");

    private Integer errorCode;

    private String errorMsg;

    RedissonxResponseErrorCode(Integer errorCode, String errorMsg) {
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }}
