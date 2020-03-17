package org.redission.config;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.leshiguang.arch.redissonx.client.RedissonxConstants;
import org.apache.commons.lang3.StringUtils;
import org.redisson.config.SingleServerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-13 16:39
 * @Modify
 */
public class ApolloRedissonxConfigLoader implements RedissonxConfigLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ApolloRedissonxConfigLoader.class);

    @Override
    public org.redisson.config.Config getByCluster(String clusterName) {
        Config config = ConfigService.getConfig(RedissonxConstants.APOLLO_NS);
        org.redisson.config.Config redissonConfig = new org.redisson.config.Config();
        SingleServerConfig singleServerConfig = redissonConfig.useSingleServer();

        String address = config.getProperty(buildPropertyKey(clusterName, RedissonxConstants.REDIS_ADDRESS), null);
        if (StringUtils.isBlank(address)) {
            LOGGER.error("redissonx clusterName:[" + clusterName + "] not exist in apollo!");
            return null;
        }
        Boolean useHttpsMode = config.getBooleanProperty(buildPropertyKey(clusterName, RedissonxConstants.REDIS_USE_HTTPS_MODE), false);
        singleServerConfig.setAddress((useHttpsMode ? "rediss" : "redis") + "://" + address);

        int database = config.getIntProperty(buildPropertyKey(clusterName, RedissonxConstants.REDIS_DATABASE), 0);
        singleServerConfig.setDatabase(database);

        int coonectionTimeout = config.getIntProperty(buildPropertyKey(clusterName, RedissonxConstants.REDIS_CONNECT_TIMEOUT), 10000);
        singleServerConfig.setConnectTimeout(coonectionTimeout);


        int idleConnectionTimeout = config.getIntProperty(buildPropertyKey(clusterName, RedissonxConstants.REDIS_IDLE_CONNECT_TIMEOUT), 10000);
        singleServerConfig.setIdleConnectionTimeout(idleConnectionTimeout);
        int retryAttempts = config.getIntProperty(buildPropertyKey(clusterName, RedissonxConstants.REDIS_RETRY_ATTEMPTS), 3);
        singleServerConfig.setRetryAttempts(retryAttempts);
        int retryInterval = config.getIntProperty(buildPropertyKey(clusterName, RedissonxConstants.REDIS_RETRY_INTERVAL), 1500);
        singleServerConfig.setRetryAttempts(retryInterval);
        String password = config.getProperty(buildPropertyKey(clusterName, RedissonxConstants.REDIS_PASSWORD), null);
        singleServerConfig.setPassword(password);


        int connectionMinimumIdleSize = config.getIntProperty(buildPropertyKey(clusterName, RedissonxConstants.REDIS_CONNECT_MINIDLESIZE), 32);
        singleServerConfig.setConnectionPoolSize(connectionMinimumIdleSize);

        int connectionPoolSize = config.getIntProperty(buildPropertyKey(clusterName, RedissonxConstants.REDIS_CONNECT_POOLSIZE), 64);
        singleServerConfig.setConnectionPoolSize(connectionPoolSize);

        int subscriptionConnectionMinimumIdleSize = config.getIntProperty(buildPropertyKey(clusterName, RedissonxConstants.REDIS_SUBSCRIPTION_CONNECT_MINIDLESIZE), 1);
        singleServerConfig.setSubscriptionConnectionPoolSize(subscriptionConnectionMinimumIdleSize);


        int subscriptionConnectionPoolSize = config.getIntProperty(buildPropertyKey(clusterName, RedissonxConstants.REDIS_SUBSCRIPTION_CONNECT_POOLSIZE), 32);
        singleServerConfig.setSubscriptionConnectionPoolSize(subscriptionConnectionPoolSize);
        return redissonConfig;


    }

    private String buildPropertyKey(String clusterName, String propertyKey) {
        StringBuilder buf = new StringBuilder();
        buf.append(RedissonxConstants.APOLLO_CLUSTER_PREFIX);
        buf.append(".");
        buf.append(clusterName);
        buf.append(".");
        buf.append(propertyKey);
        return buf.toString();
    }
}
