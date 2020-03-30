package com.leshiguang.arch.redissonx.config.zookeeper;

import com.leshiguang.arch.redissonx.config.store.StoreCategoryConfig;
import com.leshiguang.redissonx.common.zookeeper.ConfigListenable;

import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-16 13:01
 * @Modify
 */
public interface StoreConfigClient extends ConfigListenable {
    /**
     * 获取一个cluster是否严格鉴权
     *
     * @param clusterName
     * @return
     */
    Boolean isStrictAuth(String clusterName);

    /**
     * 获取一个cluster有效的应用列表
     *
     * @param clusterName
     * @return
     */
    List<String> loadAuthApps(String clusterName);

    /**
     * 获取一个category的配置
     *
     * @param category
     * @return
     */
    StoreCategoryConfig getStoreCategoryConfig(String clusterName, String category);
}
