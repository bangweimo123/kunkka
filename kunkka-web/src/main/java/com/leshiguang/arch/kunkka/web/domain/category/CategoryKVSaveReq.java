package com.leshiguang.arch.kunkka.web.domain.category;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

/**
 * @Author bangwei.mo
 * @Date 2021-01-19 20:57
 * @Description
 */
@Getter
@Setter
public class CategoryKVSaveReq extends CategoryBaseReq {
    private Integer tenantId;

    private Object[] params;

    private String type;

    private JSONObject data;
}
