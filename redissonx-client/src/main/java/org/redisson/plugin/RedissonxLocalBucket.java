package org.redisson.plugin;

import com.google.common.cache.LoadingCache;
import org.redisson.client.codec.Codec;
import org.redisson.command.CommandAsyncExecutor;
import org.redisson.config.GuavaCacheHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-26 13:34
 * @Modify 使用guava cache实现bucket
 */
public class RedissonxLocalBucket<V> extends RedissonxBucket<V> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedissonxLocalBucket.class);
    private LoadingCache<String, V> cache;

    public RedissonxLocalBucket(Codec codec, CommandAsyncExecutor connectionManager, String name, GuavaCacheHolder cacheHolder) {
        super(codec, connectionManager, name);
        this.cache = cacheHolder.getCache(this);
    }

    @Override
    public V get() {
        V value = null;
        try {
            value = cache.get(getName());
        } catch (Exception e) {
            LOGGER.warn("can't get value from guava");
        }
        if (null == value) {
            value = super.get();
        }
        return value;
    }

    @Override
    public void set(V value) {
        super.set(value);
        cache.invalidate(getName());
    }


    @Override
    public boolean compareAndSet(V expect, V update) {
        boolean result = super.compareAndSet(expect, update);
        cache.invalidate(getName());
        return result;
    }

    @Override
    public V getAndSet(V newValue) {
        V result = super.getAndSet(newValue);
        cache.invalidate(getName());
        return result;
    }

    public V getAndDelete() {
        V result = this.get(this.getAndDeleteAsync());
        cache.invalidate(getName());
        return result;
    }
}
