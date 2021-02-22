package com.leshiguang.arch.kunkka.client.configure.zookeeper;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.leshiguang.arch.kunkka.client.configure.IConfigCallback;
import com.leshiguang.arch.kunkka.client.configure.IConfigureClient;
import com.leshiguang.arch.kunkka.client.exception.KunkkaConfigException;
import com.leshiguang.arch.kunkka.common.entity.category.CategoryBO;
import com.leshiguang.arch.kunkka.common.entity.cluster.ClusterBO;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.IZkStateListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.SerializableSerializer;
import org.I0Itec.zkclient.serialize.ZkSerializer;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.zookeeper.Watcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author bangwei.mo
 * @Date 2020-03-20 14:35
 * @Modify
 */
public class ZKConfigureClient implements IConfigureClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZKConfigureClient.class);
    private String region;
    private ZKPathProvider pathProvider;
    private ZkSerializer serializer;
    private ZkClient zkClient;
    private ZKConfig zkConfig;

    public ZKConfigureClient(String region, ZKConfig zkConfig) {
        init(region, zkConfig);
    }

    private void init(String region, ZKConfig zkConfig) {
        this.region = region;
        this.zkConfig = zkConfig;
    }

    public void setPathProvider(ZKPathProvider pathProvider) {
        this.pathProvider = pathProvider;
    }

    public void setSerializer(ZkSerializer serializer) {
        this.serializer = serializer;
    }

    protected ZkClient initZkClient() {
        if (null == zkConfig) {
            throw new KunkkaConfigException(String.format("zkConfig for region:%s is empty!", region));
        }
        String zkAddress = zkConfig.getAddress();
        if (StringUtils.isBlank(zkAddress)) {
            throw new KunkkaConfigException("zkClient init error for empty zkAddress!");
        }
        Integer zkSessionTimeout = zkConfig.getSessionTimeout();
        Integer zkConnectionTimeout = zkConfig.getConnectTimeout();
        Long operationRetryTimeout = zkConfig.getOperationRetryTimeout();
        ZkClient zkClient = new ZkClient(zkAddress, zkSessionTimeout, zkConnectionTimeout, serializer, operationRetryTimeout);
        zkClient.subscribeStateChanges(new IZkStateListener() {

            @Override
            public void handleStateChanged(Watcher.Event.KeeperState keeperState) throws Exception {
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

    @Override
    public void start() {
        // init pathProvider
        if (null == pathProvider) {
            pathProvider = new ZKPathProvider();
            pathProvider.addTemplate("cluster", "/kunkka/cluster/$0/$1");
            pathProvider.addTemplate("category", "/kunkka/cluster/$0/$1/category/@bucket/$2");
        }
        // init serializer
        if (null == serializer) {
            serializer = new SerializableSerializer();
        }
        this.zkClient = initZkClient();
    }

    private void renewZkClient() {
        ZkClient _pre = zkClient;
        ZkClient _new = null;
        try {
            _new = initZkClient();
        } catch (Exception e) {
            LOGGER.error("failed to  init new ZkClient!", e);
        }
        if (null == _new) {
            return;
        } else {
            this.zkClient = _new;
            try {
                _pre.unsubscribeAll();
                _pre.close();
            } catch (Exception e) {
                LOGGER.error("failed to close old ZKClient!", e);
            }
        }


    }

    @Override
    public ClusterBO getCluster(String clusterName) {
        String clusterPath = pathProvider.getPath("cluster", region, clusterName);
        if (zkClient.exists(clusterPath)) {
            String zkData = zkClient.readData(clusterPath, true);
            if (null == zkData) {
                throw new KunkkaConfigException("zkData empty");
            }
            ClusterBO clusterBO = JSON.parseObject(zkData, new TypeReference<ClusterBO>() {
            });
            return clusterBO;
        } else {
            return null;
        }
    }

    @Override
    public boolean setCluster(String clusterName, ClusterBO cluster) {
        if (null != cluster) {
            String clusterPath = pathProvider.getPath("cluster", region, clusterName);
            if (!zkClient.exists(clusterPath)) {
                zkClient.createPersistent(clusterPath, true);
            }
            String zkData = JSON.toJSONString(cluster);
            zkClient.writeData(clusterPath, zkData);
        }
        return true;
    }

    @Override
    public boolean deleteCluster(String clusterName) {
        String clusterPath = pathProvider.getPath("cluster", region, clusterName);
        if (zkClient.exists(clusterPath)) {
            return zkClient.deleteRecursive(clusterPath);
        } else {
            return false;
        }
    }

    @Override
    public void clusterWatch(String clusterName, IConfigCallback configCallback) {
        String clusterPath = pathProvider.getPath("cluster", region, clusterName);
        IZkDataListener zkDataListener = new ClusterWatchListener(clusterName, configCallback);
        zkClient.unsubscribeDataChanges(clusterPath, zkDataListener);
        zkClient.subscribeDataChanges(clusterPath, zkDataListener);
    }

    class ClusterWatchListener implements IZkDataListener {
        private String clusterName;

        private IConfigCallback configCallback;

        public ClusterWatchListener(String clusterName, IConfigCallback configCallback) {
            this.clusterName = clusterName;
            this.configCallback = configCallback;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            ClusterWatchListener that = (ClusterWatchListener) o;

            return new EqualsBuilder()
                    .append(clusterName, that.clusterName)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(clusterName)
                    .toHashCode();
        }

        @Override
        public void handleDataChange(String s, Object o) throws Exception {
            String zkData = (String) o;
            ClusterBO clusterBO = JSON.parseObject(zkData, new TypeReference<ClusterBO>() {
            });
            configCallback.changed(clusterBO);
        }

        @Override
        public void handleDataDeleted(String s) throws Exception {
            configCallback.deleted();
        }
    }

    @Override
    public CategoryBO getCategory(String clusterName, String category) {
        Integer bucket = category.hashCode() % 50 + 50;
        String categoryPath = pathProvider.getPathWithBucket("category", bucket, region, clusterName, category);
        if (zkClient.exists(categoryPath)) {
            String zkData = zkClient.readData(categoryPath, true);
            if (null == zkData) {
                throw new KunkkaConfigException("zkData empty");
            }
            CategoryBO categoryBO = JSON.parseObject(zkData, new TypeReference<CategoryBO>() {
            });
            return categoryBO;
        }
        return null;
    }

    @Override
    public boolean setCategory(String clusterName, CategoryBO category) {
        Integer bucket = category.getCategory().hashCode() % 50 + 50;
        String categoryPath = pathProvider.getPathWithBucket("category", bucket, region, clusterName, category.getCategory());
        try {
            if (!zkClient.exists(categoryPath)) {
                zkClient.createPersistent(categoryPath, true);
            }
            String zkData = JSON.toJSONString(category);
            zkClient.writeData(categoryPath, zkData);
            return true;
        } catch (Exception e) {
            LOGGER.warn("exception for setCategory", e);
            return false;
        }

    }

    @Override
    public boolean deleteCategory(String clusterName, String category) {
        Integer bucket = category.hashCode() % 50 + 50;
        String categoryPath = pathProvider.getPathWithBucket("category", bucket, region, clusterName, category);
        try {
            if (zkClient.exists(categoryPath)) {
                return zkClient.delete(categoryPath);
            }
            return true;
        } catch (Exception e) {
            LOGGER.warn("exception for deleteCategory", e);
            return false;
        }
    }

    @Override
    public void categoryWatch(String clusterName, String category, IConfigCallback configCallback) {
        Integer bucket = category.hashCode() % 50 + 50;
        String categoryPath = pathProvider.getPathWithBucket("category", bucket, region, clusterName, category);
        IZkDataListener zkDataListener = new CategoryWatchListener(clusterName, category, configCallback);
        zkClient.unsubscribeDataChanges(categoryPath, zkDataListener);
        zkClient.subscribeDataChanges(categoryPath, zkDataListener);
    }

    class CategoryWatchListener implements IZkDataListener {
        private String clusterName;

        private String category;

        private IConfigCallback configCallback;

        public CategoryWatchListener(String clusterName, String category, IConfigCallback configCallback) {
            this.clusterName = clusterName;
            this.category = category;
            this.configCallback = configCallback;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;

            if (o == null || getClass() != o.getClass()) return false;

            CategoryWatchListener that = (CategoryWatchListener) o;

            return new EqualsBuilder()
                    .append(clusterName, that.clusterName)
                    .append(category, that.category)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder(17, 37)
                    .append(clusterName)
                    .append(category)
                    .toHashCode();
        }

        @Override
        public void handleDataChange(String s, Object o) throws Exception {
            String zkData = (String) o;
            CategoryBO categoryBO = JSON.parseObject(zkData, new TypeReference<CategoryBO>() {
            });
            configCallback.changed(categoryBO);
        }

        @Override
        public void handleDataDeleted(String s) throws Exception {
            configCallback.deleted();
        }
    }

    @Override
    public void stop() {
        try {
            zkClient.unsubscribeAll();
        } catch (Exception e) {
            LOGGER.error("failed to close old ZKClient!", e);
        } finally {
            zkClient.close();
        }
    }
}
