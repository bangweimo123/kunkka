package com.leshiguang.arch.kunkka.web.domain.category;

import com.alibaba.fastjson.JSONObject;
import com.leshiguang.arch.kunkka.web.domain.base.KunkkaPaging;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-18 11:42
 * @Description
 */
@Getter
@Setter
public class CategoryKVReq extends CategoryBaseReq {
    private String type;

    private String key;
    /**
     * 分页查找
     */
    private KunkkaPaging paging;
}
