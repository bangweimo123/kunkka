package com.leshiguang.arch.kunkka.demo;

import com.leshiguang.arch.kunkka.client.KunkkaClient;
import com.leshiguang.arch.kunkka.client.StoreKey;
import org.junit.Test;
import org.springframework.data.redis.core.BoundValueOperations;

import javax.annotation.Resource;

/**
 * @Author bangwei.mo
 * @Date 2021-01-26 17:36
 * @Description
 */
public class KunkkaStringExample extends BaseTest {
    @Resource
    private KunkkaClient chaosKunkkaClient;

    @Test
    public void testSet() {
        StoreKey storeKey = new StoreKey("testHotKey", "32");
        BoundValueOperations<StoreKey, String> ops = chaosKunkkaClient.boundValueOps(storeKey);
        ops.set("2323");
    }

    @Test
    public void testGet() {
        StoreKey storeKey = new StoreKey("testHotKey", "32");
        BoundValueOperations<StoreKey, String> ops = chaosKunkkaClient.boundValueOps(storeKey);
        ops.get();
    }

    @Test
    public void testSetIfAbsent() {
        StoreKey storeKey = new StoreKey("testHotKey", "32");
        BoundValueOperations<StoreKey, String> ops = chaosKunkkaClient.boundValueOps(storeKey);
        ops.setIfAbsent("2323");
    }

    @Test
    public void testSetIfPresent() {
        StoreKey storeKey = new StoreKey("testHotKey", "32");
        BoundValueOperations<StoreKey, String> ops = chaosKunkkaClient.boundValueOps(storeKey);
        ops.setIfPresent("32");
    }

    @Test
    public void testGetAndSet() {
        StoreKey storeKey = new StoreKey("testHotKey", "32");
        BoundValueOperations<StoreKey, String> ops = chaosKunkkaClient.boundValueOps(storeKey);
        String old = ops.getAndSet("34");
    }
}
