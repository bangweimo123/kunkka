package com.leshiguang.arch.redissonx.config.auth;

import com.leshiguang.arch.redissonx.client.StoreKey;
import com.leshiguang.arch.redissonx.client.TenantStoreKey;
import com.leshiguang.redissonx.common.enums.StrategyOperate;

import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-09-11 17:25
 * @Description
 */
public class TenantAuthStrategy implements RuntimeAuthStrategy {
    private List<Integer> tenantList;

    private StrategyOperate operate;


    public TenantAuthStrategy(List<Integer> tenantList, StrategyOperate operate) {
        this.tenantList = tenantList;
        this.operate = operate;
    }

    @Override
    public boolean authorize(StoreKey storeKey) {
        if (storeKey instanceof TenantStoreKey) {
            TenantStoreKey tenantStoreKey = (TenantStoreKey) storeKey;
            switch (operate) {
                case in:
                    return tenantList.contains(tenantStoreKey.getTenantId());
                case notIn:
                    return !tenantList.contains(tenantStoreKey.getTenantId());
            }
            return false;
        } else {
            return true;
        }
    }
}
