/**
 * generate by mybatisgen in http://git.dianpingoa.com/v1/sh/projects/opplatform/repos/mybatisgen/browse
 */
package com.leshiguang.arch.kunkka.db.entity.gen;

import java.io.Serializable;

public class ClusterConnectMapping implements Serializable {
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
     * 区域
     */
    private String region;

    /**
     * 主节点
     */
    private String masterNode;

    /**
     * 子节点
     */
    private String slaveNodes;

    /**
     * 数据库
     */
    private Integer db;

    /**
     * 密码模式
     */
    private Integer passwordMode;

    /**
     * 密码
     */
    private String password;

    /**
     * 连接参数
     */
    private String connectParams;

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
     * 主节点
     * @return master_node 主节点
     */
    public String getMasterNode() {
        return masterNode;
    }

    /**
     * 主节点
     * @param masterNode 主节点
     */
    public void setMasterNode(String masterNode) {
        this.masterNode = masterNode;
    }

    /**
     * 子节点
     * @return slave_nodes 子节点
     */
    public String getSlaveNodes() {
        return slaveNodes;
    }

    /**
     * 子节点
     * @param slaveNodes 子节点
     */
    public void setSlaveNodes(String slaveNodes) {
        this.slaveNodes = slaveNodes;
    }

    /**
     * 数据库
     * @return db 数据库
     */
    public Integer getDb() {
        return db;
    }

    /**
     * 数据库
     * @param db 数据库
     */
    public void setDb(Integer db) {
        this.db = db;
    }

    /**
     * 密码模式
     * @return password_mode 密码模式
     */
    public Integer getPasswordMode() {
        return passwordMode;
    }

    /**
     * 密码模式
     * @param passwordMode 密码模式
     */
    public void setPasswordMode(Integer passwordMode) {
        this.passwordMode = passwordMode;
    }

    /**
     * 密码
     * @return password 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 密码
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 连接参数
     * @return connect_params 连接参数
     */
    public String getConnectParams() {
        return connectParams;
    }

    /**
     * 连接参数
     * @param connectParams 连接参数
     */
    public void setConnectParams(String connectParams) {
        this.connectParams = connectParams;
    }
}