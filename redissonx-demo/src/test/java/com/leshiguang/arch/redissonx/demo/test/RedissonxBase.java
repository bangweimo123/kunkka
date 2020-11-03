package com.leshiguang.arch.redissonx.demo.test;

import com.leshiguang.arch.redissonx.client.StoreKey;
import com.leshiguang.arch.redissonx.demo.base.BaseTest;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.redisson.RedissonxClient;
import org.redisson.api.RBucket;

import javax.annotation.Resource;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-09-14 13:37
 * @Description
 */
@Slf4j
public class RedissonxBase extends BaseTest {
    private static ExecutorService es = Executors.newCachedThreadPool();
    @Resource
    protected RedissonxClient redissonxClient;

    @Test
    public void simpleKey() {
        StoreKey storeKey = new StoreKey("bangweimo", 2);
        RBucket<String> bucket = redissonxClient.getBucket(storeKey);
        long a = bucket.remainTimeToLive();
        System.out.println(a);
    }


    public void testSet() {
        for (int i = 1; i < 10; i++) {
            for (int j = 1; j < 10; j++) {
                StoreKey storeKey = new StoreKey("testredissonx4", i, j);
                RBucket<String> bucket = redissonxClient.getBucket(storeKey);
                bucket.set("jiahuan.wu:[i]:" + i + ",[j]:" + j);
                System.out.println(bucket.get());
            }
        }
    }

    public static void testHotKey(RedissonxClient redissonxClient) {
        for (int i = 1; i < 3; i++) {
            for (int j = 1; j < 4; j++) {

                es.submit(new RedissonxBase.MThread(redissonxClient, i, j));
            }
        }
        while (!es.isShutdown() && !es.isTerminated()) {
            try {
                Thread.sleep(500l);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static class MThread extends Thread {
        private int i;

        private int j;

        private RedissonxClient redissonxClient;

        public MThread(RedissonxClient redissonxClient, int i, int j) {
            this.redissonxClient = redissonxClient;
            this.i = i;
            this.j = j;
        }

        @Override
        public void run() {
            StoreKey storeKey = new StoreKey("testredissonx4", i, j);
            RBucket<String> bucket = redissonxClient.getBucket(storeKey);
            for (int i = 0; i < 10; i++) {
                System.out.println(bucket.get());
            }
        }
    }
}