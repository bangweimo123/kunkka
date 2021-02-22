package org.springframework.data.redis.core;

import com.leshiguang.arch.kunkka.client.StoreKey;
import com.leshiguang.arch.kunkka.client.config.CategoryConfig;
import org.springframework.data.redis.connection.DataType;

import java.io.Serializable;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author bangwei.mo
 * @Date 2021-01-12 15:16
 * @Description
 */
public class KunkkaBoundListOperations<K extends StoreKey, V extends Serializable> extends KunkkaBoundKeyOperations<K> implements BoundListOperations<K, V> {

    private final ListOperations<K, V> ops;

    /**
     * Constructs a new <code>DefaultBoundListOperations</code> instance.
     *
     * @param key
     * @param operations
     */
    KunkkaBoundListOperations(CategoryConfig categoryConfig, K key, RedisOperations<K, V> operations) {
        super(categoryConfig, key, operations);
        this.ops = operations.opsForList();
    }

    @Override
    public RedisOperations<K, V> getOperations() {
        return ops.getOperations();
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundListOperations#index(long)
     */
    @Override
    public V index(long index) {
        return new MonitorCommand(MonitorMethod.create("index"), getCategoryConfig()).execute(getKey(), () -> ops.index(getKey(), index));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundListOperations#leftPop()
     */
    @Override
    public V leftPop() {
        return new MonitorCommand(MonitorMethod.create("leftPop"), getCategoryConfig()).execute(getKey(), () -> ops.leftPop(getKey()));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundListOperations#leftPop(long, java.util.concurrent.TimeUnit)
     */
    @Override
    public V leftPop(long timeout, TimeUnit unit) {
        return new MonitorCommand(MonitorMethod.create("leftPop"), getCategoryConfig()).execute(getKey(), () -> ops.leftPop(getKey(), timeout, unit));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundListOperations#leftPush(java.lang.Object)
     */
    @Override
    public Long leftPush(V value) {
        return new MonitorCommand(MonitorMethod.create("leftPush").setExpireable(), getCategoryConfig()).execute(getKey(), () -> ops.leftPush(getKey(), value));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundListOperations#leftPushAll(java.lang.Object[])
     */
    @Override
    public Long leftPushAll(V... values) {
        return new MonitorCommand(MonitorMethod.create("leftPushAll").setExpireable(), getCategoryConfig()).execute(getKey(), () -> ops.leftPushAll(getKey(), values));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundListOperations#leftPushIfPresent(java.lang.Object)
     */
    @Override
    public Long leftPushIfPresent(V value) {
        return new MonitorCommand(MonitorMethod.create("leftPushIfPresent").setExpireable(), getCategoryConfig()).execute(getKey(), () -> ops.leftPushIfPresent(getKey(), value));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundListOperations#leftPush(java.lang.Object, java.lang.Object)
     */
    @Override
    public Long leftPush(V pivot, V value) {
        return new MonitorCommand(MonitorMethod.create("leftPush").setExpireable(), getCategoryConfig()).execute(getKey(), () -> ops.leftPush(getKey(), pivot, value));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundListOperations#size()
     */
    @Override
    public Long size() {
        return new MonitorCommand(MonitorMethod.create("size"), getCategoryConfig()).execute(getKey(), () -> ops.size(getKey()));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundListOperations#range(long, long)
     */
    @Override
    public List<V> range(long start, long end) {
        return new MonitorCommand(MonitorMethod.create("range"), getCategoryConfig()).execute(getKey(), () -> ops.range(getKey(), start, end));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundListOperations#remove(long, java.lang.Object)
     */
    @Override
    public Long remove(long i, Object value) {
        return new MonitorCommand(MonitorMethod.create("range"), getCategoryConfig()).execute(getKey(), () -> ops.remove(getKey(), i, value));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundListOperations#rightPop()
     */
    @Override
    public V rightPop() {
        return new MonitorCommand(MonitorMethod.create("rightPop"), getCategoryConfig()).execute(getKey(), () -> ops.rightPop(getKey()));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundListOperations#rightPop(long, java.util.concurrent.TimeUnit)
     */
    @Override
    public V rightPop(long timeout, TimeUnit unit) {
        return new MonitorCommand(MonitorMethod.create("rightPop"), getCategoryConfig()).execute(getKey(), () -> ops.rightPop(getKey(), timeout, unit));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundListOperations#rightPushIfPresent(java.lang.Object)
     */
    @Override
    public Long rightPushIfPresent(V value) {
        return new MonitorCommand(MonitorMethod.create("rightPushIfPresent").setExpireable(), getCategoryConfig()).execute(getKey(), () -> ops.rightPushIfPresent(getKey(), value));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundListOperations#rightPush(java.lang.Object)
     */
    @Override
    public Long rightPush(V value) {
        return new MonitorCommand(MonitorMethod.create("rightPush").setExpireable(), getCategoryConfig()).execute(getKey(), () -> ops.rightPush(getKey(), value));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundListOperations#rightPushAll(java.lang.Object[])
     */
    @Override
    public Long rightPushAll(V... values) {
        return new MonitorCommand(MonitorMethod.create("rightPushAll").setExpireable(), getCategoryConfig()).execute(getKey(), () -> ops.rightPushAll(getKey(), values));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundListOperations#rightPush(java.lang.Object, java.lang.Object)
     */
    @Override
    public Long rightPush(V pivot, V value) {
        return new MonitorCommand(MonitorMethod.create("rightPush").setExpireable(), getCategoryConfig()).execute(getKey(), () -> ops.rightPush(getKey(), pivot, value));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundListOperations#trim(long, long)
     */
    @Override
    public void trim(long start, long end) {
        new MonitorCommand(MonitorMethod.create("trim"), getCategoryConfig()).execute(getKey(), () -> ops.trim(getKey(), start, end));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundListOperations#set(long, java.lang.Object)
     */
    @Override
    public void set(long index, V value) {
        new MonitorCommand(MonitorMethod.create("set").setExpireable(), getCategoryConfig()).execute(getKey(), () -> ops.set(getKey(), index, value));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundKeyOperations#getType()
     */
    @Override
    public DataType getType() {
        return DataType.LIST;
    }
}
