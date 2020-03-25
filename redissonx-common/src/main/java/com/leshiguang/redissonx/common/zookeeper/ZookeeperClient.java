package com.leshiguang.redissonx.common.zookeeper;

import com.leshiguang.redissonx.common.entity.category.CategoryBO;
import com.leshiguang.redissonx.common.entity.cluster.ClusterBO;
import com.leshiguang.redissonx.common.entity.hotkey.HotKeyBO;
import org.I0Itec.zkclient.ZkClient;

import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-20 14:28
 * @Modify
 */
public interface ZookeeperClient extends ConfigListenable {
    ZkClient getZkClient();

    /**
     * 加载所有的cluster
     *
     * @return
     */
    List<ClusterBO> loadAllCluster();

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
    void setCluster(ClusterBO cluster);

    boolean deleteCluster(String clusterName);

    List<String> queryCategorys(String clusterName);

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
     * 加载所有的热key
     *
     * @param clusterName
     * @return
     */
    List<HotKeyBO> loadHotKey(String clusterName);

    /**
     * 是否严格鉴权
     *
     * @param clusterName
     * @return
     */
    Boolean isStrictAuth(String clusterName);

    /**
     * 加载鉴权的应用
     *
     * @param clusterName
     * @return
     */
    List<String> authApps(String clusterName);


}
