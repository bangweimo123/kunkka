package com.leshiguang.arch.kunkka.web.domain.category;

import com.leshiguang.arch.kunkka.web.domain.base.PagingReq;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

/**
 * @Author bangwei.mo
 * @Date 2020-03-23 19:18
 * @Modify
 */
@Getter
@Setter
public class CategoryQueryReq extends PagingReq implements Serializable {
    private String clusterName;

    private String keyword;

    private String userId;

    private Boolean showHotKey;

    private List<Integer> statusList;
}
