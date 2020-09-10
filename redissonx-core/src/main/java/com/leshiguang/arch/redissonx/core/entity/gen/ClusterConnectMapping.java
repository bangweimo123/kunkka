/**
 * generate by mybatisgen in http://git.dianpingoa.com/v1/sh/projects/opplatform/repos/mybatisgen/browse
 */
package com.leshiguang.arch.redissonx.core.entity.gen;

import java.io.Serializable;

public class ClusterConnectMapping implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 唯一id
     */
    private Integer id;

    /**
     * 连接名
     */
    private String connectName;

    /**
     * 数据库
     */
    private Integer ds;

    /**
     * 集群名
     */
    private String clusterName;

    /**
     * 区域
     */
    private String region;

    /**
     * 唯一id
     * @return id 唯一id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 唯一id
     * @param id 唯一id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 连接名
     * @return connect_name 连接名
     */
    public String getConnectName() {
        return connectName;
    }

    /**
     * 连接名
     * @param connectName 连接名
     */
    public void setConnectName(String connectName) {
        this.connectName = connectName;
    }

    /**
     * 数据库
     * @return ds 数据库
     */
    public Integer getDs() {
        return ds;
    }

    /**
     * 数据库
     * @param ds 数据库
     */
    public void setDs(Integer ds) {
        this.ds = ds;
    }

    /**
     * 集群名
     * @return cluster_name 集群名
     */
    public String getClusterName() {
        return clusterName;
    }

    /**
     * 集群名
     * @param clusterName 集群名
     */
    public void setClusterName(String clusterName) {
        this.clusterName = clusterName;
    }

    /**
     * 区域
     * @return region 区域
     */
    public String getRegion() {
        return region;
    }

    /**
     * 区域
     * @param region 区域
     */
    public void setRegion(String region) {
        this.region = region;
    }
}