package com.leshiguang.arch.kunkka.common.entity.cluster;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author bangwei.mo
 * @Date 2020-11-11 10:50
 * @Description
 */
@Data
public class ClusterSimpleBO implements Serializable {
    /**
     * 集群名
     */
    private String clusterName;
    /**
     * 模式 single/cluster/sentinel/replicate/masterslave
     */
    private String mode;
    /**
     * 使用的对象
     */
    private String engine;
}
