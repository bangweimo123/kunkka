package com.leshiguang.arch.redissonx.config.zookeeper;

import com.leshiguang.arch.redissonx.config.hotkey.HotKeyStrategy;
import com.leshiguang.arch.redissonx.config.hotkey.HotKeyStrategyAnalyzer;
import com.leshiguang.arch.redissonx.config.hotkey.LocalCacheHotKeyStrategy;
import com.leshiguang.arch.redissonx.config.store.StoreCategoryConfig;
import com.leshiguang.arch.redissonx.exception.StoreConfigException;
import com.leshiguang.redissonx.common.entity.category.CategoryBO;
import com.leshiguang.redissonx.common.entity.category.HotKeyStrategyBO;
import com.leshiguang.redissonx.common.zookeeper.ZookeeperClient;
import com.leshiguang.redissonx.common.zookeeper.ZookeeperClientImpl;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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
        if (categoryBO.isHot()) {
            //设置默认值
            HotKeyStrategyAnalyzer.analyze(storeCategoryConfig, categoryBO);
        }
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
