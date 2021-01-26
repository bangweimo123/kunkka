package com.leshiguang.arch.kunkka.web.operate.log;

import com.leshiguang.arch.kunkka.db.entity.gen.OperateLog;
import com.leshiguang.arch.kunkka.db.mapper.gen.OperateLogMapper;
import org.apache.commons.lang3.StringUtils;

import java.util.Date;

/**
 * @Author bangwei.mo[bangwei.mo@lifesense.com]
 * @Date 2021-01-22 10:42
 * @Description
 */
public class OperateLogBuilder {
    public enum CType {
        CREATE("create"),
        MODIFY("modify"),
        DELETE("delete"),
        RESET("reset"),
        PUBLISH("publish"),
        OFFLINE("offline");

        private String code;

        CType(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    public enum RelationType {
        CONNECT("connect"),
        CLUSTER("cluster"),
        CATEGORY("category");
        private String code;

        RelationType(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }
    }

    private OperateLog operateLog;

    private static OperateLogMapper operateLogMapper = SpringUtils.getBean(OperateLogMapper.class);

    private boolean ofFlag = false;

    private boolean withFlag = false;

    private boolean contentFlag = false;

    public static OperateLogBuilder createOpt() {
        return builder(CType.CREATE);
    }

    public static OperateLogBuilder resetOpt() {
        return builder(CType.RESET);
    }

    public static OperateLogBuilder modifyOpt() {
        return builder(CType.MODIFY);
    }

    public static OperateLogBuilder deleteOpt() {
        return builder(CType.DELETE);
    }

    public static OperateLogBuilder onlineOpt() {
        return builder(CType.PUBLISH);
    }

    public static OperateLogBuilder offlineOpt() {
        return builder(CType.OFFLINE);
    }

    private static OperateLogBuilder builder(CType type) {
        OperateLogBuilder builder = new OperateLogBuilder();
        builder.operateLog = new OperateLog();
        builder.operateLog.setcType(type.getCode());
        return builder;
    }

    public OperateLogBuilder of(RelationType relationType, Object relationKey) {
        operateLog.setRelType(relationType.getCode());
        operateLog.setRelKey(String.valueOf(relationKey));
        ofFlag = true;
        return this;
    }

    public OperateLogBuilder with(String operator) {
        if (StringUtils.isBlank(operator)) {
            operator = "system";
        }
        operateLog.setOperator(operator);
        withFlag = true;
        return this;
    }

    public OperateLogBuilder content(String format, String... params) {
        operateLog.setContent(String.format(format, params));
        contentFlag = true;
        return this;
    }

    public OperateLogBuilder content(String content) {
        operateLog.setContent(content);
        contentFlag = true;
        return this;
    }

    public void log() {
        if (ofFlag && withFlag && contentFlag) {
            operateLog.setOpTime(new Date());
            operateLogMapper.insert(operateLog);
        }
    }


}
