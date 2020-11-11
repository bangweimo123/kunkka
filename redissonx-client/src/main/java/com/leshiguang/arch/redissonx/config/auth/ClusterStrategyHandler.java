package com.leshiguang.arch.redissonx.config.auth;

import com.leshiguang.arch.redissonx.client.StoreKey;
import com.leshiguang.arch.redissonx.config.store.StoreCategoryConfig;
import com.leshiguang.redissonx.common.entity.cluster.ClusterAuthStrategyBO;
import com.leshiguang.redissonx.common.enums.StrategyOperate;
import com.leshiguang.redissonx.common.enums.StrategySource;
import com.leshiguang.redissonx.common.zookeeper.AuthStrategyConfigListenable;
import com.leshiguang.redissonx.common.zookeeper.ZookeeperClient;
import com.leshiguang.redissonx.common.zookeeper.ZookeeperClientFactory;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.apache.commons.collections4.CollectionUtils;
import org.redisson.config.RedissonxConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-09-11 16:24
 * @Description
 */
public class ClusterStrategyHandler implements RuntimeAuthStrategy, InitAuthStrategy {
    private Boolean authStrict = true;

    private List<InitAuthStrategy> initAuthStrategies = new ArrayList<>();

    private List<RuntimeAuthStrategy> runtimeAuthStrategies = new ArrayList<>();

    public ClusterStrategyHandler(RedissonxConfig redissonxConfig) {
        ZookeeperClient zookeeperClient = ZookeeperClientFactory.getInstance(redissonxConfig.getRegion());
        zookeeperClient.addAuthStrategoyConfigListener(redissonxConfig.getClusterName(), new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                List<ClusterAuthStrategyBO> strategylist = (List<ClusterAuthStrategyBO>) o;
                init(strategylist);
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                init(null);
            }
        });
        this.authStrict = redissonxConfig.getAuthStrict();
        this.init(redissonxConfig.getStrategyList());

    }

    public Boolean beAuth() {
        return authStrict;
    }

    private void init(List<ClusterAuthStrategyBO> strategyList) {
        this.authStrict = false;
        this.initAuthStrategies = new ArrayList<>();
        this.runtimeAuthStrategies = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(strategyList)) {
            for (ClusterAuthStrategyBO strategy : strategyList) {
                StrategySource strategySource = StrategySource.parse(strategy.getSource());
                StrategyOperate strategyOperate = StrategyOperate.parse(strategy.getOperator());
                switch (strategySource) {
                    case application:
                        List<String> applicationList = (List<String>) strategy.getData();
                        ApplicationAuthStrategy applicationAuthStrategy = new ApplicationAuthStrategy(applicationList, strategyOperate);
                        initAuthStrategies.add(applicationAuthStrategy);
                        break;
                    case tenant:
                        List<Integer> tenantList = (List<Integer>) strategy.getData();
                        TenantAuthStrategy tenantAuthStrategy = new TenantAuthStrategy(tenantList, strategyOperate);
                        runtimeAuthStrategies.add(tenantAuthStrategy);
                        break;
                }
            }
        }
        this.authStrict = true;
    }

    @Override
    public boolean initAuthorize() {
        if (this.beAuth()) {
            for (InitAuthStrategy authStrategy : initAuthStrategies) {
                if (!authStrategy.initAuthorize()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public boolean authorize(StoreKey storeKey) {
        if (this.beAuth()) {
            for (RuntimeAuthStrategy authStrategy : runtimeAuthStrategies) {
                if (!authStrategy.authorize(storeKey)) {
                    return false;
                }
            }
        }
        return true;
    }
}
