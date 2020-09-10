package com.leshiguang.redissonx.common.entity.cluster;

import com.leshiguang.redissonx.common.entity.connect.ConnectBO;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-09-02 20:48
 * @Description
 */
@Data
public class ClusterConnectBO implements Serializable {
    /**
     * 连接
     */
    private ConnectBO connect;
    /**
     * db
     */
    private Integer database;

}
