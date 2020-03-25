package com.leshiguang.redissonx.common.entity.cluster;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-20 13:35
 * @Modify
 */
@Data
public class ClusterBO<T extends ClusterInnerBO> implements Serializable {
    private static final long serialVersionUID = -7865144088316178836L;
    /**
     * 集群名
     */
    private String clusterName;
    /**
     * 认证方式 password/ssh
     */
    private String authMode;

    private String password;

    private ClusterSSHBO ssh;

    /**
     * 模式 single/cluster/sentinel/replicate/masterslave
     */
    private String mode;
    /**
     * 连接信息
     */
    private ClusterConnectBO connect;
    /**
     * 特殊信息
     */
    private T inner;
}
