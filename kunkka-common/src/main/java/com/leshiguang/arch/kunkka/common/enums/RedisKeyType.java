package com.leshiguang.arch.kunkka.common.enums;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-09-14 15:31
 * @Description
 */
public enum RedisKeyType {
    string(1, "string"),

    list(2, "list"),

    set(3, "set"),

    hash(4, "hash"),

    zset(5, "zset");

    RedisKeyType(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    private Integer code;

    private String desc;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public static RedisKeyType parse(String desc) {
        for (RedisKeyType data : RedisKeyType.values()) {
            if (data.desc.equalsIgnoreCase(desc)) {
                return data;
            }
        }
        return null;
    }

    public static RedisKeyType parse(int code) {
        for (RedisKeyType data : RedisKeyType.values()) {
            if (data.code == code) {
                return data;
            }
        }
        return null;
    }
}
