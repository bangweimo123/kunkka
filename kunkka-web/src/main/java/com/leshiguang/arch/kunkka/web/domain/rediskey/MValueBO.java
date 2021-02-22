package com.leshiguang.arch.kunkka.web.domain.rediskey;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @Author bangwei.mo
 * @Date 2020-03-25 14:31
 * @Modify
 */
@Getter
@Setter
public class MValueBO implements AbstractValueBO {
    /**
     * 数据
     */
    private String value;
    /**
     * 单个数据的类型
     */
    private String type;

    @Override
    public <T> T parse() {
        Object result = null;
        switch (type) {
            case "string":
                result = value;
                break;
            case "boolean":
                result = Boolean.valueOf(value);
                break;
            case "long":
                result = Long.valueOf(value);
                break;
            case "int":
                result = Integer.valueOf(value);
                break;
            case "double":
                result = Double.valueOf(value);
                break;
            case "float":
                result = Float.valueOf(value);
                break;
            default:
                result = value;
                break;
        }
        return (T) result;
    }
}
