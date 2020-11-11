package org.redisson.config;

import com.leshiguang.redissonx.common.entity.cluster.ClusterAuthStrategyBO;

import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-09-11 16:19
 * @Description
 */
public class RedissonxConfig extends Config {
    private String clusterName;

    private String region;
    /**
     * 策略列表
     */
    private List<ClusterAuthStrategyBO> strategyList;
    /**
     * 是否开启权限策略校验,默认为true
     */
    private Boolean authStrict = true;

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public List<ClusterAuthStrategyBO> getStrategyList() {
        return strategyList;
    }

    public void setStrategyList(List<ClusterAuthStrategyBO> strategyList) {
        this.strategyList = strategyList;
    }

    public String getClusterName() {
        return clusterName;
    }

    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    public Boolean getAuthStrict() {
        return authStrict;
    }

    public void setAuthStrict(Boolean authStrict) {
        this.authStrict = authStrict;
    }
}
