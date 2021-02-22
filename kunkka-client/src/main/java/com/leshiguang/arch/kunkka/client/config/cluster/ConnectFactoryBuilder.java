package com.leshiguang.arch.kunkka.client.config.cluster;

import com.leshiguang.arch.kunkka.client.config.cluster.builder.*;
import com.leshiguang.arch.kunkka.client.exception.KunkkaConfigException;
import com.leshiguang.arch.kunkka.common.entity.cluster.ClusterBO;
import com.leshiguang.arch.kunkka.common.entity.cluster.ClusterConnectParamsBO;
import com.leshiguang.arch.kunkka.common.enums.ClusterEngine;
import com.leshiguang.arch.kunkka.common.enums.ClusterMode;
import com.leshiguang.scaffold.common.utils.AppUtil;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.redisson.config.Config;
import org.redisson.spring.data.connection.RedissonConnectionFactory;
import org.springframework.data.redis.connection.*;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceClientConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;

import java.time.Duration;

/**
 * @Author bangwei.mo
 * @Date 2021-01-13 20:37
 * @Description
 */
public class ConnectFactoryBuilder {
    private ClusterBO cluster;

    private IRedisConfigurationBuilder configurationBuilder;

    private RedisConfiguration redisConfiguration;

    private Config redissonConfig;

    private RedisConnectionFactory redisConnectionFactory;

    public ConnectFactoryBuilder(ClusterBO cluster) {
        this.cluster = cluster;
    }

    public static RedisConnectionFactory build(ClusterBO clusterBO) {
        ConnectFactoryBuilder builder = new ConnectFactoryBuilder(clusterBO);
        return builder.build();
    }

    private void mode() {
        ClusterMode mode = ClusterMode.parse(cluster.getEngine());
        if (null == mode) {
            mode = ClusterMode.single;
        }
        switch (mode) {
            case single:
                configurationBuilder = new RedisStandaloneConfigurationBuilder();
                break;
            case cluster:
                configurationBuilder = new RedisClusterConfigurationBuilder();
                break;
            case sentinel:
                configurationBuilder = new RedisSentinelConfigurationBuilder();
                break;
            case replicate:
                configurationBuilder = new RedisStaticMasterReplicaConfigurationBuilder();
                break;
        }
    }

    private GenericObjectPoolConfig genericObjectPoolConfig() {
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        if (null != cluster.getConnectParams()) {
            poolConfig.setMaxIdle(cluster.getConnectParams().getMaxIdle());
            poolConfig.setMaxTotal(cluster.getConnectParams().getMaxTotal());
            poolConfig.setMinIdle(cluster.getConnectParams().getMinIdle());
        }
        return poolConfig;
    }

    private JedisClientConfiguration processJedisClientConfiguration() {
        JedisClientConfiguration.JedisClientConfigurationBuilder builder = JedisClientConfiguration.builder();
        if (null != cluster.getConnectParams()) {
            if (cluster.getConnectParams().getSupportClient()) {
                builder.clientName(AppUtil.appName());
            }
            builder.connectTimeout(Duration.ofMillis(cluster.getConnectParams().getConnectTimeout()));
            builder.readTimeout(Duration.ofMillis(cluster.getConnectParams().getReadTimeout()));
        }
        GenericObjectPoolConfig genericObjectPoolConfig = genericObjectPoolConfig();
        genericObjectPoolConfig.setTestWhileIdle(true);
        genericObjectPoolConfig.setMinEvictableIdleTimeMillis(60000L);
        genericObjectPoolConfig.setTimeBetweenEvictionRunsMillis(30000L);
        genericObjectPoolConfig.setNumTestsPerEvictionRun(-1);
        return builder.usePooling().poolConfig(genericObjectPoolConfig()).and().build();

    }

    private LettuceClientConfiguration processLettuceClientConfiguration() {
        LettuceClientConfiguration.LettuceClientConfigurationBuilder builder = LettuceClientConfiguration.builder();

        if (null != cluster.getConnectParams()) {
            if (cluster.getConnectParams().getSupportClient()) {
                builder.clientName(AppUtil.appName());
            }
            builder.commandTimeout(Duration.ofMillis(cluster.getConnectParams().getConnectTimeout()));
        }
        GenericObjectPoolConfig genericObjectPoolConfig = genericObjectPoolConfig();
        return LettucePoolingClientConfiguration.builder().poolConfig(genericObjectPoolConfig).build();
    }

    private void engine() {
        if (null == configurationBuilder) {
            this.mode();
        }
        ClusterEngine engine = ClusterEngine.parse(cluster.getEngine());
        if (null == engine) {
            engine = ClusterEngine.jedis;
        }
        switch (engine) {
            case jedis:
                this.redisConfiguration = configurationBuilder.build(cluster);
                JedisClientConfiguration jedisClientConfiguration = processJedisClientConfiguration();
                if (RedisConfiguration.isClusterConfiguration(redisConfiguration)) {
                    redisConnectionFactory = new JedisConnectionFactory(
                            (RedisClusterConfiguration) redisConfiguration, jedisClientConfiguration);

                } else {
                    if (RedisConfiguration.isSentinelConfiguration(redisConfiguration)) {
                        redisConnectionFactory = new JedisConnectionFactory(
                                (RedisSentinelConfiguration) redisConfiguration, jedisClientConfiguration);
                    } else {
                        redisConnectionFactory = new JedisConnectionFactory(
                                (RedisStandaloneConfiguration) redisConfiguration, jedisClientConfiguration);
                    }
                }
                ((JedisConnectionFactory) redisConnectionFactory).afterPropertiesSet();
                break;
            case letture:
                LettuceClientConfiguration lettuceClientConfiguration = processLettuceClientConfiguration();
                this.redisConfiguration = configurationBuilder.build(cluster);
                redisConnectionFactory = new LettuceConnectionFactory(redisConfiguration, lettuceClientConfiguration);
                ((LettuceConnectionFactory) redisConnectionFactory).afterPropertiesSet();
                break;
            case redisson:
                redissonConfig = configurationBuilder.redissonBuild(cluster);
                redisConnectionFactory = new RedissonConnectionFactory(redissonConfig);
                try {
                    ((RedissonConnectionFactory) redisConnectionFactory).afterPropertiesSet();
                } catch (Exception e) {
                    throw new KunkkaConfigException("redissonConnectFactory init error", e);
                }
                break;
        }
    }

    public RedisConnectionFactory build() {
        if (null == redisConnectionFactory) {
            this.engine();
        }
        return redisConnectionFactory;
    }
}
