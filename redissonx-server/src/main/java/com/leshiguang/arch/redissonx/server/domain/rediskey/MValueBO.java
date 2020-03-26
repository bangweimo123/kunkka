package com.leshiguang.arch.redissonx.server.domain.rediskey;

import com.alibaba.fastjson.JSON;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-25 14:31
 * @Modify
 */
@Data
public class MValueBO implements Serializable {
    private String data;

    private String type;

    public <T> T parse() {
        Object result = null;
        switch (type) {
            case "string":
                result = data;
                break;
            case "boolean":
                result = Boolean.valueOf(data);
                break;
            case "long":
                result = Long.valueOf(data);
                break;
            case "int":
                result = Integer.valueOf(data);
                break;
            case "double":
                result = Double.valueOf(data);
                break;
            case "float":
                result = Float.valueOf(data);
                break;
            case "list":
                List listData = JSON.parseArray(data);
                result = listData;
                break;
            default:
                result = data;
                break;
        }
        return (T) result;
    }
}
