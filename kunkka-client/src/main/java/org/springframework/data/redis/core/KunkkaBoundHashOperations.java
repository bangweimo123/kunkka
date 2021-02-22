package org.springframework.data.redis.core;

import com.leshiguang.arch.kunkka.client.StoreKey;
import com.leshiguang.arch.kunkka.client.config.CategoryConfig;
import org.springframework.data.redis.connection.DataType;
import org.springframework.lang.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author bangwei.mo
 * @Date 2021-01-26 14:37
 * @Description
 */
public class KunkkaBoundHashOperations<K extends StoreKey, HK, HV> extends KunkkaBoundKeyOperations<K> implements BoundHashOperations<K, HK, HV> {
    private final HashOperations<K, HK, HV> ops;

    /**
     * Constructs a new <code>DefaultBoundHashOperations</code> instance.
     *
     * @param key
     * @param operations
     */
    KunkkaBoundHashOperations(CategoryConfig categoryConfig, K key, RedisOperations<K, ?> operations) {
        super(categoryConfig, key, operations);
        this.ops = operations.opsForHash();
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundHashOperations#delete(java.lang.Object[])
     */
    @Override
    public Long delete(Object... keys) {
        return new MonitorCommand(MonitorMethod.create("delete"), getCategoryConfig()).execute(getKey(), () -> ops.delete(getKey(), keys));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundHashOperations#get(java.lang.Object)
     */
    @Override
    public HV get(Object key) {
        return new MonitorCommand(MonitorMethod.create("get"), getCategoryConfig()).execute(getKey(), () -> ops.get(getKey(), key));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundHashOperations#multiGet(java.util.Collection)
     */
    @Override
    public List<HV> multiGet(Collection<HK> hashKeys) {
        return new MonitorCommand(MonitorMethod.create("multiGet").setBatch(hashKeys.size()), getCategoryConfig()).execute(getKey(), () -> ops.multiGet(getKey(), hashKeys));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundHashOperations#getOperations()
     */
    @Override
    public RedisOperations<K, ?> getOperations() {
        return ops.getOperations();
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundHashOperations#hasKey(java.lang.Object)
     */
    @Override
    public Boolean hasKey(Object key) {
        return new MonitorCommand(MonitorMethod.create("hasKey"), getCategoryConfig()).execute(getKey(), () -> ops.hasKey(getKey(), key));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundHashOperations#increment(java.lang.Object, long)
     */
    @Override
    public Long increment(HK key, long delta) {
        return new MonitorCommand(MonitorMethod.create("increment"), getCategoryConfig()).execute(getKey(), () -> ops.increment(getKey(), key, delta));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundHashOperations#increment(java.lang.Object, double)
     */
    @Override
    public Double increment(HK key, double delta) {
        return new MonitorCommand(MonitorMethod.create("increment"), getCategoryConfig()).execute(getKey(), () -> ops.increment(getKey(), key, delta));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundHashOperations#keys()
     */
    @Override
    public Set<HK> keys() {
        return new MonitorCommand(MonitorMethod.create("keys"), getCategoryConfig()).execute(getKey(), () -> ops.keys(getKey()));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundHashOperations#lengthOfValue(java.lang.Object, java.lang.Object)
     */
    @Nullable
    @Override
    public Long lengthOfValue(HK hashKey) {
        return new MonitorCommand(MonitorMethod.create("lengthOfValue"), getCategoryConfig()).execute(getKey(), () -> ops.lengthOfValue(getKey(), hashKey));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundHashOperations#size()
     */
    @Override
    public Long size() {
        return new MonitorCommand(MonitorMethod.create("size"), getCategoryConfig()).execute(getKey(), () -> ops.size(getKey()));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundHashOperations#putAll(java.util.Map)
     */
    @Override
    public void putAll(Map<? extends HK, ? extends HV> m) {
        new MonitorCommand(MonitorMethod.create("putAll").setExpireable().setBatch(m.size()), getCategoryConfig()).execute(getKey(), () -> ops.putAll(getKey(), m));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundHashOperations#put(java.lang.Object, java.lang.Object)
     */
    @Override
    public void put(HK key, HV value) {
        new MonitorCommand(MonitorMethod.create("put").setExpireable(), getCategoryConfig()).execute(getKey(), () -> ops.put(getKey(), key, value));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundHashOperations#putIfAbsent(java.lang.Object, java.lang.Object)
     */
    @Override
    public Boolean putIfAbsent(HK key, HV value) {
        return new MonitorCommand(MonitorMethod.create("putIfAbsent").setExpireable(), getCategoryConfig()).execute(getKey(), () -> ops.putIfAbsent(getKey(), key, value));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundHashOperations#values()
     */
    @Override
    public List<HV> values() {
        return new MonitorCommand(MonitorMethod.create("values"), getCategoryConfig()).execute(getKey(), () -> ops.values(getKey()));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundHashOperations#entries()
     */
    @Override
    public Map<HK, HV> entries() {
        return new MonitorCommand(MonitorMethod.create("entries"), getCategoryConfig()).execute(getKey(), () -> ops.entries(getKey()));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundKeyOperations#getType()
     */
    @Override
    public DataType getType() {
        return DataType.HASH;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundHashOperations#scan(org.springframework.data.redis.core.ScanOptions)
     */
    @Override
    public Cursor<Map.Entry<HK, HV>> scan(ScanOptions options) {
        return new MonitorCommand(MonitorMethod.create("scan").setBatch(options.getCount()), getCategoryConfig()).execute(getKey(), () -> ops.scan(getKey(), options));
    }
}
