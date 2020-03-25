package com.leshiguang.redissonx.common.entity.cluster;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-20 13:54
 * @Modify SSH 方式连接
 */
@Data
public class ClusterSSHBO implements Serializable {
    private String userName;

    private String address;

    private Integer port;

    private String password;

    private String privateKey;
}
