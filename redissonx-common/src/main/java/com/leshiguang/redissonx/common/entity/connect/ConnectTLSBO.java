package com.leshiguang.redissonx.common.entity.connect;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-09-02 17:04
 * @Description
 */
@Data
public class ConnectTLSBO implements Serializable {
    private static final long serialVersionUID = 310267508628089243L;

    private String privateKey;

    private String cert;

    private String caCert;
}
