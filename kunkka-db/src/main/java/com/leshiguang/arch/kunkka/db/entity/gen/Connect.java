/**
 * generate by mybatisgen in http://git.dianpingoa.com/v1/sh/projects/opplatform/repos/mybatisgen/browse
 */
package com.leshiguang.arch.kunkka.db.entity.gen;

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
     * 所属区域
     */
    private String region;

    /**
     * 主机地址
     */
    private String hostName;

    /**
     * 端口号
     */
    private Integer port;

    /**
     * 状态
     */
    private Integer cStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

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

    /**
     * 主机地址
     * @return host_name 主机地址
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * 主机地址
     * @param hostName 主机地址
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * 端口号
     * @return port 端口号
     */
    public Integer getPort() {
        return port;
    }

    /**
     * 端口号
     * @param port 端口号
     */
    public void setPort(Integer port) {
        this.port = port;
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
}