package com.leshiguang.redissonx.common.zookeeper;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-09-09 19:24
 * @Description
 */
@Data
public class ZookeeperConfig implements Serializable {
    private static final long serialVersionUID = 3089545906357447364L;
    private String address;

    private Integer sessionTimeout = 30000;

    private Integer connectTimeout = 2147483647;

    private Long operationRetryTimeout = -1L;
}
