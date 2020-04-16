package com.leshiguang.redissonx.common.entity.cluster;

import com.leshiguang.redissonx.common.entity.connect.ConnectBO;
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
     * 连接
     */
    private ConnectBO connect;
    /**
     * 数据库
     */
    private Integer database;
    /**
     * 模式 single/cluster/sentinel/replicate/masterslave
     */
    private String mode;
    /**
     * 支持的租户
     */
    private List<String> tenantList;
    /**
     * 支持的应用
     */
    private List<String> applicationList;
}
