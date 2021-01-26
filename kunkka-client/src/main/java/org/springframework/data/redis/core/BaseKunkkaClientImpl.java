package org.springframework.data.redis.core;

import com.leshiguang.arch.kunkka.client.BaseKunkkaClient;
import com.leshiguang.arch.kunkka.client.config.CategoryConfigManager;
import com.leshiguang.arch.kunkka.client.config.ZkCategoryConfigManager;
import com.leshiguang.arch.kunkka.client.config.cluster.ClusterConfigManager;
import com.leshiguang.arch.kunkka.client.config.cluster.ConnectFactoryBuilder;
import com.leshiguang.arch.kunkka.client.config.cluster.ZkClusterConfigManager;
import com.leshiguang.arch.kunkka.client.configure.zookeeper.ConfigureClientFactory;
import com.leshiguang.arch.kunkka.client.configure.zookeeper.DefaultConfigureClientFactory;
import com.leshiguang.arch.kunkka.client.lifecycle.Lifecycle;
import com.leshiguang.arch.kunkka.common.entity.cluster.ClusterBO;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-14 20:31
 * @Description
 */
public class BaseKunkkaClientImpl<V extends Serializable> implements BaseKunkkaClient<V>, Lifecycle {
    protected RedisTemplate<String, V> stringRedisTemplate;

    protected CategoryConfigManager categoryConfigManager;

    protected ClusterConfigManager clusterConfigManager;

    protected RedisConnectionFactory connectionFactory;

    protected ConfigureClientFactory configureClientFactory;

    protected String clusterName;

    protected String region;

    public BaseKunkkaClientImpl(String clusterName, String region) {
        this.clusterName = clusterName;
        this.region = region;
    }

    @Override
    public Boolean delete(String key) {
        return stringRedisTemplate.delete(key);
    }

    @Override
    public List<String> scan(String pattern, Long count) {
        ScanOptions scanOptions = ScanOptions.scanOptions().count(count).match(pattern).build();
        Cursor<byte[]> rawKeys = stringRedisTemplate.execute(connection -> connection.scan(scanOptions), true);
        List<String> keys = new ArrayList<>();
        while (rawKeys.hasNext()) {
            byte[] rawKey = rawKeys.next();
            keys.add((String) stringRedisTemplate.getKeySerializer().deserialize(rawKey));
        }
        return keys;
    }

    @Override
    public BoundListOperations<String, V> boundListOps(String finalKey) {
        return new DefaultBoundListOperations<>(finalKey, stringRedisTemplate);
    }

    @Override
    public BoundGeoOperations<String, V> boundGeoOps(String finalKey) {
        return new DefaultBoundGeoOperations<>(finalKey, stringRedisTemplate);
    }

    @Override
    public <HK, HV> BoundHashOperations<String, HK, HV> boundHashOps(String finalKey) {
        return new DefaultBoundHashOperations<>(finalKey, stringRedisTemplate);
    }

    @Override
    public BoundSetOperations<String, V> boundSetOps(String finalKey) {
        return new DefaultBoundSetOperations<>(finalKey, stringRedisTemplate);
    }

//    @Override
//    public <HK, HV> BoundStreamOperations<String, HK, HV> boundStreamOps(String finalKey) {
//        return new DefaultBoundStreamOperations<>(finalKey, stringRedisTemplate);
//    }

    @Override
    public BoundValueOperations<String, V> boundValueOps(String finalKey) {
        return new DefaultBoundValueOperations<>(finalKey, stringRedisTemplate);
    }

    @Override
    public BoundZSetOperations<String, V> boundZSetOps(String finalKey) {
        return new DefaultBoundZSetOperations<>(finalKey, stringRedisTemplate);
    }

    public void setStringRedisTemplate(RedisTemplate<String, V> stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    public void setConfigureClientFactory(ConfigureClientFactory configureClientFactory) {
        this.configureClientFactory = configureClientFactory;
    }


    @Override
    public void start() {
        if (null == configureClientFactory) {
            configureClientFactory = new DefaultConfigureClientFactory();
            configureClientFactory.start();
        }
        if (null == clusterConfigManager) {
            clusterConfigManager = new ZkClusterConfigManager();
        }
        ClusterBO clusterBO = clusterConfigManager.getClusterConfig(clusterName, region, configureClientFactory);
        connectionFactory = ConnectFactoryBuilder.build(clusterBO);
        categoryConfigManager = new ZkCategoryConfigManager(clusterName, region, configureClientFactory);
        stringRedisTemplate = new RedisTemplate<>();
        stringRedisTemplate.setKeySerializer(new StringRedisSerializer());
        stringRedisTemplate.setConnectionFactory(connectionFactory);
        stringRedisTemplate.afterPropertiesSet();
    }

    @Override
    public void stop() {
        configureClientFactory.stop();
    }
}