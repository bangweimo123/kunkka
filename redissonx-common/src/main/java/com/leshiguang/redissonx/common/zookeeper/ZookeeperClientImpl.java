package com.leshiguang.redissonx.common.zookeeper;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.leshiguang.redissonx.common.constants.RedissonxConstants;
import com.leshiguang.redissonx.common.entity.category.CategoryBO;
import com.leshiguang.redissonx.common.entity.cluster.ClusterBO;
import com.leshiguang.redissonx.common.entity.hotkey.HotKeyBO;
import com.leshiguang.redissonx.common.path.PathProvider;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
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


    public ZookeeperClientImpl() {
        try {
            init();
        } catch (Exception e) {
            LOGGER.error("failed to init zk StoreConfigClient", e);
        }
    }

    protected void init() {
        preparePathProvider();
        zkClient = prepareZkClient();
        config.addChangeListener(new ConfigChangeListener() {
            @Override
            public void onChange(ConfigChangeEvent configChangeEvent) {
                if (configChangeEvent.getNamespace().equalsIgnoreCase(RedissonxConstants.APOLLO_NS)) {
                    for (String changeKey : configChangeEvent.changedKeys()) {
                        if (changeKey.startsWith("zk")) {
                            renewZkClient();
                        }
                    }
                }
            }
        });
    }

    protected void preparePathProvider() {
        pathProvider = new PathProvider();
        pathProvider.addTemplate("clusterList", "/redissonx/cluster");
        pathProvider.addTemplate("cluster", "/redissonx/cluster/$0/info");
        pathProvider.addTemplate("strict", "/redissonx/cluster/$0/auth/strict");
        pathProvider.addTemplate("application", "/redissonx/cluster/$0/auth/applications");
        pathProvider.addTemplate("categoryPage", "/redissonx/cluster/$0/bucket");
        pathProvider.addTemplate("categoryBucket", "/redissonx/cluster/$0/bucket/@bucket");
        pathProvider.addTemplate("category", "/redissonx/cluster/$0/bucket/@bucket/$1");
        pathProvider.addTemplate("hotkey", "/redissonx/cluster/$0/hotkeys");
    }

    public ZkClient prepareZkClient() {
        String zkAddress = config.getProperty(RedissonxConstants.ZK_ADDRESS, "");
        if (StringUtils.isBlank(zkAddress)) {
            throw new NullPointerException("redissonx zookeeper address is empty");
        }
        Integer zkSessionTimeout = config.getIntProperty(RedissonxConstants.ZK_SESSION_TIMEOUT, 30000);
        Integer zkConnectionTimeout = config.getIntProperty(RedissonxConstants.ZK_CONNECTION_TIMEOUT, 2147483647);
        Long operationRetryTimeout = config.getLongProperty(RedissonxConstants.ZK_OPERATION_RETRY_TIMEOUT, -1L);
        ZkClient zkClient = new ZkClient(zkAddress, zkSessionTimeout, zkConnectionTimeout, new SerializableSerializer(), operationRetryTimeout);
        zkClient.subscribeStateChanges(new IZkStateListener() {

            @Override
            public void handleStateChanged(Watcher.Event.KeeperState keeperState) throws Exception {
                try {
                    if (keeperState == Watcher.Event.KeeperState.Disconnected) {
                        renewZkClient();
                    }
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
                LOGGER.error("failed to close zkclient: " + e.getMessage());
            }
        }
    }

    @Override
    public ZkClient getZkClient() {
        return zkClient;
    }

    @Override
    public List<ClusterBO> loadAllCluster() {
        String path = pathProvider.getPath("clusterList");
        List<ClusterBO> clusterList = new ArrayList<>();
        if (zkClient.exists(path)) {
            List<String> clusterNameList = zkClient.getChildren(path);
            if (CollectionUtils.isNotEmpty(clusterNameList)) {
                for (String clusterName : clusterNameList) {
                    String clusterPath = pathProvider.getPath("cluster", clusterName);
                    ClusterBO clusterBO = zkClient.readData(clusterPath, true);
                    if (null != clusterBO) {
                        clusterList.add(clusterBO);
                    }
                }
            }
        }
        return clusterList;
    }

    @Override
    public ClusterBO getCluster(String clusterName) {
        String clusterPath = pathProvider.getPath("cluster", clusterName);
        if (zkClient.exists(clusterPath)) {
            ClusterBO clusterBO = zkClient.readData(clusterPath, true);
            return clusterBO;
        }
        return null;
    }

    @Override
    public void setCluster(ClusterBO cluster) {
        String clusterPath = pathProvider.getPath("cluster", cluster.getClusterName());
        if (!zkClient.exists(clusterPath)) {
            zkClient.createPersistent(clusterPath, true);
        }
        zkClient.writeData(clusterPath, cluster);
    }

    @Override
    public boolean deleteCluster(String clusterName) {
        String clusterPath = pathProvider.getPath("cluster", clusterName);
        if (zkClient.exists(clusterPath)) {
            return zkClient.deleteRecursive(clusterPath);
        } else {
            return false;
        }
    }

    @Override
    public List<String> queryCategorys(String clusterName) {
        String categoryBucketPath = pathProvider.getPath("categoryPage", clusterName);
        List<String> categoryList = new ArrayList<>();
        try {
            if (zkClient.exists(categoryBucketPath)) {
                List<String> bucketList = zkClient.getChildren(categoryBucketPath);
                if (!CollectionUtils.isEmpty(bucketList)) {
                    for (String bucket : bucketList) {
                        String categoryPath = pathProvider.getPathWithBucket("categoryBucket", Integer.parseInt(bucket), clusterName);
                        if (zkClient.exists(categoryPath)) {
                            List<String> bucketCategoryList = zkClient.getChildren(categoryPath);
                            categoryList.addAll(bucketCategoryList);
                        }
                    }
                }
                return categoryList;
            }
        } catch (Exception e) {
            LOGGER.warn("exception for setCategory", e);
        }
        return null;
    }

    @Override
    public CategoryBO getCategory(String clusterName, String category) {
        Integer bucket = category.hashCode() % 50 + 50;
        String categoryPath = pathProvider.getPathWithBucket("category", bucket, clusterName, category);
        if (zkClient.exists(categoryPath)) {
            CategoryBO categoryBO = zkClient.readData(categoryPath, true);
            return categoryBO;
        }
        return null;
    }

    @Override
    public boolean setCategory(String clusterName, CategoryBO category) {
        Integer bucket = category.getCategory().hashCode() % 50 + 50;
        String categoryPath = pathProvider.getPathWithBucket("category", bucket, clusterName, category.getCategory());
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
    public boolean deleteCategory(String clusterName, String category) {
        try {
            CategoryBO categoryBO = getCategory(clusterName, category);
            if (null != categoryBO) {
                categoryBO.setVersion("none");
                return setCategory(clusterName, categoryBO);
            } else {
                return false;
            }
        } catch (Exception e) {
            LOGGER.warn("exception for deleteCategory", e);
            return false;
        }
    }

    @Override
    public List<HotKeyBO> loadHotKey(String clusterName) {
        return null;
    }

    @Override
    public Boolean isStrictAuth(String clusterName) {
        return null;
    }

    @Override
    public List<String> authApps(String clusterName) {
        return null;
    }

    @Override
    public void addCategoryConfigListener(String clusterName, String category, IZkDataListener listener) {
        Integer bucket = category.hashCode() % 50 + 50;
        String path = pathProvider.getPathWithBucket("category", bucket, clusterName, category);
        zkClient.subscribeDataChanges(path, listener);
    }

    @Override
    public void addHotKeyConfigListener(String clusterName, IZkDataListener listener) {
        String path = pathProvider.getPath("hotkey", clusterName);
        zkClient.subscribeDataChanges(path, listener);
    }

    @Override
    public void addAuthAppsListner(String clusterName, IZkChildListener listener) {
        String path = pathProvider.getPath("application", clusterName);
        zkClient.subscribeChildChanges(path, listener);
    }
}
