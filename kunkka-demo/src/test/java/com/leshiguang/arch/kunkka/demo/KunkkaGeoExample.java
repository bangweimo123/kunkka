package com.leshiguang.arch.kunkka.demo;

import com.leshiguang.arch.kunkka.client.KunkkaClient;
import com.leshiguang.arch.kunkka.client.StoreKey;
import org.junit.Test;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.BoundGeoOperations;

import javax.annotation.Resource;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-27 14:12
 * @Description
 */
public class KunkkaGeoExample extends BaseTest {
    @Resource
    private KunkkaClient chaosKunkkaClient;

    @Test
    public void testAdd() {
        StoreKey storeKey = new StoreKey("testZSet", "43");
        BoundGeoOperations<StoreKey, String> ops = chaosKunkkaClient.boundGeoOps(storeKey);
        RedisGeoCommands.GeoLocation aLocation = new RedisGeoCommands.GeoLocation("a", new Point(32.32, 23.23));
        ops.add(aLocation);
        RedisGeoCommands.GeoLocation bLocation = new RedisGeoCommands.GeoLocation("b", new Point(32.33, 23.23));
        ops.add(bLocation);
    }

    @Test
    public void testDistance() {
        StoreKey storeKey = new StoreKey("testZSet", "43");
        BoundGeoOperations<StoreKey, String> ops = chaosKunkkaClient.boundGeoOps(storeKey);
        Distance distance = ops.distance("a", "b");
        //输出 1022.0166 METERS
        System.out.println(distance);
    }
}
