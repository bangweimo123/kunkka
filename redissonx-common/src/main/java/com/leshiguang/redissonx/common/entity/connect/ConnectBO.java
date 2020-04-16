package com.leshiguang.redissonx.common.entity.connect;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-04-15 14:20
 * @Modify
 */
@Data
public class ConnectBO implements Serializable {

    private static final long serialVersionUID = -1090367296516310682L;

    private String connectName;

    private List<String> addressList;

    private Boolean useHttpsMode;

    private String authMode;

    private ConnectSSHBO ssh;

    private ConnectPasswordBO password;

    private String source;
}
