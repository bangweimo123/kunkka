package com.leshiguang.redissonx.common.entity.cluster;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-20 14:20
 * @Modify
 */
@Data
public class ClusterModeDataBO implements Serializable {
    /**
     * 空闲超时时间
     */
    private Integer idleConenctTimeout;
    /**
     * 超时时间
     */
    private Integer connectTimeout;
    /**
     * ping超时
     */
    private Integer pingTimeout;
    /**
     * 连接重试次数
     */
    private Integer retryAttempts;
    /**
     * 重试间隔时间
     */
    private Integer retryInterval;

    private Integer pingConnectionInterval;

    private Boolean keepAlive;

    private Boolean tcpNoDelay;

    private Integer timeout;

    private Integer subscriptionsPerConnection;
}
