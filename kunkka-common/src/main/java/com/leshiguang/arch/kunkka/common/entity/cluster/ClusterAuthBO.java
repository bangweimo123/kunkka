package com.leshiguang.arch.kunkka.common.entity.cluster;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author bangwei.mo
 * @Date 2020-09-02 21:00
 * @Description
 */
@Data
public class ClusterAuthBO implements Serializable {
    private String field;

    private String operate;

    private List data;
}
