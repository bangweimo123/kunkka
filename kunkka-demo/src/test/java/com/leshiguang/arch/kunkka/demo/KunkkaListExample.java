package com.leshiguang.arch.kunkka.demo;

import com.leshiguang.arch.kunkka.client.KunkkaClient;
import com.leshiguang.arch.kunkka.client.StoreKey;
import org.junit.Test;
import org.springframework.data.redis.core.BoundListOperations;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-27 14:12
 * @Description
 */
public class KunkkaListExample extends BaseTest {
    @Resource
    private KunkkaClient chaosKunkkaClient;

    @Test
    public void testPush() {
        StoreKey storeKey = new StoreKey("testList", "32");
        BoundListOperations<StoreKey, String> ops = chaosKunkkaClient.boundListOps(storeKey);
        ops.leftPush("a");
        ops.leftPush("b");
        ops.rightPush("c");
        ops.rightPush("d");
    }

    @Test
    public void testRange() {
        StoreKey storeKey = new StoreKey("testList", "32");
        BoundListOperations<StoreKey, String> ops = chaosKunkkaClient.boundListOps(storeKey);
        List<String> result = ops.range(0, 100);
        System.out.println(result);
    }
}
