package com.leshiguang.arch.kunkka.client;

import org.springframework.data.redis.core.*;

import java.io.Serializable;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-12 11:29
 * @Description
 */
public interface KunkkaClient<K extends StoreKey, V extends Serializable> extends BaseKunkkaClient<V> {
    BoundListOperations<K, V> boundListOps(K key);

    BoundGeoOperations<K, V> boundGeoOps(K key);

    <HK, HV> BoundHashOperations<K, HK, HV> boundHashOps(K key);

    BoundSetOperations<K, V> boundSetOps(K key);
//
//    <HK, HV> BoundStreamOperations<K, HK, HV> boundStreamOps(K key);

    BoundValueOperations<K, V> boundValueOps(K key);

    BoundZSetOperations<K, V> boundZSetOps(K key);
}
