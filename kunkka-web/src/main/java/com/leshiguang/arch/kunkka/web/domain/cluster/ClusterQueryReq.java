package com.leshiguang.arch.kunkka.web.domain.cluster;

import com.leshiguang.arch.kunkka.web.domain.base.PagingReq;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-20 15:27
 * @Modify
 */
@Getter
@Setter
public class ClusterQueryReq extends PagingReq implements Serializable {
    private String keyword;

    private String mode;

    private List<Integer> statusList;

    private List<Integer> tenantList;

    private List<String> applicationList;
}
