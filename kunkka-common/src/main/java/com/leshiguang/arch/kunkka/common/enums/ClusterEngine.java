package com.leshiguang.arch.kunkka.common.enums;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-13 21:10
 * @Description
 */
public enum ClusterEngine {
    jedis(1, "jedis"),
    letture(2, "letture"),
    redisson(3, "redisson");


    ClusterEngine(Integer code, String desc) {
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

    public static ClusterEngine parse(String desc) {
        for (ClusterEngine e : ClusterEngine.values()) {
            if (e.desc.equalsIgnoreCase(desc)) {
                return e;
            }
        }
        return null;
    }

    public static ClusterEngine parse(int code) {
        for (ClusterEngine e : ClusterEngine.values()) {
            if (e.code == code) {
                return e;
            }
        }
        return null;
    }
}
