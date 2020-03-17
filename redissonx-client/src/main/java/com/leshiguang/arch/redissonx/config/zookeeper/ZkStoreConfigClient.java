package com.leshiguang.arch.redissonx.config.zookeeper;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.leshiguang.arch.redissonx.config.store.HotKeyConfig;
import com.leshiguang.arch.redissonx.client.RedissonxConstants;
import com.leshiguang.arch.redissonx.config.store.StoreCategoryConfig;
import com.leshiguang.redissonx.common.path.PathProvider;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.apache.commons.lang3.StringUtils;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-16 13:20
 * @Modify
 */
public class ZkStoreConfigClient implements StoreConfigClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZkStoreConfigClient.class);
    private Config config = ConfigService.getConfig(RedissonxConstants.APOLLO_NS);

    private ZkClient zkClient;

    private PathProvider pathProvider;

    public ZkStoreConfigClient() {
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
        pathProvider.addTemplate("cluster", "/redissonx/$0/auth");
        pathProvider.addTemplate("applications", "/redissonx/$0/auth/applications");
        pathProvider.addTemplate("category", "/redissonx/$0/bucket/@bucket/$1");
        pathProvider.addTemplate("hotkey", "/redissonx/$0/hotkey");
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
    public Boolean isStrictAuth(String clusterName) {
        String path = pathProvider.getPath("cluster", clusterName);
        Boolean result = zkClient.readData(path);
        return result;
    }

    @Override
    public List<String> loadAuthApps(String clusterName) {
        String path = pathProvider.getPath("applications", clusterName);
        List<String> result = zkClient.getChildren(path);
        return result;
    }

    @Override
    public Map<String, HotKeyConfig> loadHotKeyConfigs(String clusterName) {
        String path = pathProvider.getPath("hotkey", clusterName);
        Map<String, HotKeyConfig> result = zkClient.readData(path);
        return result;
    }

    @Override
    public StoreCategoryConfig getStoreCategoryConfig(String clusterName, String category) {
        Integer bucket = category.hashCode() % 50 + 50;
        String path = pathProvider.getPathWithBucket("category", bucket, clusterName, category);
        StoreCategoryConfig result = zkClient.readData(path);
        return result;
    }

    @Override
    public void setStoreCategoryConfig(String clusterName, StoreCategoryConfig config) {
        Integer bucket = config.getCategory().hashCode() % 50 + 50;
        String path = pathProvider.getPathWithBucket("category", bucket, clusterName, config.getCategory());
        boolean existPath = zkClient.exists(path);
        if (!existPath) {
            zkClient.createPersistent(path, true);
        }
        zkClient.writeData(path, config);
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
        String path = pathProvider.getPath("applications", clusterName);
        zkClient.subscribeChildChanges(path, listener);
    }
}
