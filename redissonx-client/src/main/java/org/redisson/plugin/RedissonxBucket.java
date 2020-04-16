package org.redisson.plugin;

import com.leshiguang.arch.redissonx.config.store.StoreCategoryConfig;
import org.redisson.RedissonBucket;
import org.redisson.api.RFuture;
import org.redisson.client.codec.Codec;
import org.redisson.client.protocol.RedisCommands;
import org.redisson.command.CommandAsyncExecutor;

import java.util.concurrent.TimeUnit;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-04-16 16:47
 * @Modify
 */
public class RedissonxBucket<V> extends RedissonBucket<V> {
    private StoreCategoryConfig categoryConfig;

    public RedissonxBucket(CommandAsyncExecutor connectionManager, String name) {
        super(connectionManager, name);
    }

    public RedissonxBucket(Codec codec, CommandAsyncExecutor connectionManager, String name) {
        super(codec, connectionManager, name);
    }

    public RedissonxBucket(StoreCategoryConfig categoryConfig, CommandAsyncExecutor connectionManager, String name) {
        super(connectionManager, name);
        this.categoryConfig = categoryConfig;
    }

    public RedissonxBucket(StoreCategoryConfig categoryConfig, Codec codec, CommandAsyncExecutor connectionManager, String name) {
        super(codec, connectionManager, name);
        this.categoryConfig = categoryConfig;
    }

    public RFuture<V> getAndSetAsync(V newValue) {
        if (categoryConfig.getDurationSeconds() != -1) {
            return this.getAndSetAsync(newValue, categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        } else {
            return super.getAndSetAsync(newValue);
        }
    }

    public RFuture<Boolean> trySetAsync(V value) {
        if (categoryConfig.getDurationSeconds() != -1) {
            return this.trySetAsync(value, categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        } else {
            return super.trySetAsync(value);
        }
    }

    public RFuture<Void> setAsync(V value) {
        if (categoryConfig.getDurationSeconds() != -1) {
            return this.setAsync(value, categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
        } else {
            return super.setAsync(value);
        }
    }
}
