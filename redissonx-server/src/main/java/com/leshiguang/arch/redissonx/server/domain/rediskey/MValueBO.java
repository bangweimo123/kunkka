package com.leshiguang.arch.redissonx.server.domain.rediskey;

import com.alibaba.fastjson.JSON;
import com.leshiguang.redissonx.common.enums.RedisKeyType;
import lombok.Data;

import java.io.Serializable;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-03-25 14:31
 * @Modify
 */
@Data
public class MValueBO implements Serializable {
    /**
     * 数据
     */
    private String data;
    /**
     * 数据类型
     */
    private String dataType;
    /**
     * key的类型
     */
    private String keyType;
    /**
     * 是否为单个值
     */
    private Boolean single = true;

    public <T> T parse() {
        Object result = null;
        if (single) {
            switch (dataType) {
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
                default:
                    result = data;
                    break;
            }
        }
        return (T) result;
    }

    public <T> T parseAll() {
        Object result = null;
        RedisKeyType redisKeyType = RedisKeyType.parse(keyType);
        switch (redisKeyType) {
            case string:
                break;
            case list:
            case set:
            case sortSet:
                result = JSON.parseArray(data, getDataClass());
                break;
            case hash:
                result = JSON.parseObject(data);
        }
        return (T) result;
    }

    private Class getDataClass() {
        switch (dataType) {
            case "string":
                return String.class;
            case "boolean":
                return Boolean.class;
            case "long":
                return Long.class;
            case "int":
                return Integer.class;
            case "double":
                return Double.class;
            case "float":
                return Float.class;
            default:
                return Object.class;
        }
    }
}
