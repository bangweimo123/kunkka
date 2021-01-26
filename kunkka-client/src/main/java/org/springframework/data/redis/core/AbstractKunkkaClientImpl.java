package org.springframework.data.redis.core;

import com.leshiguang.arch.kunkka.client.KunkkaClient;
import com.leshiguang.arch.kunkka.client.StoreKey;
import com.leshiguang.arch.kunkka.client.config.CategoryConfig;
import com.leshiguang.arch.kunkka.client.ehcache.EhcacheValueOperations;
import com.leshiguang.arch.kunkka.client.serialize.StoreKeyEhcacheSerializer;
import com.leshiguang.arch.kunkka.client.serialize.StoreKeyRedisSerializer;
import com.leshiguang.scaffold.common.utils.RegionUtil;

import java.io.Serializable;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-07 16:35
 * @Description
 */
public abstract class AbstractKunkkaClientImpl<K extends StoreKey, V extends Serializable> extends BaseKunkkaClientImpl<V> implements KunkkaClient<K, V> {
    private RedisTemplate<K, V> redisTemplate;

    private EhcacheValueOperations<K, V> ehcacheValueOperations;

    public AbstractKunkkaClientImpl(String clusterName) {
        super(clusterName, RegionUtil.getRegionKey());
    }

    public AbstractKunkkaClientImpl(String clusterName, String region) {
        super(clusterName, region);
    }


    protected CategoryConfig processCategoryConfig(K key) {
        CategoryConfig categoryConfig = categoryConfigManager.getConfig(key.getCategory());
        return categoryConfig;
    }

    @Override
    public BoundListOperations<K, V> boundListOps(K key) {
        return new KunkkaBoundListOperations(processCategoryConfig(key), key, redisTemplate);
    }

    @Override
    public BoundGeoOperations<K, V> boundGeoOps(K key) {
        return new KunkkaBoundGeoOperations<>(processCategoryConfig(key), key, redisTemplate);
    }

    @Override
    public <HK, HV> BoundHashOperations<K, HK, HV> boundHashOps(K key) {
        return new KunkkaBoundHashOperations<>(processCategoryConfig(key), key, redisTemplate);
    }

    @Override
    public BoundSetOperations<K, V> boundSetOps(K key) {
        return new KunkkaBoundSetOperations<>(processCategoryConfig(key), key, redisTemplate);
    }

//    @Override
//    public <HK, HV> BoundStreamOperations<K, HK, HV> boundStreamOps(K key) {
//        return new KunkkaBoundStreamOperations<>(processCategoryConfig(key), key, redisTemplate);
//    }

    @Override
    public BoundValueOperations<K, V> boundValueOps(K key) {
        return new KunkkaBoundValueOperations(processCategoryConfig(key), key, redisTemplate, ehcacheValueOperations);
    }

    @Override
    public BoundZSetOperations<K, V> boundZSetOps(K key) {
        return new KunkkaBoundZSetOperations<>(processCategoryConfig(key), key, redisTemplate);
    }

    @Override
    public void start() {
        super.start();
        this.redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setKeySerializer(new StoreKeyRedisSerializer(categoryConfigManager));
        redisTemplate.afterPropertiesSet();
        StoreKeyEhcacheSerializer storeKeyEhcacheSerializer = new StoreKeyEhcacheSerializer(categoryConfigManager);
        ehcacheValueOperations = new EhcacheValueOperations<>(storeKeyEhcacheSerializer);
        ehcacheValueOperations.start();
    }
}
