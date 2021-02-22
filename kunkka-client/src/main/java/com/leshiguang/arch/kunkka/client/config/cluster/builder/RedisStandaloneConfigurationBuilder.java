package com.leshiguang.arch.kunkka.client.config.cluster.builder;

import com.leshiguang.arch.kunkka.common.entity.cluster.ClusterBO;
import com.leshiguang.arch.kunkka.common.entity.connect.ConnectBO;
import com.leshiguang.scaffold.common.utils.AppUtil;
import org.apache.commons.lang3.StringUtils;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;

/**
 * @Author bangwei.mo
 * @Date 2021-01-13 15:39
 * @Description
 */
public class RedisStandaloneConfigurationBuilder extends AbstractRedissionConfigBuilder implements IRedisConfigurationBuilder<RedisStandaloneConfiguration> {
    @Override
    public RedisStandaloneConfiguration build(ClusterBO cluster) {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        ConnectBO connectBO = cluster.getMasterNode();
        configuration.setHostName(connectBO.getHostName());
        configuration.setPort(connectBO.getPort());
        configuration.setDatabase(cluster.getDatabase());
        configuration.setPassword(StringUtils.isBlank(cluster.getPassword()) ? RedisPassword.none() : RedisPassword.of(cluster.getPassword()));
        return configuration;
    }

    @Override
    public Config redissonBuild(ClusterBO cluster) {
        Config config = new Config();
        SingleServerConfig serverConfig = config.useSingleServer();
        serverConfig.setDatabase(cluster.getDatabase());
        if (StringUtils.isNotBlank(cluster.getPassword())) {
            serverConfig.setPassword(cluster.getPassword());
        }
        serverConfig.setAddress(processAddress(cluster.getMasterNode()));
        if (null != cluster.getConnectParams()) {
            processStandardConnectParams(serverConfig, cluster.getConnectParams());
        }
        return config;
    }
}
