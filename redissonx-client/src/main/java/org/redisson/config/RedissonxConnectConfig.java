package org.redisson.config;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-04-15 16:53
 * @Modify
 */
@Data
public class RedissonxConnectConfig implements Serializable {
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

    /**
     * 订阅的连接最小空闲大小
     */
    private Integer subscriptionConnectMinimumIdleSize;
    /**
     * 订阅的连接池大小
     */
    private Integer subscriptionConnectPoolSize;
    /**
     * 最小空闲连接数
     */
    private Integer minIdleSize;
    /**
     * 连接池大小
     */
    private Integer connectPoolSize;
}
