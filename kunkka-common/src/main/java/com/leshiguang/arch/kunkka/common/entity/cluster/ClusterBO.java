package com.leshiguang.arch.kunkka.common.entity.cluster;

import com.leshiguang.arch.kunkka.common.entity.connect.ConnectBO;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-20 13:35
 * @Modify
 */
@Getter
@Setter
public class ClusterBO extends ClusterSimpleBO implements Serializable {
    private static final long serialVersionUID = -7865144088316178836L;
    /**
     * 主节点
     */
    private ConnectBO masterNode;
    /**
     * 具体哪个database 不管哪种方式,database都需要是同一个
     */
    private Integer database;
    /**
     * 子节点
     */
    private List<ConnectBO> slaveNodes;
    /**
     * 密码
     */
    private String password;

    /**
     * 策略列表
     */
    private List<ClusterAuthBO> strategys;
    /**
     * 连接参数
     */
    private ClusterConnectParamsBO connectParams = new ClusterConnectParamsBO();
}
