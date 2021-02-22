package com.leshiguang.arch.kunkka.web.domain.cluster;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author bangwei.mo
 * @Date 2020-11-11 11:01
 * @Description
 */
@Getter
@Setter
public class ClusterPublishOption implements Serializable {
    private Boolean all = Boolean.TRUE;

    private Boolean connect = Boolean.FALSE;

    private Boolean auth = Boolean.FALSE;
}
