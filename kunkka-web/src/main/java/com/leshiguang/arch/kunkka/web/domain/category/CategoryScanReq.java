package com.leshiguang.arch.kunkka.web.domain.category;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author bangwei.mo
 * @Date 2021-01-15 17:00
 * @Description
 */
@Getter
@Setter
public class CategoryScanReq implements Serializable {
    private String clusterName;

    private String category;

    private String region;

    private String paramFormat;

    private Integer tenantId;

    private Long pageSize;
    /**
     * 是否使用scan操作
     */
    private Boolean scanEabled=Boolean.TRUE;
}
