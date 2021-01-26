package com.leshiguang.arch.kunkka.common.enums;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-09-02 21:18
 * @Description
 */
public enum StrategySource {

    application(1, "application"),

    tenant(2, "tenant");

    StrategySource(Integer code, String desc) {
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

    public static StrategySource parse(String desc) {
        for (StrategySource data : StrategySource.values()) {
            if (data.desc.equalsIgnoreCase(desc)) {
                return data;
            }
        }
        return null;
    }

    public static StrategySource parse(int code) {
        for (StrategySource data : StrategySource.values()) {
            if (data.code == code) {
                return data;
            }
        }
        return null;
    }
}
