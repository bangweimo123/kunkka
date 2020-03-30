package com.leshiguang.arch.redissonx.demo;

import com.leshiguang.arch.redissonx.client.StoreKey;
import com.leshiguang.arch.redissonx.client.TenantStoreKey;
import com.leshiguang.arch.redissonx.config.store.StoreCategoryConfig;
import com.leshiguang.arch.redissonx.config.zookeeper.StoreConfigClient;
import com.leshiguang.arch.redissonx.config.zookeeper.ZkStoreConfigClient;
import org.redisson.RedissonxClient;
import org.redisson.api.RBucket;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.Resource;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-17 19:55
 * @Modify
 */
public class RedissonxSpringHandler {
    private static ExecutorService es = Executors.newCachedThreadPool();

    @Resource
    private RedissonxClient redissonxClient;

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:spring/appcontext-core.xml");
        RedissonxClient redissonxClient = applicationContext.getBean("redissonxClient", RedissonxClient.class);
        testTenantKey(redissonxClient);

    }

    public static void testSimpleKey(RedissonxClient redissonxClient) {
        StoreKey storeKey = new StoreKey("testredissonx4", 2, 3);
        RBucket<String> bucket = redissonxClient.getBucket(storeKey);
        long a = bucket.remainTimeToLive();
        System.out.println(a);
    }

    public static void testTenantKey(RedissonxClient redissonxClient) {
        StoreKey storeKey = new TenantStoreKey("testredissonx4", 2, 2, 3);
        RBucket<String> bucket = redissonxClient.getBucket(storeKey);
        bucket.set("bangwei.mo");
        System.out.println(bucket.get());

    }

    public static void testSet(RedissonxClient redissonxClient) {
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

                es.submit(new MThread(redissonxClient, i, j));
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
