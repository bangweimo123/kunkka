package com.leshiguang.arch.kunkka.common.enums;

/**
 * @Author bangwei.mo
 * @Date 2020-09-11 16:38
 * @Description
 */
public enum StrategyOperate {

    in(1, "in"),
    notIn(2, "notIn"),
    equals(3, "equals");

    StrategyOperate(Integer code, String desc) {
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

    public static StrategyOperate parse(String desc) {
        for (StrategyOperate data : StrategyOperate.values()) {
            if (data.desc.equalsIgnoreCase(desc)) {
                return data;
            }
        }
        return null;
    }

    public static StrategyOperate parse(int code) {
        for (StrategyOperate data : StrategyOperate.values()) {
            if (data.code == code) {
                return data;
            }
        }
        return null;
    }
}
