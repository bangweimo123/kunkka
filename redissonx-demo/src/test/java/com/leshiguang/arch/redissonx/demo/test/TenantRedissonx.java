package com.leshiguang.arch.redissonx.demo.test;

import com.alibaba.fastjson.JSON;
import com.leshiguang.arch.redissonx.client.TenantStoreKey;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.redisson.api.RBucket;
import org.redisson.api.RMap;

import java.util.HashSet;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-09-14 13:57
 * @Description
 */
@Slf4j
public class TenantRedissonx extends RedissonxBase {
    @Test
    public void bucketTest() {
        log.info("startTenantKey");
        TenantStoreKey tenantStoreKey = new TenantStoreKey("bangweimo", 2, 2);
        RBucket<Integer> bucket = redissonxClient.getBucket(tenantStoreKey);
        bucket.set(4324);
        System.out.println(bucket.get());
    }

    @Test
    public void mapTest() {
        TenantStoreKey tenantStoreKey = new TenantStoreKey("bangweimo", 2, 25);
        RMap<Integer, Integer> map = redissonxClient.getMap(tenantStoreKey);
        map.put(1, 2);
        map.put(2, 4);
        HashSet<Integer> keySets = new HashSet<>();
        keySets.add(1);
        keySets.add(2);
        System.out.println(JSON.toJSON(map.getAll(keySets)));

    }
}
