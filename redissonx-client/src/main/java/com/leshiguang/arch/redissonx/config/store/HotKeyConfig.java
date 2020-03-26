package com.leshiguang.arch.redissonx.config.store;

import java.io.Serializable;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-16 13:07
 * @Modify 热点key 处理1:流控，处理2：使用本地缓存
 */
public class HotKeyConfig implements Serializable {
    private Boolean useLocalCache;

    private Integer maximumSize = 1000;

    private Integer maximumWeight = 1000;

    private String expireAfterWrite = "10m";

    public Boolean getUseLocalCache() {
        return useLocalCache;
    }

    public void setUseLocalCache(Boolean useLocalCache) {
        this.useLocalCache = useLocalCache;
    }

    public Integer getMaximumSize() {
        return maximumSize;
    }

    public void setMaximumSize(Integer maximumSize) {
        this.maximumSize = maximumSize;
    }

    public Integer getMaximumWeight() {
        return maximumWeight;
    }

    public void setMaximumWeight(Integer maximumWeight) {
        this.maximumWeight = maximumWeight;
    }

    public String getExpireAfterWrite() {
        return expireAfterWrite;
    }

    public void setExpireAfterWrite(String expireAfterWrite) {
        this.expireAfterWrite = expireAfterWrite;
    }
}
