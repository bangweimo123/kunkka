package org.springframework.data.redis.core;

import com.leshiguang.arch.kunkka.client.StoreKey;
import com.leshiguang.arch.kunkka.client.config.CategoryConfig;
import org.springframework.data.redis.connection.DataType;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @Author bangwei.mo
 * @Date 2021-01-26 16:15
 * @Description
 */
public class KunkkaBoundSetOperations<K extends StoreKey, V> extends KunkkaBoundKeyOperations<K> implements BoundSetOperations<K, V> {
    private final SetOperations<K, V> ops;

    /**
     * Constructs a new <code>DefaultBoundSetOperations</code> instance.
     *
     * @param key
     * @param operations
     */
    KunkkaBoundSetOperations(CategoryConfig categoryConfig, K key, RedisOperations<K, V> operations) {
        super(categoryConfig, key, operations);
        this.ops = operations.opsForSet();
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundSetOperations#add(java.lang.Object[])
     */
    @Override
    public Long add(V... values) {
        return new MonitorCommand(MonitorMethod.create("add").setExpireable().setBatch(values.length), getCategoryConfig()).execute(getKey(), () -> ops.add(getKey(), values));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundSetOperations#diff(java.lang.Object)
     */
    @Override
    public Set<V> diff(K key) {
        return new MonitorCommand(MonitorMethod.create("diff"), getCategoryConfig()).execute(getKey(), () -> ops.difference(getKey(), key));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundSetOperations#diff(java.util.Collection)
     */
    @Override
    public Set<V> diff(Collection<K> keys) {
        return new MonitorCommand(MonitorMethod.create("diff").setBatch(keys.size()), getCategoryConfig()).execute(getKey(), () -> ops.difference(getKey(), keys));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundSetOperations#diffAndStore(java.lang.Object, java.lang.Object)
     */
    @Override
    public void diffAndStore(K key, K destKey) {
        new MonitorCommand(MonitorMethod.create("diffAndStore"), getCategoryConfig()).execute(getKey(), () -> ops.differenceAndStore(getKey(), key, destKey));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundSetOperations#diffAndStore(java.util.Collection, java.lang.Object)
     */
    @Override
    public void diffAndStore(Collection<K> keys, K destKey) {
        new MonitorCommand(MonitorMethod.create("diffAndStore").setBatch(keys.size()), getCategoryConfig()).execute(getKey(), () -> ops.differenceAndStore(getKey(), keys, destKey));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundSetOperations#getOperations()
     */
    @Override
    public RedisOperations<K, V> getOperations() {
        return ops.getOperations();
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundSetOperations#intersect(java.lang.Object)
     */
    @Override
    public Set<V> intersect(K key) {
        return new MonitorCommand(MonitorMethod.create("intersect"), getCategoryConfig()).execute(getKey(), () -> ops.intersect(getKey(), key));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundSetOperations#intersect(java.util.Collection)
     */
    @Override
    public Set<V> intersect(Collection<K> keys) {
        return new MonitorCommand(MonitorMethod.create("intersect").setBatch(keys.size()), getCategoryConfig()).execute(getKey(), () -> ops.intersect(getKey(), keys));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundSetOperations#intersectAndStore(java.lang.Object, java.lang.Object)
     */
    @Override
    public void intersectAndStore(K key, K destKey) {
        new MonitorCommand(MonitorMethod.create("intersectAndStore"), getCategoryConfig()).execute(getKey(), () -> ops.intersectAndStore(getKey(), key, destKey));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundSetOperations#intersectAndStore(java.util.Collection, java.lang.Object)
     */
    @Override
    public void intersectAndStore(Collection<K> keys, K destKey) {
        new MonitorCommand(MonitorMethod.create("intersectAndStore").setBatch(keys.size()), getCategoryConfig()).execute(getKey(), () -> ops.intersectAndStore(getKey(), keys, destKey));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundSetOperations#isMember(java.lang.Object)
     */
    @Override
    public Boolean isMember(Object o) {
        return new MonitorCommand(MonitorMethod.create("isMember"), getCategoryConfig()).execute(getKey(), () -> ops.isMember(getKey(), o));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundSetOperations#members()
     */
    @Override
    public Set<V> members() {
        return new MonitorCommand(MonitorMethod.create("members"), getCategoryConfig()).execute(getKey(), () -> ops.members(getKey()));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundSetOperations#move(java.lang.Object, java.lang.Object)
     */
    @Override
    public Boolean move(K destKey, V value) {
        return new MonitorCommand(MonitorMethod.create("move"), getCategoryConfig()).execute(getKey(), () -> ops.move(getKey(), value, destKey));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundSetOperations#randomMember()
     */
    @Override
    public V randomMember() {
        return new MonitorCommand(MonitorMethod.create("randomMember"), getCategoryConfig()).execute(getKey(), () -> ops.randomMember(getKey()));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundSetOperations#distinctRandomMembers(long)
     */
    @Override
    public Set<V> distinctRandomMembers(long count) {
        return new MonitorCommand(MonitorMethod.create("distinctRandomMembers"), getCategoryConfig()).execute(getKey(), () -> ops.distinctRandomMembers(getKey(), count));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundSetOperations#randomMembers(long)
     */
    @Override
    public List<V> randomMembers(long count) {
        return new MonitorCommand(MonitorMethod.create("randomMembers"), getCategoryConfig()).execute(getKey(), () -> ops.randomMembers(getKey(), count));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundSetOperations#remove(java.lang.Object[])
     */
    @Override
    public Long remove(Object... values) {
        return new MonitorCommand(MonitorMethod.create("remove").setBatch(values.length), getCategoryConfig()).execute(getKey(), () -> ops.remove(getKey(), values));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundSetOperations#pop()
     */
    @Override
    public V pop() {
        return new MonitorCommand(MonitorMethod.create("pop"), getCategoryConfig()).execute(getKey(), () -> ops.pop(getKey()));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundSetOperations#size()
     */
    @Override
    public Long size() {
        return new MonitorCommand(MonitorMethod.create("size"), getCategoryConfig()).execute(getKey(), () -> ops.size(getKey()));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundSetOperations#union(java.lang.Object)
     */
    @Override
    public Set<V> union(K key) {
        return new MonitorCommand(MonitorMethod.create("union"), getCategoryConfig()).execute(getKey(), () -> ops.union(getKey(), key));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundSetOperations#union(java.util.Collection)
     */
    @Override
    public Set<V> union(Collection<K> keys) {
        return new MonitorCommand(MonitorMethod.create("union").setBatch(keys.size()), getCategoryConfig()).execute(getKey(), () -> ops.union(getKey(), keys));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundSetOperations#unionAndStore(java.lang.Object, java.lang.Object)
     */
    @Override
    public void unionAndStore(K key, K destKey) {
        new MonitorCommand(MonitorMethod.create("unionAndStore"), getCategoryConfig()).execute(getKey(), () -> ops.unionAndStore(getKey(), key, destKey));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundSetOperations#unionAndStore(java.util.Collection, java.lang.Object)
     */
    @Override
    public void unionAndStore(Collection<K> keys, K destKey) {
        new MonitorCommand(MonitorMethod.create("unionAndStore").setBatch(keys.size()), getCategoryConfig()).execute(getKey(), () -> ops.unionAndStore(getKey(), keys, destKey));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundKeyOperations#getType()
     */
    @Override
    public DataType getType() {
        return DataType.SET;
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundSetOperations#scan(org.springframework.data.redis.core.ScanOptions)
     */
    @Override
    public Cursor<V> scan(ScanOptions options) {
        return new MonitorCommand(MonitorMethod.create("scan").setBatch(options.getCount()), getCategoryConfig()).execute(getKey(), () -> ops.scan(getKey(), options));
    }
}
