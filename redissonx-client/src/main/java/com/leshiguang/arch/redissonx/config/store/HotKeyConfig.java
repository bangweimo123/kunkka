package com.leshiguang.arch.redissonx.config.store;

import java.io.Serializable;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-16 13:07
 * @Modify 热点key 处理1:流控，处理2：使用本地缓存
 */
public class HotKeyConfig implements Serializable {
    private String hotKey;

    private String clusterName;

    private String category;

    private HotKeyHandleConfig handleConfig;

    public String getHotKey() {
        return hotKey;
    }

    public void setHotKey(String hotKey) {
        this.hotKey = hotKey;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public HotKeyHandleConfig getHandleConfig() {
        return handleConfig;
    }

    public void setHandleConfig(HotKeyHandleConfig handleConfig) {
        this.handleConfig = handleConfig;
    }
}
