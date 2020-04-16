package com.leshiguang.arch.redissonx.config.auth;

import com.leshiguang.arch.redissonx.client.StoreKey;
import com.leshiguang.arch.redissonx.client.TenantStoreKey;
import com.leshiguang.arch.redissonx.exception.StoreAuthException;
import org.apache.commons.collections4.CollectionUtils;

import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-04-16 15:00
 * @Modify
 */
public class TenantAuthStrategy implements AuthStrategy {
    private List<String> tenantList;

    public List<String> getTenantList() {
        return tenantList;
    }

    public void setTenantList(List<String> tenantList) {
        this.tenantList = tenantList;
    }

    @Override
    public boolean auth(StoreKey storeKey) {
        if (storeKey instanceof TenantStoreKey) {
            TenantStoreKey tenantStoreKey = (TenantStoreKey) storeKey;
            Integer tenantId = tenantStoreKey.getTenantId();
            if (CollectionUtils.isNotEmpty(tenantList)) {
                boolean exist = tenantList.contains(tenantId);
                if (!exist) {
                    throw new StoreAuthException("no auth for category:" + tenantStoreKey.getCategory() + ",with tenantId:" + tenantId);
                }
            }
        }
        return true;
    }
}
