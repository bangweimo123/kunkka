/**
 * generate by mybatisgen in http://git.dianpingoa.com/v1/sh/projects/opplatform/repos/mybatisgen/browse
 */
package com.leshiguang.arch.kunkka.db.entity.gen;

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
     * 集群模式
     */
    private String clusterMode;

    /**
     * 集群引擎
     */
    private String clusterEngine;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 状态
     */
    private Integer cStatus;

    /**
     * 所属人
     */
    private String ownerList;

    /**
     * 成员
     */
    private String memberList;

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
     * 集群模式
     * @return cluster_mode 集群模式
     */
    public String getClusterMode() {
        return clusterMode;
    }

    /**
     * 集群模式
     * @param clusterMode 集群模式
     */
    public void setClusterMode(String clusterMode) {
        this.clusterMode = clusterMode;
    }

    /**
     * 集群引擎
     * @return cluster_engine 集群引擎
     */
    public String getClusterEngine() {
        return clusterEngine;
    }

    /**
     * 集群引擎
     * @param clusterEngine 集群引擎
     */
    public void setClusterEngine(String clusterEngine) {
        this.clusterEngine = clusterEngine;
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
     * 状态
     * @return c_status 状态
     */
    public Integer getcStatus() {
        return cStatus;
    }

    /**
     * 状态
     * @param cStatus 状态
     */
    public void setcStatus(Integer cStatus) {
        this.cStatus = cStatus;
    }

    /**
     * 所属人
     * @return owner_list 所属人
     */
    public String getOwnerList() {
        return ownerList;
    }

    /**
     * 所属人
     * @param ownerList 所属人
     */
    public void setOwnerList(String ownerList) {
        this.ownerList = ownerList;
    }

    /**
     * 成员
     * @return member_list 成员
     */
    public String getMemberList() {
        return memberList;
    }

    /**
     * 成员
     * @param memberList 成员
     */
    public void setMemberList(String memberList) {
        this.memberList = memberList;
    }
}