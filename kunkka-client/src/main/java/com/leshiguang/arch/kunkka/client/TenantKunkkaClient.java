package com.leshiguang.arch.kunkka.client;

import com.leshiguang.arch.kunkka.client.config.CategoryConfig;
import com.leshiguang.arch.kunkka.client.exception.KunkkaUnsupportMethodException;
import org.springframework.data.redis.core.AbstractKunkkaClientImpl;

import java.io.Serializable;

/**
 * @Author bangwei.mo
 * @Date 2021-01-26 16:43
 * @Description
 */
public class TenantKunkkaClient<V extends Serializable> extends AbstractKunkkaClientImpl<TenantStoreKey, V> {

    public TenantKunkkaClient(String clusterName) {
        super(clusterName);
    }

    public TenantKunkkaClient(String clusterName, String region) {
        super(clusterName, region);
    }

    @Override
    protected CategoryConfig processCategoryConfig(TenantStoreKey key) {
        CategoryConfig categoryConfig = categoryConfigManager.getConfig(key.getCategory());
        if (!categoryConfig.getMultiTenant()) {
            throw new KunkkaUnsupportMethodException();
        }
        return categoryConfig;
    }
}
