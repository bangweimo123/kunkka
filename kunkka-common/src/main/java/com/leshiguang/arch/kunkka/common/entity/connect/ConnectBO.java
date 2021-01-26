package com.leshiguang.arch.kunkka.common.entity.connect;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-04-15 14:20
 * @Modify
 */
@Data
public class ConnectBO implements Serializable {

    private static final long serialVersionUID = -1090367296516310682L;
    /**
     * 连接名
     */
    private String connectName;
    /**
     * 连接地址
     */
    private String hostName;
    /**
     * 端口号
     */
    private Integer port;
}
