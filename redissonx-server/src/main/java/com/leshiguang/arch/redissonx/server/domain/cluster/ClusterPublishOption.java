package com.leshiguang.arch.redissonx.server.domain.cluster;

import lombok.Data;

import java.io.Serializable;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-11-11 11:01
 * @Description
 */
@Data
public class ClusterPublishOption implements Serializable {
    private Boolean all = Boolean.TRUE;

    private Boolean connect = Boolean.FALSE;

    private Boolean auth = Boolean.FALSE;
}
