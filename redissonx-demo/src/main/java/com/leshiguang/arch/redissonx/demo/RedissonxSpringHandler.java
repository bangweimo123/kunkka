package com.leshiguang.arch.redissonx.demo;

import com.leshiguang.arch.redissonx.client.StoreKey;
import com.leshiguang.arch.redissonx.config.store.StoreCategoryConfig;
import com.leshiguang.arch.redissonx.config.zookeeper.StoreConfigClient;
import com.leshiguang.arch.redissonx.config.zookeeper.ZkStoreConfigClient;
import org.redisson.RedissonxClient;
import org.redisson.api.RBucket;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-17 19:55
 * @Modify
 */
public class RedissonxSpringHandler {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath*:spring/appcontext-core.xml");
        RedissonxClient redissonxClient = applicationContext.getBean("redissonxClient", RedissonxClient.class);
        initZkData();
        StoreKey storeKey = new StoreKey("redissonx-test2", 1, 2);
        RBucket<String> bucket = redissonxClient.getBucket(storeKey);
        bucket.set("bangwei.mo");
        System.out.println(bucket.get());
        RBucket<String> bucket2 = redissonxClient.getBucket(storeKey);
        bucket.set("jiahuan.wu");
        System.out.println(bucket2.get());
    }


    public static void initZkData() {
        StoreConfigClient storeConfigClient = new ZkStoreConfigClient();
        StoreCategoryConfig storeCategoryConfig = new StoreCategoryConfig();
        storeCategoryConfig.setCategory("redissonx-test2");
        storeCategoryConfig.setDuration("3h");
        storeCategoryConfig.setHot(false);
        storeCategoryConfig.setIndexTemplate("a{0}d{1}");
        storeConfigClient.setStoreCategoryConfig("test_01", storeCategoryConfig);
    }

}
