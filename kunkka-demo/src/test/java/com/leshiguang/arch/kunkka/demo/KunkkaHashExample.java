package com.leshiguang.arch.kunkka.demo;

import com.leshiguang.arch.kunkka.client.KunkkaClient;
import com.leshiguang.arch.kunkka.client.StoreKey;
import org.junit.Test;
import org.springframework.data.redis.core.BoundHashOperations;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-27 12:08
 * @Description
 */
public class KunkkaHashExample extends BaseTest {
    @Resource
    private KunkkaClient chaosKunkkaClient;

    @Test
    public void testPut() {
        StoreKey storeKey = new StoreKey("testHash", "32", 44);
        BoundHashOperations<StoreKey, String, Long> ops = chaosKunkkaClient.boundHashOps(storeKey);
        ops.put("a", 432l);
        ops.put("b", 4324l);
    }

    @Test
    public void testMultiGet() {
        StoreKey storeKey = new StoreKey("testHash", "32", 44);
        BoundHashOperations<StoreKey, String, Long> ops = chaosKunkkaClient.boundHashOps(storeKey);
        List<Long> result = ops.multiGet(Arrays.asList("a", "b"));
        System.out.println(result);
    }
}
