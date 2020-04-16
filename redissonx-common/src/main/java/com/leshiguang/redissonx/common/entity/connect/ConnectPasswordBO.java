package com.leshiguang.redissonx.common.entity.connect;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-04-15 14:25
 * @Modify
 */
@Data
public class ConnectPasswordBO implements Serializable {
    private static final long serialVersionUID = 8992844992043263135L;

    private String password;
}
