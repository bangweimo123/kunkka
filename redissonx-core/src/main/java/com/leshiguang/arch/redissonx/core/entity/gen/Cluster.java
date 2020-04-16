/**
 * generate by mybatisgen in http://git.dianpingoa.com/v1/sh/projects/opplatform/repos/mybatisgen/browse
 */
package com.leshiguang.arch.redissonx.core.entity.gen;

import java.io.Serializable;
import java.util.Date;

public class Cluster implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    private Integer id;

    /**
     * 集群名
     */
    private String clusterName;

    /**
     * 集群类型
     */
    private String mode;

    /**
     * 连接名
     */
    private String connectName;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * owner
     */
    private String owner;

    /**
     * member
     */
    private String member;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 应用列表
     */
    private String applicationList;

    /**
     * 租户列表
     */
    private String tenantList;

    /**
     * 数据库
     */
    private Integer ds;

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
     * 集群类型
     * @return mode 集群类型
     */
    public String getMode() {
        return mode;
    }

    /**
     * 集群类型
     * @param mode 集群类型
     */
    public void setMode(String mode) {
        this.mode = mode;
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
     * 创建时间
     * @return create_time 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 创建时间
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 更新时间
     * @return update_time 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 更新时间
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    /**
     * owner
     * @return owner owner
     */
    public String getOwner() {
        return owner;
    }

    /**
     * owner
     * @param owner owner
     */
    public void setOwner(String owner) {
        this.owner = owner;
    }

    /**
     * member
     * @return member member
     */
    public String getMember() {
        return member;
    }

    /**
     * member
     * @param member member
     */
    public void setMember(String member) {
        this.member = member;
    }

    /**
     * 状态
     * @return status 状态
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 状态
     * @param status 状态
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 应用列表
     * @return application_list 应用列表
     */
    public String getApplicationList() {
        return applicationList;
    }

    /**
     * 应用列表
     * @param applicationList 应用列表
     */
    public void setApplicationList(String applicationList) {
        this.applicationList = applicationList;
    }

    /**
     * 租户列表
     * @return tenant_list 租户列表
     */
    public String getTenantList() {
        return tenantList;
    }

    /**
     * 租户列表
     * @param tenantList 租户列表
     */
    public void setTenantList(String tenantList) {
        this.tenantList = tenantList;
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
}