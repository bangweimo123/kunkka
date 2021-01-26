/**
 * generate by mybatisgen in http://git.dianpingoa.com/v1/sh/projects/opplatform/repos/mybatisgen/browse
 */
package com.leshiguang.arch.kunkka.db.entity.gen;

import java.io.Serializable;
import java.util.Date;

public class OperateLog implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 自增id
     */
    private Integer id;

    /**
     * 操作人
     */
    private String operator;

    /**
     * 操作类型
     */
    private String cType;

    /**
     * 内容
     */
    private String content;

    /**
     * 操作时间
     */
    private Date opTime;

    /**
     * 关联类型
     */
    private String relType;

    /**
     * 关联key
     */
    private String relKey;

    /**
     * 自增id
     * @return id 自增id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 自增id
     * @param id 自增id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 操作人
     * @return operator 操作人
     */
    public String getOperator() {
        return operator;
    }

    /**
     * 操作人
     * @param operator 操作人
     */
    public void setOperator(String operator) {
        this.operator = operator;
    }

    /**
     * 操作类型
     * @return c_type 操作类型
     */
    public String getcType() {
        return cType;
    }

    /**
     * 操作类型
     * @param cType 操作类型
     */
    public void setcType(String cType) {
        this.cType = cType;
    }

    /**
     * 内容
     * @return content 内容
     */
    public String getContent() {
        return content;
    }

    /**
     * 内容
     * @param content 内容
     */
    public void setContent(String content) {
        this.content = content;
    }

    /**
     * 操作时间
     * @return op_time 操作时间
     */
    public Date getOpTime() {
        return opTime;
    }

    /**
     * 操作时间
     * @param opTime 操作时间
     */
    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }

    /**
     * 关联类型
     * @return rel_type 关联类型
     */
    public String getRelType() {
        return relType;
    }

    /**
     * 关联类型
     * @param relType 关联类型
     */
    public void setRelType(String relType) {
        this.relType = relType;
    }

    /**
     * 关联key
     * @return rel_key 关联key
     */
    public String getRelKey() {
        return relKey;
    }

    /**
     * 关联key
     * @param relKey 关联key
     */
    public void setRelKey(String relKey) {
        this.relKey = relKey;
    }
}