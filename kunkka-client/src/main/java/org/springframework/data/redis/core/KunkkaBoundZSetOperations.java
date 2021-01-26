package org.springframework.data.redis.core;

import com.leshiguang.arch.kunkka.client.StoreKey;
import com.leshiguang.arch.kunkka.client.config.CategoryConfig;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisZSetCommands;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-26 16:26
 * @Description
 */
public class KunkkaBoundZSetOperations<K extends StoreKey, V extends Serializable> extends KunkkaBoundKeyOperations<K> implements BoundZSetOperations<K, V> {

    private final ZSetOperations<K, V> ops;

    /**
     * Constructs a new <code>DefaultBoundZSetOperations</code> instance.
     *
     * @param key
     * @param operations
     */
    KunkkaBoundZSetOperations(CategoryConfig categoryConfig, K key, RedisOperations<K, V> operations) {
        super(categoryConfig, key, operations);
        this.ops = operations.opsForZSet();
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundZSetOperations#add(java.lang.Object, double)
     */
    @Override
    public Boolean add(V value, double score) {
        return new MonitorCommand(MonitorMethod.create("add"), getCategoryConfig()).execute(getKey(), () -> ops.add(getKey(), value, score));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundZSetOperations#add(java.util.Set)
     */
    @Override
    public Long add(Set<ZSetOperations.TypedTuple<V>> tuples) {
        return new MonitorCommand(MonitorMethod.create("add"), getCategoryConfig()).execute(getKey(), () -> ops.add(getKey(), tuples));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundZSetOperations#incrementScore(java.lang.Object, double)
     */
    @Override
    public Double incrementScore(V value, double delta) {
        return new MonitorCommand(MonitorMethod.create("incrementScore"), getCategoryConfig()).execute(getKey(), () -> ops.incrementScore(getKey(), value, delta));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundZSetOperations#getOperations()
     */
    @Override
    public RedisOperations<K, V> getOperations() {
        return new MonitorCommand(MonitorMethod.create("getOperations"), getCategoryConfig()).execute(getKey(), () -> ops.getOperations());
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundZSetOperations#intersectAndStore(java.lang.Object, java.lang.Object)
     */
    @Override
    public Long intersectAndStore(K otherKey, K destKey) {
        return new MonitorCommand(MonitorMethod.create("intersectAndStore"), getCategoryConfig()).execute(getKey(), () -> ops.intersectAndStore(getKey(), otherKey, destKey));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundZSetOperations#intersectAndStore(java.util.Collection, java.lang.Object)
     */
    @Override
    public Long intersectAndStore(Collection<K> otherKeys, K destKey) {
        return new MonitorCommand(MonitorMethod.create("intersectAndStore").setBatch(otherKeys.size()), getCategoryConfig()).execute(getKey(), () -> ops.intersectAndStore(getKey(), otherKeys, destKey));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundZSetOperations#intersectAndStore(java.util.Collection, java.lang.Object, org.springframework.data.redis.connection.RedisZSetCommands.Aggregate)
     */
    @Override
    public Long intersectAndStore(Collection<K> otherKeys, K destKey, RedisZSetCommands.Aggregate aggregate) {
        return new MonitorCommand(MonitorMethod.create("intersectAndStore").setBatch(otherKeys.size()), getCategoryConfig()).execute(getKey(), () -> ops.intersectAndStore(getKey(), otherKeys, destKey, aggregate));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundZSetOperations#intersectAndStore(java.util.Collection, java.lang.Object, org.springframework.data.redis.connection.RedisZSetCommands.Aggregate, org.springframework.data.redis.connection.RedisZSetCommands.Weights)
     */
    @Override
    public Long intersectAndStore(Collection<K> otherKeys, K destKey, RedisZSetCommands.Aggregate aggregate, RedisZSetCommands.Weights weights) {
        return new MonitorCommand(MonitorMethod.create("intersectAndStore").setBatch(otherKeys.size()), getCategoryConfig()).execute(getKey(), () -> ops.intersectAndStore(getKey(), otherKeys, destKey, aggregate, weights));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundZSetOperations#range(long, long)
     */
    @Override
    public Set<V> range(long start, long end) {
        return new MonitorCommand(MonitorMethod.create("range"), getCategoryConfig()).execute(getKey(), () -> ops.range(getKey(), start, end));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundZSetOperations#rangeByScore(double, double)
     */
    @Override
    public Set<V> rangeByScore(double min, double max) {
        return new MonitorCommand(MonitorMethod.create("rangeByScore"), getCategoryConfig()).execute(getKey(), () -> ops.rangeByScore(getKey(), min, max));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundZSetOperations#rangeByScoreWithScores(double, double)
     */
    @Override
    public Set<ZSetOperations.TypedTuple<V>> rangeByScoreWithScores(double min, double max) {
        return new MonitorCommand(MonitorMethod.create("rangeByScoreWithScores"), getCategoryConfig()).execute(getKey(), () -> ops.rangeByScoreWithScores(getKey(), min, max));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundZSetOperations#rangeWithScores(long, long)
     */
    @Override
    public Set<ZSetOperations.TypedTuple<V>> rangeWithScores(long start, long end) {
        return new MonitorCommand(MonitorMethod.create("rangeWithScores"), getCategoryConfig()).execute(getKey(), () -> ops.rangeWithScores(getKey(), start, end));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundZSetOperations#reverseRangeByScore(double, double)
     */
    @Override
    public Set<V> reverseRangeByScore(double min, double max) {
        return new MonitorCommand(MonitorMethod.create("reverseRangeByScore"), getCategoryConfig()).execute(getKey(), () -> ops.reverseRangeByScore(getKey(), min, max));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundZSetOperations#reverseRangeByScoreWithScores(double, double)
     */
    @Override
    public Set<ZSetOperations.TypedTuple<V>> reverseRangeByScoreWithScores(double min, double max) {
        return new MonitorCommand(MonitorMethod.create("reverseRangeByScoreWithScores"), getCategoryConfig()).execute(getKey(), () -> ops.reverseRangeByScoreWithScores(getKey(), min, max));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundZSetOperations#reverseRangeWithScores(long, long)
     */
    @Override
    public Set<ZSetOperations.TypedTuple<V>> reverseRangeWithScores(long start, long end) {
        return new MonitorCommand(MonitorMethod.create("reverseRangeWithScores"), getCategoryConfig()).execute(getKey(), () -> ops.reverseRangeWithScores(getKey(), start, end));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundZSetOperations#rangeByLex(org.springframework.data.redis.connection.RedisZSetCommands.Range)
     */
    @Override
    public Set<V> rangeByLex(RedisZSetCommands.Range range) {
        return new MonitorCommand(MonitorMethod.create("rangeByLex"), getCategoryConfig()).execute(getKey(), () -> rangeByLex(range, RedisZSetCommands.Limit.unlimited()));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundZSetOperations#rangeByLex(org.springframework.data.redis.connection.RedisZSetCommands.Range, org.springframework.data.redis.connection.RedisZSetCommands.Limit)
     */
    @Override
    public Set<V> rangeByLex(RedisZSetCommands.Range range, RedisZSetCommands.Limit limit) {
        return new MonitorCommand(MonitorMethod.create("rangeByLex"), getCategoryConfig()).execute(getKey(), () -> ops.rangeByLex(getKey(), range, limit));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundZSetOperations#rank(java.lang.Object)
     */
    @Override
    public Long rank(Object o) {
        return new MonitorCommand(MonitorMethod.create("rank"), getCategoryConfig()).execute(getKey(), () -> ops.rank(getKey(), o));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundZSetOperations#reverseRank(java.lang.Object)
     */
    @Override
    public Long reverseRank(Object o) {
        return new MonitorCommand(MonitorMethod.create("reverseRank"), getCategoryConfig()).execute(getKey(), () -> ops.reverseRank(getKey(), o));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundZSetOperations#score(java.lang.Object)
     */
    @Override
    public Double score(Object o) {
        return new MonitorCommand(MonitorMethod.create("score"), getCategoryConfig()).execute(getKey(), () -> ops.score(getKey(), o));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundZSetOperations#remove(java.lang.Object[])
     */
    @Override
    public Long remove(Object... values) {
        return new MonitorCommand(MonitorMethod.create("remove"), getCategoryConfig()).execute(getKey(), () -> ops.remove(getKey(), values));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundZSetOperations#removeRange(long, long)
     */
    @Override
    public Long removeRange(long start, long end) {
        return new MonitorCommand(MonitorMethod.create("removeRange"), getCategoryConfig()).execute(getKey(), () -> ops.removeRange(getKey(), start, end));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundZSetOperations#removeRangeByScore(double, double)
     */
    @Override
    public Long removeRangeByScore(double min, double max) {
        return new MonitorCommand(MonitorMethod.create("removeRangeByScore"), getCategoryConfig()).execute(getKey(), () -> ops.removeRangeByScore(getKey(), min, max));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundZSetOperations#reverseRange(long, long)
     */
    @Override
    public Set<V> reverseRange(long start, long end) {
        return new MonitorCommand(MonitorMethod.create("reverseRange"), getCategoryConfig()).execute(getKey(), () -> ops.reverseRange(getKey(), start, end));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundZSetOperations#count(double, double)
     */
    @Override
    public Long count(double min, double max) {
        return new MonitorCommand(MonitorMethod.create("count"), getCategoryConfig()).execute(getKey(), () -> ops.count(getKey(), min, max));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundZSetOperations#size()
     */
    @Override
    public Long size() {
        return new MonitorCommand(MonitorMethod.create("size"), getCategoryConfig()).execute(getKey(), () -> zCard());
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundZSetOperations#zCard()
     */
    @Override
    public Long zCard() {
        return new MonitorCommand(MonitorMethod.create("zCard"), getCategoryConfig()).execute(getKey(), () -> ops.zCard(getKey()));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundZSetOperations#unionAndStore(java.lang.Object, java.lang.Object)
     */
    @Override
    public Long unionAndStore(K otherKey, K destKey) {
        return new MonitorCommand(MonitorMethod.create("unionAndStore"), getCategoryConfig()).execute(getKey(), () -> ops.unionAndStore(getKey(), otherKey, destKey));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundZSetOperations#unionAndStore(java.util.Collection, java.lang.Object)
     */
    @Override
    public Long unionAndStore(Collection<K> otherKeys, K destKey) {
        return new MonitorCommand(MonitorMethod.create("unionAndStore"), getCategoryConfig()).execute(getKey(), () -> ops.unionAndStore(getKey(), otherKeys, destKey));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundZSetOperations#unionAndStore(java.util.Collection, java.lang.Object, org.springframework.data.redis.connection.RedisZSetCommands.Aggregate)
     */
    @Override
    public Long unionAndStore(Collection<K> otherKeys, K destKey, RedisZSetCommands.Aggregate aggregate) {
        return new MonitorCommand(MonitorMethod.create("unionAndStore"), getCategoryConfig()).execute(getKey(), () -> ops.unionAndStore(getKey(), otherKeys, destKey, aggregate));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundZSetOperations#unionAndStore(java.util.Collection, java.lang.Object, org.springframework.data.redis.connection.RedisZSetCommands.Aggregate, org.springframework.data.redis.connection.RedisZSetCommands.Weights)
     */
    @Override
    public Long unionAndStore(Collection<K> otherKeys, K destKey, RedisZSetCommands.Aggregate aggregate, RedisZSetCommands.Weights weights) {
        return new MonitorCommand(MonitorMethod.create("unionAndStore"), getCategoryConfig()).execute(getKey(), () -> ops.unionAndStore(getKey(), otherKeys, destKey, aggregate, weights));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundKeyOperations#getType()
     */
    @Override
    public DataType getType() {
        return new MonitorCommand(MonitorMethod.create("add"), getCategoryConfig()).execute(getKey(), () -> DataType.ZSET);
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundZSetOperations#scan(org.springframework.data.redis.core.ScanOptions)
     */
    @Override
    public Cursor<ZSetOperations.TypedTuple<V>> scan(ScanOptions options) {
        return new MonitorCommand(MonitorMethod.create("scan"), getCategoryConfig()).execute(getKey(), () -> ops.scan(getKey(), options));
    }
}
