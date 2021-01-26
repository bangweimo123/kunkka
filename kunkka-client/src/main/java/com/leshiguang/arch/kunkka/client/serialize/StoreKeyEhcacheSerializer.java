package com.leshiguang.arch.kunkka.client.serialize;

import com.leshiguang.arch.kunkka.client.StoreKey;
import com.leshiguang.arch.kunkka.client.config.CategoryConfig;
import com.leshiguang.arch.kunkka.client.config.CategoryConfigManager;
import org.springframework.data.redis.serializer.SerializationException;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-12 20:31
 * @Description
 */
public class StoreKeyEhcacheSerializer {

    private CategoryConfigManager categoryConfigManager;

    public StoreKeyEhcacheSerializer(CategoryConfigManager categoryConfigManager) {
        this.categoryConfigManager = categoryConfigManager;
    }

    public String serialize(StoreKey storeKey) throws SerializationException {
        CategoryConfig categoryConfig = categoryConfigManager.getConfig(storeKey.getCategory());
        String finalKey = categoryConfig.getFinalKey(storeKey);
        return finalKey;
    }
}
