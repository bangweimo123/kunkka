package com.leshiguang.arch.redissonx.server.domain.cluster;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.leshiguang.redissonx.common.entity.cluster.ClusterBO;
import com.leshiguang.redissonx.common.entity.cluster.ClusterInnerSingleBO;
import lombok.Data;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-23 16:30
 * @Modify
 */
@Data
public class ClusterVO extends ClusterBO {
    private JSONObject innerData;

    public void convert() {
        switch (this.getMode()) {
            case "single":
                this.setInner(JSON.toJavaObject(innerData, ClusterInnerSingleBO.class));
                break;
            default:
                this.setInner(JSON.toJavaObject(innerData, ClusterInnerSingleBO.class));
        }
    }
}
