package com.leshiguang.arch.kunkka.common.enums;

/**
 * @Author bangwei.mo
 * @Date 2021-01-13 15:45
 * @Description
 */
public enum RoleMode {
    master(1, "master"),
    slave(2, "slave");

    RoleMode(Integer code, String desc) {
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

    public static RoleMode parse(String desc) {
        for (RoleMode mode : RoleMode.values()) {
            if (mode.desc.equalsIgnoreCase(desc)) {
                return mode;
            }
        }
        return null;
    }

    public static RoleMode parse(int code) {
        for (RoleMode mode : RoleMode.values()) {
            if (mode.code == code) {
                return mode;
            }
        }
        return null;
    }
}
