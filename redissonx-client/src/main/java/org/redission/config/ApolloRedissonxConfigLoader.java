package org.redission.config;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
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
        Config config = ConfigService.getAppConfig();
        org.redisson.config.Config redissonConfig = new org.redisson.config.Config();
        SingleServerConfig singleServerConfig = redissonConfig.useSingleServer();

        String address = config.getProperty(buildPropertyKey(clusterName, AppolloConstants.REDIS_ADDRESS), null);
        if (StringUtils.isBlank(address)) {
            LOGGER.error("redissonx clusterName:[" + clusterName + "] not exist in apollo!");
            return null;
        }
        Boolean useHttpsMode = config.getBooleanProperty(buildPropertyKey(clusterName, AppolloConstants.REDIS_USE_HTTPS_MODE), false);
        singleServerConfig.setAddress((useHttpsMode ? "rediss" : "redis") + "://" + address);

        int database = config.getIntProperty(buildPropertyKey(clusterName, AppolloConstants.REDIS_DATABASE), 0);
        singleServerConfig.setDatabase(database);

        int coonectionTimeout = config.getIntProperty(buildPropertyKey(clusterName, AppolloConstants.REDIS_CONNECT_TIMEOUT), 10000);
        singleServerConfig.setConnectTimeout(coonectionTimeout);


        int idleConnectionTimeout = config.getIntProperty(buildPropertyKey(clusterName, AppolloConstants.REDIS_IDLE_CONNECT_TIMEOUT), 10000);
        singleServerConfig.setIdleConnectionTimeout(idleConnectionTimeout);
        int retryAttempts = config.getIntProperty(buildPropertyKey(clusterName, AppolloConstants.REDIS_RETRY_ATTEMPTS), 3);
        singleServerConfig.setRetryAttempts(retryAttempts);
        int retryInterval = config.getIntProperty(buildPropertyKey(clusterName, AppolloConstants.REDIS_RETRY_INTERVAL), 1500);
        singleServerConfig.setRetryAttempts(retryInterval);
        String password = config.getProperty(buildPropertyKey(clusterName, AppolloConstants.REDIS_PASSWORD), null);
        singleServerConfig.setPassword(password);
        int connectionMinimumIdleSize = config.getIntProperty(buildPropertyKey(clusterName, AppolloConstants.REDIS_CONNECT_MINIDLESIZE), 32);
        singleServerConfig.setConnectionPoolSize(connectionMinimumIdleSize);

        int connectionPoolSize = config.getIntProperty(buildPropertyKey(clusterName, AppolloConstants.REDIS_CONNECT_POOLSIZE), 64);
        singleServerConfig.setConnectionPoolSize(connectionPoolSize);

        int subscriptionConnectionMinimumIdleSize = config.getIntProperty(buildPropertyKey(clusterName, AppolloConstants.REDIS_SUBSCRIPTION_CONNECT_MINIDLESIZE), 1);
        singleServerConfig.setSubscriptionConnectionPoolSize(subscriptionConnectionMinimumIdleSize);


        int subscriptionConnectionPoolSize = config.getIntProperty(buildPropertyKey(clusterName, AppolloConstants.REDIS_SUBSCRIPTION_CONNECT_POOLSIZE), 32);
        singleServerConfig.setSubscriptionConnectionPoolSize(subscriptionConnectionPoolSize);
        return redissonConfig;


    }

    private String buildPropertyKey(String clusterName, String propertyKey) {
        StringBuilder buf = new StringBuilder();
        buf.append(AppolloConstants.APOLLO_CLUSTER_PREFIX);
        buf.append(".");
        buf.append(clusterName);
        buf.append(".");
        buf.append(propertyKey);
        return buf.toString();
    }

    public static class AppolloConstants {
        public static final String APOLLO_CLUSTER_PREFIX = "cluster";

        public static final String REDIS_ADDRESS = "redis.address";

        public static final String REDIS_MODE = "redis.mode";

        public static final String REDIS_DATABASE = "redis.database";

        public static final String REDIS_USE_HTTPS_MODE = "redis.use.https.mode";

        public static final String REDIS_PASSWORD = "redis.password";

        public static final String REDIS_CONNECT_TIMEOUT = "redis.connect.timeout";

        public static final String REDIS_IDLE_CONNECT_TIMEOUT = "redis.idle.connect.timeout";

        public static final String REDIS_CONNECT_MINIDLESIZE = "redis.connect.minIdleSize";

        public static final String REDIS_CONNECT_POOLSIZE = "redis.connect.poolSize";

        public static final String REDIS_SUBSCRIPTION_CONNECT_MINIDLESIZE = "redis.subscription.connect.minIdleSize";

        public static final String REDIS_SUBSCRIPTION_CONNECT_POOLSIZE = "redis.subscription.connect.poolSize";

        public static final String REDIS_RETRY_ATTEMPTS = "redis.retry.attempts";

        public static final String REDIS_RETRY_INTERVAL = "redis.retry.interval";
    }
}
