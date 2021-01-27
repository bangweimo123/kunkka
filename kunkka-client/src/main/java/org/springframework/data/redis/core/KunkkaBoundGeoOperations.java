package org.springframework.data.redis.core;

import com.leshiguang.arch.kunkka.client.StoreKey;
import com.leshiguang.arch.kunkka.client.config.CategoryConfig;
import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.connection.RedisGeoCommands;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-26 16:53
 * @Description
 */
public class KunkkaBoundGeoOperations<K extends StoreKey, M extends Serializable> extends KunkkaBoundKeyOperations<K> implements BoundGeoOperations<K, M> {

    private final GeoOperations<K, M> ops;

    /**
     * Constructs a new {@code DefaultBoundGeoOperations}.
     *
     * @param key        must not be {@literal null}.
     * @param operations must not be {@literal null}.
     */
    KunkkaBoundGeoOperations(CategoryConfig categoryConfig, K key, RedisOperations<K, M> operations) {
        super(categoryConfig, key, operations);
        this.ops = operations.opsForGeo();
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundGeoOperations#add(org.springframework.data.geo.Point, java.lang.Object)
     */
    @Override
    public Long add(Point point, M member) {
        return new MonitorCommand(MonitorMethod.create("add").setExpireable(), getCategoryConfig()).execute(getKey(), () -> ops.add(getKey(), point, member));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundGeoOperations#add(org.springframework.data.redis.connection.RedisGeoCommands.GeoLocation)
     */
    @Override
    public Long add(RedisGeoCommands.GeoLocation<M> location) {
        return new MonitorCommand(MonitorMethod.create("add").setExpireable(), getCategoryConfig()).execute(getKey(), () -> ops.add(getKey(), location));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundGeoOperations#add(java.util.Map)
     */
    @Override
    public Long add(Map<M, Point> memberCoordinateMap) {
        return new MonitorCommand(MonitorMethod.create("add").setExpireable(), getCategoryConfig()).execute(getKey(), () -> ops.add(getKey(), memberCoordinateMap));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundGeoOperations#add(java.lang.Iterable)
     */
    @Override
    public Long add(Iterable<RedisGeoCommands.GeoLocation<M>> locations) {
        return new MonitorCommand(MonitorMethod.create("add").setExpireable(), getCategoryConfig()).execute(getKey(), () -> ops.add(getKey(), locations));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundGeoOperations#distance(java.lang.Object, java.lang.Object)
     */
    @Override
    public Distance distance(M member1, M member2) {
        return new MonitorCommand(MonitorMethod.create("distance"), getCategoryConfig()).execute(getKey(), () -> ops.distance(getKey(), member1, member2));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundGeoOperations#distance(java.lang.Object, java.lang.Object, org.springframework.data.geo.Metric)
     */
    @Override
    public Distance distance(M member1, M member2, Metric unit) {
        return new MonitorCommand(MonitorMethod.create("delete"), getCategoryConfig()).execute(getKey(), () -> ops.distance(getKey(), member1, member2, unit));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundGeoOperations#hash(java.lang.Object[])
     */
    @Override
    public List<String> hash(M... members) {
        return new MonitorCommand(MonitorMethod.create("hash"), getCategoryConfig()).execute(getKey(), () -> ops.hash(getKey(), members));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundGeoOperations#position(java.lang.Object[])
     */
    @Override
    public List<Point> position(M... members) {
        return new MonitorCommand(MonitorMethod.create("position"), getCategoryConfig()).execute(getKey(), () -> ops.position(getKey(), members));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundGeoOperations#radius(org.springframework.data.geo.Circle)
     */
    @Override
    public GeoResults<RedisGeoCommands.GeoLocation<M>> radius(Circle within) {
        return new MonitorCommand(MonitorMethod.create("radius"), getCategoryConfig()).execute(getKey(), () -> ops.radius(getKey(), within));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundGeoOperations#radius(org.springframework.data.geo.Circle, org.springframework.data.redis.core.GeoRadiusCommandArgs)
     */
    @Override
    public GeoResults<RedisGeoCommands.GeoLocation<M>> radius(Circle within, RedisGeoCommands.GeoRadiusCommandArgs param) {
        return new MonitorCommand(MonitorMethod.create("radius"), getCategoryConfig()).execute(getKey(), () -> ops.radius(getKey(), within, param));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundGeoOperations#radius(java.lang.Object, java.lang.Object, double)
     */
    @Override
    public GeoResults<RedisGeoCommands.GeoLocation<M>> radius(K key, M member, double radius) {
        return new MonitorCommand(MonitorMethod.create("radius"), getCategoryConfig()).execute(getKey(), () -> ops.radius(getKey(), member, radius));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundGeoOperations#radius(java.lang.Object, org.springframework.data.geo.Distance)
     */
    @Override
    public GeoResults<RedisGeoCommands.GeoLocation<M>> radius(M member, Distance distance) {
        return new MonitorCommand(MonitorMethod.create("radius"), getCategoryConfig()).execute(getKey(), () -> ops.radius(getKey(), member, distance));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundGeoOperations#radius(java.lang.Object, org.springframework.data.geo.Distance, org.springframework.data.redis.core.radiusCommandArgs)
     */
    @Override
    public GeoResults<RedisGeoCommands.GeoLocation<M>> radius(M member, Distance distance, RedisGeoCommands.GeoRadiusCommandArgs param) {
        return new MonitorCommand(MonitorMethod.create("delete"), getCategoryConfig()).execute(getKey(), () -> ops.radius(getKey(), member, distance, param));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundGeoOperations#remove(java.lang.Object[])
     */
    @Override
    public Long remove(M... members) {
        return new MonitorCommand(MonitorMethod.create("remove"), getCategoryConfig()).execute(getKey(), () -> ops.remove(getKey(), members));
    }

    /*
     * (non-Javadoc)
     * @see org.springframework.data.redis.core.BoundKeyOperations#getType()
     */
    @Override
    public DataType getType() {
        return DataType.ZSET;
    }
}
