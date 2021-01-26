/**
 * generate by mybatisgen in http://git.dianpingoa.com/v1/sh/projects/opplatform/repos/mybatisgen/browse
 */
package com.leshiguang.arch.kunkka.db.entity.gen;

import java.io.Serializable;

public class ClusterAuthMapping implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 唯一id
     */
    private Integer id;

    /**
     * 集群名
     */
    private String clusterName;

    /**
     * 使用的类型
     */
    private String field;

    /**
     * 操作
     */
    private String operate;

    /**
     * 数据
     */
    private String data;

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
     * 使用的类型
     * @return field 使用的类型
     */
    public String getField() {
        return field;
    }

    /**
     * 使用的类型
     * @param field 使用的类型
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * 操作
     * @return operate 操作
     */
    public String getOperate() {
        return operate;
    }

    /**
     * 操作
     * @param operate 操作
     */
    public void setOperate(String operate) {
        this.operate = operate;
    }

    /**
     * 数据
     * @return data 数据
     */
    public String getData() {
        return data;
    }

    /**
     * 数据
     * @param data 数据
     */
    public void setData(String data) {
        this.data = data;
    }
}