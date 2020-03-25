package com.leshiguang.arch.redissonx.config.zookeeper;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.ConfigChangeListener;
import com.ctrip.framework.apollo.ConfigService;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.leshiguang.arch.redissonx.config.store.HotKeyConfig;
import com.leshiguang.arch.redissonx.exception.StoreConfigException;
import com.leshiguang.redissonx.common.constants.RedissonxConstants;
import com.leshiguang.arch.redissonx.config.store.StoreCategoryConfig;
import com.leshiguang.redissonx.common.entity.category.CategoryBO;
import com.leshiguang.redissonx.common.path.PathProvider;
import com.leshiguang.redissonx.common.zookeeper.ZookeeperClient;
import com.leshiguang.redissonx.common.zookeeper.ZookeeperClientImpl;
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
    private ZookeeperClient zookeeperClient = new ZookeeperClientImpl();

    @Override
    public Boolean isStrictAuth(String clusterName) {
        return zookeeperClient.isStrictAuth(clusterName);
    }

    @Override
    public List<String> loadAuthApps(String clusterName) {
        return null;
    }

    @Override
    public Map<String, HotKeyConfig> loadHotKeyConfigs(String clusterName) {
        return null;
    }

    @Override
    public StoreCategoryConfig getStoreCategoryConfig(String clusterName, String category) {
        CategoryBO categoryBO = zookeeperClient.getCategory(clusterName, category);
        if (null == categoryBO) {
            throw new StoreConfigException("category not exist");
        }
        StoreCategoryConfig storeCategoryConfig = new StoreCategoryConfig();
        storeCategoryConfig.setCategory(categoryBO.getCategory());
        storeCategoryConfig.setIndexTemplate(categoryBO.getIndexTemplate());
        storeCategoryConfig.setHot(categoryBO.isHot());
        storeCategoryConfig.setDuration(categoryBO.getDuration());
        return storeCategoryConfig;
    }

    @Override
    public void addCategoryConfigListener(String clusterName, String category, IZkDataListener listener) {
        zookeeperClient.addCategoryConfigListener(clusterName, category, listener);
    }

    @Override
    public void addHotKeyConfigListener(String clusterName, IZkDataListener listener) {
        zookeeperClient.addHotKeyConfigListener(clusterName, listener);
    }

    @Override
    public void addAuthAppsListner(String clusterName, IZkChildListener listener) {
        zookeeperClient.addAuthAppsListner(clusterName, listener);
    }
}
