/**
 * generate by mybatisgen in http://git.dianpingoa.com/v1/sh/projects/opplatform/repos/mybatisgen/browse
 */
package com.leshiguang.arch.redissonx.core.entity.gen;

import java.io.Serializable;
import java.util.Date;

public class Connect implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    private Integer id;

    /**
     * 连接名
     */
    private String connectName;

    /**
     * 地址
     */
    private String address;

    /**
     * 是否使用https
     */
    private Integer useHttpsMode;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 权限类型
     */
    private String authMode;

    /**
     * 权限信息
     */
    private String authInfo;

    /**
     * 所属区域
     */
    private String region;

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
     * 地址
     * @return address 地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 地址
     * @param address 地址
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * 是否使用https
     * @return use_https_mode 是否使用https
     */
    public Integer getUseHttpsMode() {
        return useHttpsMode;
    }

    /**
     * 是否使用https
     * @param useHttpsMode 是否使用https
     */
    public void setUseHttpsMode(Integer useHttpsMode) {
        this.useHttpsMode = useHttpsMode;
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
     * 创建人
     * @return creator 创建人
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 创建人
     * @param creator 创建人
     */
    public void setCreator(String creator) {
        this.creator = creator;
    }

    /**
     * 操作人
     * @return operator 操作人
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 操作人
     * @param operator 操作人
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * 权限类型
     * @return auth_mode 权限类型
     */
    public String getAuthMode() {
        return authMode;
    }

    /**
     * 权限类型
     * @param authMode 权限类型
     */
    public void setAuthMode(String authMode) {
        this.authMode = authMode;
    }

    /**
     * 权限信息
     * @return auth_info 权限信息
     */
    public String getAuthInfo() {
        return authInfo;
    }

    /**
     * 权限信息
     * @param authInfo 权限信息
     */
    public void setAuthInfo(String authInfo) {
        this.authInfo = authInfo;
    }

    /**
     * 所属区域
     * @return region 所属区域
     */
    public String getRegion() {
        return region;
    }

    /**
     * 所属区域
     * @param region 所属区域
     */
    public void setRegion(String region) {
        this.region = region;
    }
}