package org.redisson.config;

import com.leshiguang.arch.common.util.RegionUtil;
import com.leshiguang.arch.redissonx.exception.StoreConfigException;
import com.leshiguang.redissonx.common.entity.cluster.ClusterBO;
import com.leshiguang.redissonx.common.zookeeper.ZookeeperClient;
import com.leshiguang.redissonx.common.zookeeper.ZookeeperClientFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-24 17:06
 * @Modify
 */
public class ZookeeperRedissonxConfigLoader implements RedissonxConfigLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperRedissonxConfigLoader.class);

    @Override
    public String getName() {
        return "zookeeper";
    }

    @Override
    public RedissonxConfig getByCluster(String clusterName, RedissonxConnectConfig connectConfig) {
        ZookeeperClient zookeeperClient = ZookeeperClientFactory.getDefaultInstance();
        return get(zookeeperClient, clusterName, RegionUtil.getRegionKey(), connectConfig);

    }

    @Override
    public RedissonxConfig getByClusterAndRegion(String clusterName, String reigon) {
        return getByClusterAndRegion(clusterName, reigon, null);

    }

    @Override
    public RedissonxConfig getByClusterAndRegion(String clusterName, String region, RedissonxConnectConfig connectConfig) {
        try {
            ZookeeperClient zookeeperClient = ZookeeperClientFactory.getInstance(region);
            if (null != zookeeperClient) {
                return get(zookeeperClient, clusterName, region, connectConfig);
            } else {
                throw new StoreConfigException("cluster not exist for region:" + region);
            }
        } catch (StoreConfigException e) {
            LOGGER.warn(e.getMessage());
            return null;
        }
    }

    private RedissonxConfig get(ZookeeperClient zookeeperClient, String clusterName, String region, RedissonxConnectConfig connectConfig) {
        if (!zookeeperClient.existCluster(clusterName)) {
            throw new StoreConfigException("cluster not exist for clusterName:" + clusterName);
        }
        ClusterBO cluster = zookeeperClient.getCluster(clusterName);
        if (null != cluster) {
            String clusterMode = cluster.getCluster().getMode();
            IConfigBuilder configBuilder = getConfigBuilder(clusterMode);
            RedissonxConfig redissonxConfig = configBuilder.build(cluster, connectConfig);
            redissonxConfig.setRegion(region);
            redissonxConfig.setClusterName(clusterName);
            return redissonxConfig;
        } else {
            throw new StoreConfigException("can't find cluster from zk for clusterName:" + clusterName);
        }
    }

    private IConfigBuilder getConfigBuilder(String clusterMode) {
        switch (clusterMode) {
            case "single":
                return new SingleConfigBuilder();
            default:
                return new SingleConfigBuilder();
        }
    }

}
