package com.leshiguang.arch.kunkka.client.configure;

import com.leshiguang.arch.kunkka.client.lifecycle.Lifecycle;
import com.leshiguang.arch.kunkka.common.entity.category.CategoryBO;
import com.leshiguang.arch.kunkka.common.entity.cluster.ClusterBO;
import com.leshiguang.arch.kunkka.common.exception.KunkkaException;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-20 14:28
 * @Modify
 */
public interface IConfigureClient extends Lifecycle {
    /**
     * 加载cluster
     *
     * @param clusterName
     * @return
     */
    ClusterBO getCluster(String clusterName) throws KunkkaException;

    /**
     * 设置cluster
     *
     * @param cluster
     */
    boolean setCluster(String clusterName, ClusterBO cluster) throws KunkkaException;

    /**
     * 删除集群
     *
     * @param clusterName
     * @return
     */
    boolean deleteCluster(String clusterName);

    /**
     * 集群监听
     *
     * @param clusterName
     * @param configCallback
     */
    void clusterWatch(String clusterName, IConfigCallback configCallback);

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

    /**
     * 删除category
     *
     * @param clusterName
     * @param category
     * @return
     */
    boolean deleteCategory(String clusterName, String category);

    /**
     * category监听
     *
     * @param clusterName
     * @param category
     * @param configCallback
     */
    void categoryWatch(String clusterName, String category, IConfigCallback configCallback);

}
