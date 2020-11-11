package com.leshiguang.redissonx.common.zookeeper;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigService;
import com.leshiguang.arch.common.util.RegionUtil;
import com.leshiguang.redissonx.common.constants.RedissonxConstants;
import com.leshiguang.redissonx.common.entity.category.CategoryBO;
import com.leshiguang.redissonx.common.entity.cluster.ClusterAuthStrategyBO;
import com.leshiguang.redissonx.common.entity.cluster.ClusterBO;
import com.leshiguang.redissonx.common.entity.cluster.ClusterConnectBO;
import com.leshiguang.redissonx.common.entity.cluster.ClusterSimpleBO;
import com.leshiguang.redissonx.common.path.PathProvider;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-20 14:35
 * @Modify
 */
public class ZookeeperClientImpl implements ZookeeperClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZookeeperClientImpl.class);

    private Config config = ConfigService.getConfig(RedissonxConstants.APOLLO_NS);
    private ZkClient zkClient;
    private PathProvider pathProvider;
    private ZookeeperConfig zkConfig;
    private String region;

    public ZookeeperClientImpl() {
        ZookeeperConfig zkConfig = new ZookeeperConfig();
        zkConfig.setAddress(config.getProperty(RedissonxConstants.ZK_ADDRESS, ""));
        zkConfig.setConnectTimeout(config.getIntProperty(RedissonxConstants.ZK_CONNECTION_TIMEOUT, zkConfig.getConnectTimeout()));
        zkConfig.setSessionTimeout(config.getIntProperty(RedissonxConstants.ZK_SESSION_TIMEOUT, zkConfig.getSessionTimeout()));
        zkConfig.setOperationRetryTimeout(config.getLongProperty(RedissonxConstants.ZK_OPERATION_RETRY_TIMEOUT, zkConfig.getOperationRetryTimeout()));
        config.addChangeListener(configChangeEvent -> {
            if (configChangeEvent.getNamespace().equalsIgnoreCase(RedissonxConstants.APOLLO_NS)) {
                for (String changeKey : configChangeEvent.changedKeys()) {
                    if (changeKey.startsWith("zk")) {
                        renewZkClient();
                    }
                }
            }
        });
        this.zkConfig = zkConfig;
        this.region = RegionUtil.getRegionKey();
        this.init();
    }

    public ZookeeperClientImpl(String region, ZookeeperConfig zkConfig) {
        this.region = region;
        this.zkConfig = zkConfig;
        this.init();

    }

    protected void init() {
        preparePathProvider();
        zkClient = prepareZkClient();
    }

    protected void preparePathProvider() {
        pathProvider = new PathProvider();
        pathProvider.addTemplate("cluster-base", "/redissonx/cluster/$0/$1");
        pathProvider.addTemplate("cluster", "/redissonx/cluster/$0/$1/info");
        pathProvider.addTemplate("clusterConnects", "/redissonx/cluster/$0/$1/connects");
        pathProvider.addTemplate("clusterAuthStrategys", "/redissonx/cluster/$0/$1/auth/strategys");
        pathProvider.addTemplate("categoryPage", "/redissonx/cluster/$0/$1/bucket");
        pathProvider.addTemplate("categoryBucket", "/redissonx/cluster/$0/$1/bucket/@bucket");
        pathProvider.addTemplate("category", "/redissonx/cluster/$0/$1/bucket/@bucket/$2");
    }

    public ZkClient prepareZkClient() {
        String zkAddress = zkConfig.getAddress();
        if (StringUtils.isBlank(zkAddress)) {
            throw new NullPointerException("redissonx zookeeper address is empty");
        }
        Integer zkSessionTimeout = zkConfig.getSessionTimeout();
        Integer zkConnectionTimeout = zkConfig.getConnectTimeout();
        Long operationRetryTimeout = zkConfig.getOperationRetryTimeout();
        ZkClient zkClient = new ZkClient(zkAddress, zkSessionTimeout, zkConnectionTimeout, new SerializableSerializer(), operationRetryTimeout);
        zkClient.subscribeStateChanges(new IZkStateListener() {

            @Override
            public void handleStateChanged(Watcher.Event.KeeperState keeperState) throws Exception {
                try {
//                    if (keeperState == Watcher.Event.KeeperState.Disconnected) {
//                        renewZkClient();
//                    }
                } catch (Exception e) {
                    LOGGER.error("exception when handler zk state change", e);
                }
            }

            @Override
            public void handleNewSession() throws Exception {

            }

            @Override
            public void handleSessionEstablishmentError(Throwable throwable) throws Exception {

            }
        });

        return zkClient;
    }

    private void renewZkClient() {
        ZkClient _new_zkClient = prepareZkClient();
        ZkClient _old_zkClient = this.zkClient;
        this.zkClient = _new_zkClient;
        if (_old_zkClient != null) {
            try {
                _old_zkClient.unsubscribeAll();
                _old_zkClient.close();
            } catch (Exception e) {
                LOGGER.error("failed to close zkclient: " + e.getMessage(), e);
            }
        }
    }

    @Override
    public ClusterBO getCluster(String clusterName) {
        ClusterBO cluster = new ClusterBO();
        String clusterPath = pathProvider.getPath("cluster", clusterName, region);
        if (zkClient.exists(clusterPath)) {
            ClusterSimpleBO clusterSimple = zkClient.readData(clusterPath, true);
            cluster.setCluster(clusterSimple);
        }
        String connectsPath = pathProvider.getPath("clusterConnects", clusterName, region);
        if (zkClient.exists(connectsPath)) {
            List<ClusterConnectBO> connectList = zkClient.readData(connectsPath, true);
            cluster.setConnects(connectList);
        }
        String authStrategysPath = pathProvider.getPath("clusterAuthStrategys", clusterName, region);
        if (zkClient.exists(authStrategysPath)) {
            List<ClusterAuthStrategyBO> authStrategyList = zkClient.readData(authStrategysPath, true);
            cluster.setAuthStrategies(authStrategyList);
        }
        return cluster;
    }

    @Override
    public boolean setCluster(String clusterName, ClusterBO cluster) {
        if (null != cluster.getCluster()) {
            String clusterPath = pathProvider.getPath("cluster", clusterName, region);
            if (!zkClient.exists(clusterPath)) {
                zkClient.createPersistent(clusterPath, true);
            }
            zkClient.writeData(clusterPath, cluster.getCluster());
        }
        if (CollectionUtils.isNotEmpty(cluster.getConnects())) {
            String connectsPath = pathProvider.getPath("clusterConnects", clusterName, region);
            if (!zkClient.exists(connectsPath)) {
                zkClient.createPersistent(connectsPath, true);
            }
            zkClient.writeData(connectsPath, cluster.getConnects());
        }
        if (CollectionUtils.isNotEmpty(cluster.getAuthStrategies())) {
            String authStrategysPath = pathProvider.getPath("clusterAuthStrategys", clusterName, region);
            if (!zkClient.exists(authStrategysPath)) {
                zkClient.createPersistent(authStrategysPath, true);
            }
            zkClient.writeData(authStrategysPath, cluster.getAuthStrategies());
        }
        return true;
    }

    @Override
    public boolean existCluster(String clusterName) {
        String clusterPath = pathProvider.getPath("cluster", clusterName, region);
        return zkClient.exists(clusterPath);
    }

    @Override
    public boolean deleteCluster(String clusterName) {
        String clusterPath = pathProvider.getPath("cluster-base", clusterName, region);
        if (zkClient.exists(clusterPath)) {
            return zkClient.deleteRecursive(clusterPath);
        } else {
            return false;
        }
    }


    @Override
    public CategoryBO getCategory(String clusterName, String category) {
        Integer bucket = category.hashCode() % 50 + 50;
        String categoryPath = pathProvider.getPathWithBucket("category", bucket, clusterName, region, category);
        if (zkClient.exists(categoryPath)) {
            CategoryBO categoryBO = zkClient.readData(categoryPath, true);
            return categoryBO;
        }
        return null;
    }

    @Override
    public boolean setCategory(String clusterName, CategoryBO category) {
        Integer bucket = category.getCategory().hashCode() % 50 + 50;
        String categoryPath = pathProvider.getPathWithBucket("category", bucket, clusterName, region, category.getCategory());
        try {
            if (!zkClient.exists(categoryPath)) {
                zkClient.createPersistent(categoryPath, true);
            }
            zkClient.writeData(categoryPath, category);
            return true;
        } catch (Exception e) {
            LOGGER.warn("exception for setCategory", e);
            return false;
        }

    }

    @Override
    public void addCategoryConfigListener(String clusterName, String category, IZkDataListener listener) {
        Integer bucket = category.hashCode() % 50 + 50;
        String path = pathProvider.getPathWithBucket("category", bucket, clusterName, region, category);
        zkClient.subscribeDataChanges(path, listener);
    }

    @Override
    public void addAuthStrategoyConfigListener(String clusterName, IZkDataListener listener) {
        String path = pathProvider.getPath("clusterAuthStrategys", clusterName, region);
        zkClient.subscribeDataChanges(path, listener);
    }

    @Override
    public void addConnectConfigListener(String clusterName, IZkDataListener listener) {
        String path = pathProvider.getPath("clusterConnects", clusterName, region);
        zkClient.subscribeDataChanges(path, listener);

    }
}
