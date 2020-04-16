package com.leshiguang.redissonx.common.entity.connect;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-04-15 14:23
 * @Modify
 */
@Data
public class ConnectSSHBO implements Serializable {
    private static final long serialVersionUID = -6876258222951081450L;
    private String address;

    private String user;

    private String password;

    private Integer port;

    private Boolean useSSL;

    private String privateKey;

    private String cert;

    private String caCert;
}
