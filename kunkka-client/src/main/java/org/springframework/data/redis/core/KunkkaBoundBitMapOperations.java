package org.springframework.data.redis.core;

import com.leshiguang.arch.kunkka.client.StoreKey;
import com.leshiguang.arch.kunkka.client.config.CategoryConfig;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.util.Assert;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-02-02 21:05
 * @Description
 */
public class KunkkaBoundBitMapOperations<K extends StoreKey> extends KunkkaBoundKeyOperations<K> implements BoundBitMapOperations<K> {
    private final ValueOperations<K, Object> ops;

    private final RedisOperations<K, Object> rOps;

    /**
     * Constructs a new {@code DefaultBoundGeoOperations}.
     *
     * @param key        must not be {@literal null}.
     * @param operations must not be {@literal null}.
     */
    KunkkaBoundBitMapOperations(CategoryConfig categoryConfig, K key, RedisOperations operations) {
        super(categoryConfig, key, operations);
        rOps = operations;
        this.ops = operations.opsForValue();
    }

    @SuppressWarnings("unchecked")
    private byte[] rawKey(Object key) {
        Assert.notNull(key, "non null key required");
        if (rOps.getKeySerializer() == null && key instanceof byte[]) {
            return (byte[]) key;
        }

        return ((RedisSerializer) rOps.getKeySerializer()).serialize(key);
    }

    @Override
    public Boolean setBit(long offset, boolean tag) {
        return ops.setBit(getKey(), offset, tag);
    }

    @Override
    public Boolean getBit(long offset) {
        return ops.getBit(getKey(), offset);
    }

    @Override
    public Long count() {
        final byte[] rawKey = rawKey(getKey());
        return rOps.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                return connection.bitCount(rawKey);
            }
        });
    }
}
