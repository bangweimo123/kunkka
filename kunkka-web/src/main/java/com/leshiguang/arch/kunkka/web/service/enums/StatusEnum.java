package com.leshiguang.arch.kunkka.web.service.enums;

/**
 * @Author bangwei.mo
 * @Date 2021-01-14 17:00
 * @Description
 */
public enum StatusEnum {
    CREATED(1),

    READY(2),

    ONLINE(10),

    DELETED(-1);

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    private int code;

    StatusEnum(int code) {
        this.code = code;
    }

    public static StatusEnum parse(int code) {
        for (StatusEnum e : StatusEnum.values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }
}
