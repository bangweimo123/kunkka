package com.leshiguang.redissonx.common.entity.cluster;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-20 13:58
 * @Modify
 */
@Data
public class ClusterConnectBO implements Serializable {
    /**
     * 空闲超时时间
     */
    private Integer idleConenctTimeout;
    /**
     * 超时时间
     */
    private Integer connectTimeout;
    /**
     * 连接重试次数
     */
    private Integer retryAttempts;
    /**
     * 重试间隔时间
     */
    private Integer retryInterval;
}
