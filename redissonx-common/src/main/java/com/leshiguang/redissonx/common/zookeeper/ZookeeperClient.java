package com.leshiguang.redissonx.common.zookeeper;

import com.leshiguang.redissonx.common.entity.category.CategoryBO;
import com.leshiguang.redissonx.common.entity.cluster.ClusterBO;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-20 14:28
 * @Modify
 */
public interface ZookeeperClient extends CategoryConfigListenable, AuthStrategyConfigListenable, ConnectConfigListenable {
    /**
     * 加载cluster
     *
     * @param clusterName
     * @return
     */
    ClusterBO getCluster(String clusterName);

    /**
     * 设置cluster
     *
     * @param cluster
     */
    boolean setCluster(String clusterName, ClusterBO cluster);

    /**
     * 判断集群是否存在
     *
     * @param clusterName
     * @return
     */
    boolean existCluster(String clusterName);

    /**
     * 删除集群
     *
     * @param clusterName
     * @return
     */
    boolean deleteCluster(String clusterName);

    /**
     * 获取一个category的配置
     *
     * @param category
     * @return
     */
    CategoryBO getCategory(String clusterName, String category);

    /**
     * 设置category
     *
     * @param config
     * @return
     */
    boolean setCategory(String clusterName, CategoryBO config);


}
