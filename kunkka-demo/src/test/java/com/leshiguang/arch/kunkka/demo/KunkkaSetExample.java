package com.leshiguang.arch.kunkka.demo;

import com.alibaba.fastjson.JSON;
import com.leshiguang.arch.kunkka.client.KunkkaClient;
import com.leshiguang.arch.kunkka.client.StoreKey;
import org.junit.Test;
import org.springframework.data.redis.core.BoundSetOperations;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author bangwei.mo
 * @Date 2021-01-27 14:12
 * @Description
 */
public class KunkkaSetExample extends BaseTest {
    @Resource
    private KunkkaClient chaosKunkkaClient;

    @Test
    public void testAdd() {
        StoreKey storeKey = new StoreKey("testSet", "32");
        BoundSetOperations<StoreKey, String> ops = chaosKunkkaClient.boundSetOps(storeKey);
        ops.add("a");
        ops.add("b");
    }

    @Test
    public void testRandomMembers() {
        StoreKey storeKey = new StoreKey("testSet", "32");
        BoundSetOperations<StoreKey, String> ops = chaosKunkkaClient.boundSetOps(storeKey);
        List<String> value = ops.randomMembers(100l);
        System.out.println(JSON.toJSON(value));
    }

    @Test
    public void testSize() {
        StoreKey storeKey = new StoreKey("testSet", "32");
        BoundSetOperations<StoreKey, String> ops = chaosKunkkaClient.boundSetOps(storeKey);
        Long size = ops.size();
        System.out.println(size);
    }
}
