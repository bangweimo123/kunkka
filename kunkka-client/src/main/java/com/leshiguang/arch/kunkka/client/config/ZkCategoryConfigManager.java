package com.leshiguang.arch.kunkka.client.config;

import com.leshiguang.arch.kunkka.client.configure.IConfigCallback;
import com.leshiguang.arch.kunkka.client.configure.IConfigureClient;
import com.leshiguang.arch.kunkka.client.configure.zookeeper.ConfigureClientFactory;
import com.leshiguang.arch.kunkka.common.entity.category.CategoryBO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-13 18:09
 * @Modify
 */
public class ZkCategoryConfigManager implements CategoryConfigManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZkCategoryConfigManager.class);
    private String clusterName;
    private IConfigureClient configureClient;
    private Map<String, CategoryConfig> localStoreCategoryConfigMap = new ConcurrentHashMap<String, CategoryConfig>();

    public ZkCategoryConfigManager(String clusterName, String region, ConfigureClientFactory configureClientFactory) {
        this.clusterName = clusterName;
        this.configureClient = configureClientFactory.getInstance(region);
    }

    @Override
    public CategoryConfig getConfig(String category) {
        CategoryConfig _exist = localStoreCategoryConfigMap.get(category);
        if (null == _exist) {
            synchronized (this) {
                _exist = localStoreCategoryConfigMap.get(category);
                if (null == _exist) {
                    _exist = getCategoryConfig(category);
                    if (null != _exist) {
                        localStoreCategoryConfigMap.put(category, _exist);
                    } else {
                        LOGGER.error("can't find category config for configserver!");
                    }
                }
            }
        }
        return _exist;
    }


    private CategoryConfig getCategoryConfig(final String category) {
        CategoryBO categoryBO = configureClient.getCategory(clusterName, category);
        configureClient.categoryWatch(clusterName, category, new IConfigCallback<CategoryBO>() {
            @Override
            public void changed(CategoryBO newData) {
                CategoryConfig categoryConfig = buildCategoryConfig(categoryBO);
                localStoreCategoryConfigMap.put(newData.getCategory(), categoryConfig);
            }

            @Override
            public void deleted() {
                localStoreCategoryConfigMap.remove(category);
            }
        });
        return buildCategoryConfig(categoryBO);
    }

    CategoryConfig buildCategoryConfig(CategoryBO categoryBO) {
        CategoryConfig categoryConfig = new CategoryConfig();
        categoryConfig.setcType(categoryBO.getCType());
        categoryConfig.setCategory(categoryBO.getCategory());
        categoryConfig.setDuration(categoryBO.getDuration());
        categoryConfig.setHot(categoryBO.isHot());
        categoryConfig.setIndexTemplate(categoryBO.getIndexTemplate());
        categoryConfig.setDurationChangeabled(categoryBO.isDurationChangeEnabled());
        categoryConfig.setMultiTenant(categoryBO.isMultiTenant());
        return categoryConfig;
    }

}
