package com.leshiguang.arch.kunkka.demo;

import com.leshiguang.arch.kunkka.client.KunkkaClient;
import com.leshiguang.arch.kunkka.client.StoreKey;
import org.junit.Test;
import org.springframework.data.redis.core.BoundListOperations;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author bangwei.mo
 * @Date 2021-01-27 14:12
 * @Description
 */
public class KunkkaListExample extends BaseTest {
    @Resource
    private KunkkaClient chaosKunkkaClient;

    @Test
    public void testPush() {
        StoreKey storeKey = new StoreKey("testList", "33");
        BoundListOperations<StoreKey, String> ops = chaosKunkkaClient.boundListOps(storeKey);
        //左侧添加一个 "a" 输出["a"]
        ops.leftPush("a");
        //左侧添加一个 "b" 输出["b","a"]
        ops.leftPush("b");
        //右侧添加一个"c" 输出 ["b","a","c"]
        ops.rightPush("c");
        //右侧插入一个"d" 输出["b","a","c","d"]
        ops.rightPush("d");
        //以c为中间点,在C的右边插入记录s 输出 ["b","a","c","s","d"]
        ops.rightPush("c", "s");
        //以c为中间点,在C的左边插入记录g 输出 ["b","a","g","c","s","d"]
        ops.leftPush("c", "g");
    }

    @Test
    public void testPop() {
        StoreKey storeKey = new StoreKey("testList", "33");
        BoundListOperations<StoreKey, String> ops = chaosKunkkaClient.boundListOps(storeKey);
        for (int i = 0; i < ops.size() - 1; i++) {
            String result = ops.leftPop();
            System.out.println(result);
        }
        for (int i = 0; i < ops.size() - 1; i++) {
            String result = ops.rightPop();
            System.out.println(result);
        }
    }

    @Test
    public void testRange() {
        StoreKey storeKey = new StoreKey("testList", "33");
        BoundListOperations<StoreKey, String> ops = chaosKunkkaClient.boundListOps(storeKey);
        List<String> result = ops.range(0, 100);
        System.out.println(result);
    }
}
