package com.leshiguang.arch.kunkka.demo;

import com.leshiguang.arch.kunkka.client.KunkkaClient;
import com.leshiguang.arch.kunkka.client.StoreKey;
import org.junit.Before;
import org.junit.Test;
import org.springframework.data.redis.core.BoundValueOperations;

import javax.annotation.Resource;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-26 17:36
 * @Description
 */
public class KunkkaClientExample extends BaseTest {
    @Resource
    private KunkkaClient chaosKunkkaClient;

    private BoundValueOperations<StoreKey, String> ops;

    @Before
    public void initOperations() {
        StoreKey storeKey = new StoreKey("testHotKey", "32");
        ops = chaosKunkkaClient.boundValueOps(storeKey);
    }

    @Test
    public void testSet() {
        ops.set("2323");
    }

    @Test
    public void testGet() {
        ops.get();
    }

    @Test
    public void testBatchGet() {
        for (int i = 0; i < 20; i++) {
            ops.set(String.valueOf(i));
            System.out.println(ops.get());
        }
    }
}
