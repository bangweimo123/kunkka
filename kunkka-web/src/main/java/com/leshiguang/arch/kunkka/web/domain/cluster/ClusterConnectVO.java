package com.leshiguang.arch.kunkka.web.domain.cluster;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-09-09 19:59
 * @Description
 */
@Getter
@Setter
public class ClusterConnectVO implements Serializable {
    private String region;

    private String clusterName;

    private String masterNode;

    private List<String> slaveNodes;

    private Integer db;

    private Integer passwordMode;

    private String password;
    /**
     * 连接参数
     */
    private ClusterConnectParamsVO connectParams;
}
