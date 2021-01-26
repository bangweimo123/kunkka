package com.leshiguang.arch.kunkka.client.configure.zookeeper;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-09-09 19:24
 * @Description
 */
@Getter
@Setter
public class ZKConfig implements Serializable {
    private static final long serialVersionUID = 3089545906357447364L;
    private String address;

    private Integer sessionTimeout = 30000;

    private Integer connectTimeout = 2147483647;

    private Long operationRetryTimeout = -1L;
}
