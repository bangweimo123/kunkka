package com.leshiguang.arch.redissonx.config.auth;

import com.leshiguang.arch.redissonx.client.StoreKey;
import com.leshiguang.redissonx.common.entity.cluster.ClusterStrategyBO;
import com.leshiguang.redissonx.common.enums.StrategyOperate;
import com.leshiguang.redissonx.common.enums.StrategySource;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-09-11 16:24
 * @Description
 */
public class ClusterStrategyHandler {
    private Boolean authStrategys = true;

    private List<AuthStrategy> strategys;

    public ClusterStrategyHandler(List<ClusterStrategyBO> strategyList, Boolean authStrategys) {
        this.authStrategys = authStrategys;
        this.init(strategyList);
    }

    public Boolean beAuth() {
        return authStrategys;
    }

    private void init(List<ClusterStrategyBO> strategyList) {
        strategys = new ArrayList<>();
        for (ClusterStrategyBO strategy : strategyList) {
            StrategySource strategySource = StrategySource.parse(strategy.getSource());
            StrategyOperate strategyOperate = StrategyOperate.parse(strategy.getOperator());
            switch (strategySource) {
                case application:
                    List<String> applicationList = (List<String>) strategy.getData();
                    ApplicationAuthStrategy applicationAuthStrategy = new ApplicationAuthStrategy(applicationList, strategyOperate);
                    strategys.add(applicationAuthStrategy);
                    break;
                case tenant:
                    List<Integer> tenantList = (List<Integer>) strategy.getData();
                    TenantAuthStrategy tenantAuthStrategy = new TenantAuthStrategy(tenantList, strategyOperate);
                    strategys.add(tenantAuthStrategy);
            }

        }
    }

    public boolean auth(StoreKey storeKey) {
        for (AuthStrategy authStrategy : strategys) {
            if (!authStrategy.auth(storeKey)) {
                return false;
            }
        }
        return true;
    }
}
