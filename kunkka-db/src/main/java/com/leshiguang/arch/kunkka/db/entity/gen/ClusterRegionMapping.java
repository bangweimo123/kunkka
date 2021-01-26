/**
 * generate by mybatisgen in http://git.dianpingoa.com/v1/sh/projects/opplatform/repos/mybatisgen/browse
 */
package com.leshiguang.arch.kunkka.db.entity.gen;

import java.io.Serializable;

public class ClusterRegionMapping implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    private Integer id;

    /**
     * 区域
     */
    private String region;

    /**
     * 集群名
     */
    private String clusterName;

    /**
     * 自增id
     * @return id 自增id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 自增id
     * @param id 自增id
     */
    public void setId(Integer id) {
        this.id = id;
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
}