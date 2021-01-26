package com.leshiguang.arch.kunkka.web.domain.cluster;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-15 15:28
 * @Description
 */
@Getter
@Setter
public class ClusterAuthVO implements Serializable {
    private Integer authId;

    private String clusterName;

    private String field;

    private String operate;

    private List data;
}
