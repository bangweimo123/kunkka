package org.redisson.config;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.leshiguang.arch.redissonx.config.hotkey.LocalCacheHotKeyStrategy;
import org.redisson.api.RBucket;

import java.util.concurrent.TimeUnit;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-26 14:09
 * @Modify 一个category 对应一个holder
 */
public class GuavaCacheHolder<V> {
    private LoadingCache<String, V> cache;
    private ThreadLocal<RBucket<V>> currentBucketThreadLocal = new ThreadLocal<>();

    public GuavaCacheHolder(LocalCacheHotKeyStrategy strategy) {
        cache = CacheBuilder.newBuilder()
                .expireAfterWrite(strategy.getDurationInSeconds(), TimeUnit.MINUTES)
                .maximumSize(strategy.getMaximumSize())
                .maximumWeight(strategy.getMaximumWeight())
                .build(new CacheLoader<String, V>() {
                    @Override
                    public V load(String data) throws Exception {
                        return getBucket().get();
                    }
                });
    }

    public RBucket<V> getBucket() {
        return currentBucketThreadLocal.get();
    }

    public LoadingCache<String, V> getCache(RBucket<V> bucket) {
        currentBucketThreadLocal.set(bucket);
        return cache;
    }
}
