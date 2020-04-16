package org.redisson.config;

import com.leshiguang.arch.redissonx.exception.StoreConfigException;
import com.leshiguang.redissonx.common.entity.cluster.ClusterBO;
import com.leshiguang.redissonx.common.zookeeper.ZookeeperClient;
import com.leshiguang.redissonx.common.zookeeper.ZookeeperClientImpl;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-24 17:06
 * @Modify
 */
public class ZookeeperRedissonxConfigLoader implements RedissonxConfigLoader {
    private ZookeeperClient zookeeperClient = new ZookeeperClientImpl();

    @Override
    public Config getByCluster(String clusterName) {
        return getByCluster(clusterName, null);
    }

    @Override
    public Config getByCluster(String clusterName, RedissonxConnectConfig connectConfig) {
        if (!zookeeperClient.existCluster(clusterName)) {
            throw new StoreConfigException("cluster not exist for clusterName:" + clusterName);
        }
        ClusterBO clusterBO = zookeeperClient.getCluster(clusterName);
        if (null != clusterBO) {
            //应用权限判断
            //TODO
            String clusterMode = clusterBO.getMode();
            IConfigBuilder configBuilder = getConfigBuilder(clusterMode);
            return configBuilder.build(clusterBO, connectConfig);
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
