package com.leshiguang.arch.redissonx.server.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-04-15 15:06
 * @Modify
 */
@Data
public class ConnectQueryRequest implements Serializable {
    /**
     * 关键字
     */
    private String keyword;
    /**
     * 所属云
     */
    private String region;
    /**
     * 地址
     */
    private String address;
}
