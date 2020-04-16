/**
 * generate by mybatisgen in http://git.dianpingoa.com/v1/sh/projects/opplatform/repos/mybatisgen/browse
 */
package com.leshiguang.arch.redissonx.core.entity.gen;

import java.io.Serializable;
import java.util.Date;

public class Category implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增
     */
    private Integer id;

    /**
     * 集群名
     */
    private String clusterName;

    /**
     * category
     */
    private String category;

    /**
     * 有效时间
     */
    private String duration;

    /**
     * 参数模版
     */
    private String indexTemplate;

    /**
     * 是否为热key
     */
    private Integer ishot;

    /**
     * 版本号
     */
    private String version;

    /**
     * 状态
     */
    private Integer cStatus;

    /**
     * owner
     */
    private String owner;

    /**
     * member
     */
    private String member;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 删除时间
     */
    private Date deleteTime;

    /**
     * 自增
     * @return id 自增
     */
    public Integer getId() {
        return id;
    }

    /**
     * 自增
     * @param id 自增
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
     * category
     * @return category category
     */
    public String getCategory() {
        return category;
    }

    /**
     * category
     * @param category category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * 有效时间
     * @return duration 有效时间
     */
    public String getDuration() {
        return duration;
    }

    /**
     * 有效时间
     * @param duration 有效时间
     */
    public void setDuration(String duration) {
        this.duration = duration;
    }

    /**
     * 参数模版
     * @return index_template 参数模版
     */
    public String getIndexTemplate() {
        return indexTemplate;
    }

    /**
     * 参数模版
     * @param indexTemplate 参数模版
     */
    public void setIndexTemplate(String indexTemplate) {
        this.indexTemplate = indexTemplate;
    }

    /**
     * 是否为热key
     * @return isHot 是否为热key
     */
    public Integer getIshot() {
        return ishot;
    }

    /**
     * 是否为热key
     * @param ishot 是否为热key
     */
    public void setIshot(Integer ishot) {
        this.ishot = ishot;
    }

    /**
     * 版本号
     * @return version 版本号
     */
    public String getVersion() {
        return version;
    }

    /**
     * 版本号
     * @param version 版本号
     */
    public void setVersion(String version) {
        this.version = version;
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
     * 删除时间
     * @return delete_time 删除时间
     */
    public Date getDeleteTime() {
        return deleteTime;
    }

    /**
     * 删除时间
     * @param deleteTime 删除时间
     */
    public void setDeleteTime(Date deleteTime) {
        this.deleteTime = deleteTime;
    }
}