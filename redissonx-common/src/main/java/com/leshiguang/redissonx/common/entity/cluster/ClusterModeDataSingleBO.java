package com.leshiguang.redissonx.common.entity.cluster;

import lombok.Data;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-20 13:43
 * @Modify
 */
@Data
public class ClusterModeDataSingleBO extends ClusterModeDataBO {
    private static final long serialVersionUID = -1490986939320672029L;
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
