package com.leshiguang.arch.kunkka.common.entity.cluster;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author bangwei.mo
 * @Date 2021-01-22 16:54
 * @Description
 */
@Getter
@Setter
public class ClusterConnectParamsBO implements Serializable {
    /**
     * 最大空闲连接数
     */
    private Integer maxIdle = 10;
    /**
     * 最小空闲连接数
     */
    private Integer minIdle = 0;
    /**
     * 最大连接数
     */
    private Integer maxTotal = 50;
    /**
     * 读超时时间
     */
    private Long readTimeout = 10000l;
    /**
     * 连接超时时间
     */
    private Long connectTimeout = 10000l;
    /**
     * 是否支持scan
     */
    private Boolean supportScan = Boolean.TRUE;
    /**
     * 是否支持设置client
     */
    private Boolean supportClient = Boolean.TRUE;
}
