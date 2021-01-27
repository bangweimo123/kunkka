package org.springframework.data.redis.core;

import com.leshiguang.arch.kunkka.client.KunkkaClient;
import com.leshiguang.arch.kunkka.client.StoreKey;
import com.leshiguang.arch.kunkka.client.config.CategoryConfig;
import com.leshiguang.arch.kunkka.client.ehcache.EhcacheValueOperations;
import com.leshiguang.arch.kunkka.client.serialize.StoreKeyEhcacheSerializer;
import com.leshiguang.arch.kunkka.client.serialize.StoreKeyRedisSerializer;
import com.leshiguang.arch.kunkka.common.exception.KunkkaException;
import com.leshiguang.scaffold.common.utils.RegionUtil;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;

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


    protected interface IMC<T extends KunkkaBoundKeyOperations> {
        T execute(CategoryConfig categoryConfig);
    }

    protected class BoundOperationsCommand<T extends KunkkaBoundKeyOperations> {

        public T execute(K key, IMC<T> imc) throws KunkkaException {
            CategoryConfig categoryConfig = processCategoryConfig(key);
            T opt = imc.execute(categoryConfig);
            if (opt.getExpire() <= 0 && categoryConfig.getDurationSeconds() > 0) {
                opt.expireInner(categoryConfig.getDurationSeconds(), TimeUnit.SECONDS);
            }
            return opt;
        }
    }

    protected CategoryConfig processCategoryConfig(K key) {
        CategoryConfig categoryConfig = categoryConfigManager.getConfig(key.getCategory());
        return categoryConfig;
    }

    @Override
    public BoundListOperations<K, V> boundListOps(K key) {
        return new BoundOperationsCommand<KunkkaBoundListOperations>().execute(key, (categoryConfig) -> new KunkkaBoundListOperations(categoryConfig, key, redisTemplate));
    }

    @Override
    public BoundGeoOperations<K, V> boundGeoOps(K key) {
        return new BoundOperationsCommand<KunkkaBoundGeoOperations>().execute(key, (categoryConfig) -> new KunkkaBoundGeoOperations(categoryConfig, key, redisTemplate));
    }

    @Override
    public <HK, HV> BoundHashOperations<K, HK, HV> boundHashOps(K key) {
        return new BoundOperationsCommand<KunkkaBoundHashOperations>().execute(key, (categoryConfig) -> new KunkkaBoundHashOperations(categoryConfig, key, redisTemplate));
    }

    @Override
    public BoundSetOperations<K, V> boundSetOps(K key) {
        return new BoundOperationsCommand<KunkkaBoundSetOperations>().execute(key, (categoryConfig) -> new KunkkaBoundSetOperations(categoryConfig, key, redisTemplate));
    }

//    @Override
//    public <HK, HV> BoundStreamOperations<K, HK, HV> boundStreamOps(K key) {
//        return new KunkkaBoundStreamOperations<>(processCategoryConfig(key), key, redisTemplate);
//    }

    @Override
    public BoundValueOperations<K, V> boundValueOps(K key) {
        return new BoundOperationsCommand<KunkkaBoundValueOperations>().execute(key, (categoryConfig) -> new KunkkaBoundValueOperations(categoryConfig, key, redisTemplate, ehcacheValueOperations));
    }

    @Override
    public BoundZSetOperations<K, V> boundZSetOps(K key) {
        return new BoundOperationsCommand<KunkkaBoundZSetOperations>().execute(key, (categoryConfig) -> new KunkkaBoundZSetOperations(categoryConfig, key, redisTemplate));
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
