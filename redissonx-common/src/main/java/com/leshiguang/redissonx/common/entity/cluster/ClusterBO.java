package com.leshiguang.redissonx.common.entity.cluster;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-20 13:35
 * @Modify
 */
@Data
public class ClusterBO implements Serializable {
    private static final long serialVersionUID = -7865144088316178836L;
    /**
     * 集群名
     */
    private String clusterName;
    /**
     * 模式 single/cluster/sentinel/replicate/masterslave
     */
    private String mode;
    /**
     * 连接列表
     */
    private List<ClusterConnectBO> connectList;
    /**
     * 策略列表
     */
    private List<ClusterStrategyBO> strategyList;
}
