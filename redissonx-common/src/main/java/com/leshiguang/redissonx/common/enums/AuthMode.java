package com.leshiguang.redissonx.common.enums;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2020-09-02 19:18
 * @Description
 */
public enum AuthMode {
    none(0, "none"),
    password(1, "password"),
    ssh(2, "ssh");

    AuthMode(Integer code, String desc) {
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

    public static AuthMode parse(String desc) {
        for (AuthMode authMode : AuthMode.values()) {
            if (authMode.desc.equalsIgnoreCase(desc)) {
                return authMode;
            }
        }
        return AuthMode.none;
    }

    public static AuthMode parse(int code) {
        for (AuthMode authMode : AuthMode.values()) {
            if (authMode.code == code) {
                return authMode;
            }
        }
        return AuthMode.none;
    }
}
