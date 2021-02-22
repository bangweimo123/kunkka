package com.leshiguang.arch.kunkka.common.enums;

/**
 * @Author bangwei.mo
 * @Date 2020-09-02 21:12
 * @Description
 */
public enum ClusterMode {
    single(1, "single"),

    cluster(2, "cluster"),

    sentinel(3, "sentinel"),

    replicate(4, "replicate");

    ClusterMode(Integer code, String desc) {
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

    public static ClusterMode parse(String desc) {
        for (ClusterMode clusterMode : ClusterMode.values()) {
            if (clusterMode.desc.equalsIgnoreCase(desc)) {
                return clusterMode;
            }
        }
        return null;
    }

    public static ClusterMode parse(int code) {
        for (ClusterMode clusterMode : ClusterMode.values()) {
            if (clusterMode.code == code) {
                return clusterMode;
            }
        }
        return null;
    }
}
