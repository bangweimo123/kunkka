package com.leshiguang.arch.redissonx.config.auth;

import com.leshiguang.arch.redissonx.client.StoreKey;

import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-04-16 15:04
 * @Modify
 */
public class ApplicationAuthStrategy implements AuthStrategy {
    private List<String> applicationList;

    public List<String> getApplicationList() {
        return applicationList;
    }

    public void setApplicationList(List<String> applicationList) {
        this.applicationList = applicationList;
    }

    @Override
    public boolean auth(StoreKey storeKey) {
        return true;
    }
}
