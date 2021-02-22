package com.leshiguang.arch.kunkka.client;

import org.springframework.data.redis.core.*;

import java.io.Serializable;

/**
 * @Author bangwei.mo
 * @Date 2021-01-12 11:29
 * @Description
 */
public interface KunkkaClient<K extends StoreKey, V extends Serializable> extends BaseKunkkaClient<V> {
    /**
     * 删除 key
     *
     * @param key
     * @return
     */
    Boolean delete(K key);

    /**
     * 是否存在key
     *
     * @param key
     * @return
     */
    Boolean hasKey(K key);
    /**
     * list操作
     * @param key
     * @return
     */
    BoundListOperations<K, V> boundListOps(K key);

    /**
     * geo 地理位置
     * @param key
     * @return
     */
    BoundGeoOperations<K, V> boundGeoOps(K key);

    /**
     * hash
     * @param key
     * @param <HK>
     * @param <HV>
     * @return
     */
    <HK, HV> BoundHashOperations<K, HK, HV> boundHashOps(K key);

    /**
     * set
     *
     * @param key
     * @return
     */
    BoundSetOperations<K, V> boundSetOps(K key);
//
//    <HK, HV> BoundStreamOperations<K, HK, HV> boundStreamOps(K key);

    /**
     * 默认value
     * @param key
     * @return
     */
    BoundValueOperations<K, V> boundValueOps(K key);

    /**
     * zset
     * @param key
     * @return
     */
    BoundZSetOperations<K, V> boundZSetOps(K key);

    /**
     * bitmap
     * @param key
     * @return
     */
    BoundBitMapOperations<K> boundBitMapOps(K key);
}
