package com.leshiguang.arch.kunkka.client.config.cluster;

import com.leshiguang.arch.kunkka.client.configure.zookeeper.ConfigureClientFactory;
import com.leshiguang.arch.kunkka.common.entity.cluster.ClusterBO;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-13 10:47
 * @Description
 */
public interface ClusterConfigManager {
    /**
     * 通过集群名+区域获取连接配置
     *
     * @param clusterName
     * @param region
     * @return
     */
    ClusterBO getClusterConfig(String clusterName, String region, ConfigureClientFactory configureClientFactory);
}
