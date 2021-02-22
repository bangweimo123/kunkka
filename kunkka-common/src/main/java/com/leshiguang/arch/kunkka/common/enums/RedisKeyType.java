package com.leshiguang.arch.kunkka.common.enums;

/**
 * @Author bangwei.mo
 * @Date 2020-09-14 15:31
 * @Description
 */
public enum RedisKeyType {
    string(1, "string", "string"),

    list(2, "list", "list"),

    set(3, "set", "set"),

    hash(4, "hash", "hash"),

    zset(5, "zset", "zset"),

    geo(6, "geo", "zset"),

    bitmap(7, "bitmap", "string");

    RedisKeyType(Integer code, String vType, String dataType) {
        this.code = code;
        this.vType = vType;
        this.dataType = dataType;
    }

    private Integer code;

    private String vType;

    private String dataType;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getvType() {
        return vType;
    }

    public void setvType(String vType) {
        this.vType = vType;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public static RedisKeyType parse(String vType) {
        for (RedisKeyType data : RedisKeyType.values()) {
            if (data.vType.equalsIgnoreCase(vType)) {
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
