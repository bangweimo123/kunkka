package org.redisson;

import com.google.common.cache.LoadingCache;
import org.redission.config.GuavaCacheHolder;
import org.redisson.client.codec.Codec;
import org.redisson.command.CommandAsyncExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-26 13:34
 * @Modify 使用guava cache实现bucket
 */
public class RedissonxLocalBucket<V> extends RedissonBucket<V> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RedissonxLocalBucket.class);
    private LoadingCache<String, V> cache;

    public RedissonxLocalBucket(CommandAsyncExecutor connectionManager, String name, GuavaCacheHolder cacheHolder) {
        super(connectionManager, name);
        this.cache = cacheHolder.getCache(this);
    }

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
}
