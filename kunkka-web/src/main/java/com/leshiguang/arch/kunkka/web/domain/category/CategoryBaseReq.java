package com.leshiguang.arch.kunkka.web.domain.category;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author bangwei.mo
 * @Date 2021-01-21 11:40
 * @Description
 */
@Getter
@Setter
public class CategoryBaseReq implements Serializable {
    private String clusterName;

    private String region;

    private String category;
}
