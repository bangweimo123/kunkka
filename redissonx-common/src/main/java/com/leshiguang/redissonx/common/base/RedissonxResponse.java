package com.leshiguang.redissonx.common.base;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2019-10-15 10:51
 * @Modify
 */
@Data
public class RedissonxResponse<T> implements Serializable {

    private T data;

    private Boolean success = true;

    private Integer resultCode;

    private String errorMsg;

    public RedissonxResponse(T data) {
        this.data = data;
        this.success = true;
        this.resultCode = 200;
    }

    public RedissonxResponse(Integer resultCode, String errorMsg) {
        this.success = false;
        this.resultCode = resultCode;
        this.errorMsg = errorMsg;
    }

}
