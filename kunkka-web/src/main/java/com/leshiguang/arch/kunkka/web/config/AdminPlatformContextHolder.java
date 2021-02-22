package com.leshiguang.arch.kunkka.web.config;

/**
 * @Author bangwei.mo
 * @Date 2021-01-14 11:40
 * @Description
 */
public class AdminPlatformContextHolder {
    private static ThreadLocal<AdminPlatfromContext> HOLDER = new ThreadLocal<>();

    public static void set(AdminPlatfromContext context) {
        HOLDER.set(context);
    }

    public static AdminPlatfromContext get() {
        return HOLDER.get();
    }
}
