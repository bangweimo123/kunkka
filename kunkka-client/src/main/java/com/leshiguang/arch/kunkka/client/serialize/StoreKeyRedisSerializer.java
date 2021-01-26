package com.leshiguang.arch.kunkka.client.serialize;

import com.leshiguang.arch.kunkka.client.StoreKey;
import com.leshiguang.arch.kunkka.client.config.CategoryConfig;
import com.leshiguang.arch.kunkka.client.config.CategoryConfigManager;
import com.leshiguang.arch.kunkka.client.exception.KunkkaUnsupportMethodException;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.nio.charset.Charset;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-11 13:39
 * @Description
 */
public class StoreKeyRedisSerializer implements RedisSerializer<StoreKey> {
    private final Charset charset;
    private CategoryConfigManager categoryConfigManager;

    public StoreKeyRedisSerializer(CategoryConfigManager categoryConfigManager) {
        this.categoryConfigManager = categoryConfigManager;
        this.charset = Charset.defaultCharset();
    }

    @Override
    public byte[] serialize(StoreKey storeKey) throws SerializationException {
        CategoryConfig categoryConfig = categoryConfigManager.getConfig(storeKey.getCategory());
        String finalKey = categoryConfig.getFinalKey(storeKey);
        return finalKey.getBytes(charset);
    }

    @Override
    public StoreKey deserialize(byte[] bytes) throws SerializationException {
        throw new KunkkaUnsupportMethodException();
    }

    @Override
    public Class<?> getTargetType() {
        return StoreKey.class;
    }
}
