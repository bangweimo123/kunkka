package com.leshiguang.arch.kunkka.client;

import org.springframework.data.redis.core.AbstractKunkkaClientImpl;

import java.io.Serializable;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-26 16:59
 * @Description
 */
public class DefaultKunkkaClient<V extends Serializable> extends AbstractKunkkaClientImpl<StoreKey, V> {
    public DefaultKunkkaClient(String clusterName) {
        super(clusterName);
    }

    public DefaultKunkkaClient(String clusterName, String region) {
        super(clusterName, region);
    }
}
