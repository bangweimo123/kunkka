package com.leshiguang.arch.kunkka.client;

import org.springframework.data.redis.core.*;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

/**
 * @Author bangwei.mo
 * @Date 2021-01-14 20:28
 * @Description
 */
public interface BaseKunkkaClient<V extends Serializable> {
    /**
     * 删除 key
     *
     * @param key
     * @return
     */
    Boolean delete(String key);

    /**
     * 是否存在key
     *
     * @param key
     * @return
     */
    Boolean hasKey(String key);

    Set<String> keys(String pattern);

    List<String> scan(String pattern, Long count);

    BoundListOperations<String, V> boundListOps(String finalKey);

    BoundGeoOperations<String, V> boundGeoOps(String finalKey);

    <HK, HV> BoundHashOperations<String, HK, HV> boundHashOps(String finalKey);

    BoundSetOperations<String, V> boundSetOps(String finalKey);

    BoundBitMapOperations<String> boundBitMapOps(String finalKey);
//
//    <HK, HV> BoundStreamOperations<String, HK, HV> boundStreamOps(String finalKey);

    BoundValueOperations<String, V> boundValueOps(String finalKey);

    BoundZSetOperations<String, V> boundZSetOps(String finalKey);
}
