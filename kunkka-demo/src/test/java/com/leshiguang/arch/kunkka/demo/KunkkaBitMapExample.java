package com.leshiguang.arch.kunkka.demo;

import com.alibaba.fastjson.JSON;
import com.leshiguang.arch.kunkka.client.KunkkaClient;
import com.leshiguang.arch.kunkka.client.StoreKey;
import org.junit.Test;
import org.springframework.data.redis.core.BoundBitMapOperations;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author bangwei.mo
 * @Date 2021-02-03 17:53
 * @Description
 */
public class KunkkaBitMapExample extends BaseTest {
    @Resource
    private KunkkaClient chaosKunkkaClient;


    @Test
    public void testSet() {
        StoreKey storeKey = new StoreKey("testHotKey", "32");
        BoundBitMapOperations<StoreKey> ops = chaosKunkkaClient.boundBitMapOps(storeKey);
        for (int i = 0; i < 100; i++) {
            ops.setBit(i, i % 2 == 0);
        }
    }

    @Test
    public void testGet() {
        StoreKey storeKey = new StoreKey("testHotKey", "32");
        BoundBitMapOperations<StoreKey> ops = chaosKunkkaClient.boundBitMapOps(storeKey);
        for (int i = 0; i < 100; i++) {
            System.out.println(ops.getBit(Long.valueOf(i)));
        }
    }

    @Test
    public void testSetBits() {
        StoreKey storeKey = new StoreKey("testHotKey", "32");
        BoundBitMapOperations<StoreKey> ops = chaosKunkkaClient.boundBitMapOps(storeKey);
        Map<Long, Boolean> bitsMap = new HashMap<>();
        for (int i = 0; i < 100; i++) {
            bitsMap.put(Long.valueOf(i), i % 2 == 0);
        }
        Map<Long, Boolean> result = ops.setBits(bitsMap);
        System.out.println(JSON.toJSON(result));
    }

    @Test
    public void testGetBits() {
        StoreKey storeKey = new StoreKey("testHotKey", "32");
        BoundBitMapOperations<StoreKey> ops = chaosKunkkaClient.boundBitMapOps(storeKey);
        List<Long> offsets = new ArrayList();
        for (int i = 0; i < 100; i++) {
            offsets.add(Long.valueOf(i));
        }
        Map<Long, Boolean> bitMaps = ops.getBits(offsets);

        System.out.println(JSON.toJSON(bitMaps));
    }

    @Test
    public void testBitCount() {
        StoreKey storeKey = new StoreKey("testHotKey", "32");
        BoundBitMapOperations<StoreKey> ops = chaosKunkkaClient.boundBitMapOps(storeKey);
        Long count = ops.bitCount();
        System.out.println(count);

    }
}
