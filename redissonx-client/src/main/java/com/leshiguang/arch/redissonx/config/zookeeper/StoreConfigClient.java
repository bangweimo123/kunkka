package com.leshiguang.arch.redissonx.config.zookeeper;

import com.leshiguang.arch.redissonx.config.store.HotKeyConfig;
import com.leshiguang.arch.redissonx.config.store.StoreCategoryConfig;

import java.util.List;
import java.util.Map;

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
     * 获取一个cluster对应的热key
     * <p>
     * key:finalKey value:hotKeyConfig
     *
     * @param clusterName
     * @return
     */
    Map<String, HotKeyConfig> loadHotKeyConfigs(String clusterName);

    /**
     * 获取一个category的配置
     *
     * @param category
     * @return
     */
    StoreCategoryConfig getStoreCategoryConfig(String clusterName, String category);

    /**
     * 设置config
     *
     * @param config
     * @return
     */
    void setStoreCategoryConfig(String clusterName, StoreCategoryConfig config);
}
