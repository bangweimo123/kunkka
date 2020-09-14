package org.redisson.config;

import com.leshiguang.redissonx.common.entity.cluster.ClusterStrategyBO;

import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-09-11 16:19
 * @Description
 */
public class RedissonxConfig extends Config {
    /**
     * 策略列表
     */
    private List<ClusterStrategyBO> strategyList;
    /**
     * 是否开启权限策略校验,默认为true
     */
    private Boolean authStrategys = true;

    public List<ClusterStrategyBO> getStrategyList() {
        return strategyList;
    }

    public void setStrategyList(List<ClusterStrategyBO> strategyList) {
        this.strategyList = strategyList;
    }

    public Boolean getAuthStrategys() {
        return authStrategys;
    }

    public void setAuthStrategys(Boolean authStrategys) {
        this.authStrategys = authStrategys;
    }
}
