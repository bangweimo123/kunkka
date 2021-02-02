package com.leshiguang.arch.kunkka.web.exception;

import com.leshiguang.arch.kunkka.common.exception.ErrorCode;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-14 14:51
 * @Description
 */
public enum ServerErrorCode implements ErrorCode {
    PARAM_CHECK_ERROR(499, "参数错误:参数名:[%s],参数值:[%s]"),
    SYSTEM_ERROR(500, "系统错误"),
    CATEGORY_HAS_EXIST(501, "category已存在相同命名"),
    CATEGORY_NOT_EXISTS(502, "category不存在"),
    CATEGORY_PUBLISH_ERROR(503, "category发布失败"),
    CATEGORY_OFFLINE_ERROR(504, "category下线失败"),
    KUNKKA_CLIENT_NOT_FOUND_ERROR(806, "无法找到客户端"),
    REDIS_KEY_TYPE_NOT_SUPPORT(1004, "redis key的类型不存在"),
    REDIS_KEY_VALUE_GET_ERROR(1005, "redis key获取value失败"),
    CLUSTER_NOT_EXISTS(600, "集群配置不存在"),
    CLUSTER_ONLINE_CAN_NOT_EDIT_ERROR(606, "集群已经发布，禁止修改"),
    CLUSTER_HAS_OFFLINE_ERROR(601, "集群已下线"),
    CLUSTER_MAST_BE_READY(604, "集群未就绪"),
    CLUSTER_ONLINE_STATUS_CAN_EDIT(612, "线上修改只允许上线的集群"),
    CLUSTER_PUBLISH_ERROR(605, "集群发布失败,集群:[%s],区域:[%s]"),
    CLUSTER_PUBLISH_ERROR_HAS_NO_CONFIG_REGION_CONNECT(610, "集群发布失败，区域:[%s]下不存在连接配置!"),
    CLUSTER_OFFLINE_ERROR_HAS_ONLINE_CATEGORY(606, "集群下线失败,存在在线category:[%s],请先下线对应category!"),
    UN_SUPPORT_CONVERT_ERROR(607, "不支持的转换类型"),
    CONNECT_PING_ERROR(901, "连接测试失败!IP:[%S],Port:[%s]"),
    NOT_EXIST_KEY_FOR_SCAN(905, "不存在对应的key");

    ServerErrorCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private int code;

    private String msg;

    @Override
    public int getCode() {
        return this.code;
    }

    @Override
    public String getMsg() {
        return this.msg;
    }
}
