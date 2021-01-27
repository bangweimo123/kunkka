package com.leshiguang.arch.kunkka.demo;

import com.leshiguang.arch.kunkka.client.KunkkaClient;
import com.leshiguang.arch.kunkka.client.StoreKey;
import org.junit.Test;
import org.springframework.data.redis.core.BoundZSetOperations;

import javax.annotation.Resource;
import java.util.Set;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-27 13:35
 * @Description
 */
public class KunkkaZSetExample extends BaseTest {
    @Resource
    private KunkkaClient chaosKunkkaClient;

    @Test
    public void testAdd() {
        StoreKey storeKey = new StoreKey("testZSet", "32");
        BoundZSetOperations<StoreKey, String> ops = chaosKunkkaClient.boundZSetOps(storeKey);
        ops.add("a", 0.1);
        ops.add("b", 0.2);
    }

    @Test
    public void testRangeByScore() {
        StoreKey storeKey = new StoreKey("testZSet", "32");
        BoundZSetOperations<StoreKey, String> ops = chaosKunkkaClient.boundZSetOps(storeKey);
        Set<String> values = ops.rangeByScore(0, 1);
        System.out.println(values);
    }
}
