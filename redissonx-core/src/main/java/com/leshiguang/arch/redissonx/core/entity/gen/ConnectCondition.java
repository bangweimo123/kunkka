/**
 * generate by mybatisgen in http://git.dianpingoa.com/v1/sh/projects/opplatform/repos/mybatisgen/browse
 */
package com.leshiguang.arch.redissonx.core.entity.gen;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ConnectCondition {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer mysqlOffset;

    private Integer mysqlLength;

    public ConnectCondition() {
        oredCriteria = new ArrayList<Criteria>();
    }

    public void setOrderByClause(String orderByClause) {
        this.orderByClause = orderByClause;
    }

    public String getOrderByClause() {
        return orderByClause;
    }

    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    public boolean isDistinct() {
        return distinct;
    }

    public List<Criteria> getOredCriteria() {
        return oredCriteria;
    }

    public void or(Criteria criteria) {
        oredCriteria.add(criteria);
    }

    public Criteria or() {
        Criteria criteria = createCriteriaInternal();
        oredCriteria.add(criteria);
        return criteria;
    }

    public Criteria createCriteria() {
        Criteria criteria = createCriteriaInternal();
        if (oredCriteria.size() == 0) {
            oredCriteria.add(criteria);
        }
        return criteria;
    }

    protected Criteria createCriteriaInternal() {
        Criteria criteria = new Criteria();
        return criteria;
    }

    public void clear() {
        oredCriteria.clear();
        orderByClause = null;
        distinct = false;
    }

    /**
     * @param mysqlOffset 指定返回记录行的偏移量<br> mysqlOffset= 5,mysqlLength=10;  // 检索记录行 6-15
     */
    public void setMysqlOffset(Integer mysqlOffset) {
        this.mysqlOffset = mysqlOffset;
    }

    public Integer getMysqlOffset() {
        return mysqlOffset;
    }

    /**
     * @param mysqlLength 指定返回记录行的最大数目<br> mysqlOffset= 5,mysqlLength=10;  // 检索记录行 6-15
     */
    public void setMysqlLength(Integer mysqlLength) {
        this.mysqlLength = mysqlLength;
    }

    public Integer getMysqlLength() {
        return mysqlLength;
    }

    /**
     * table: connect
     */
    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<Criterion>();
        }

        public boolean isValid() {
            return criteria.size() > 0;
        }

        public List<Criterion> getAllCriteria() {
            return criteria;
        }

        public List<Criterion> getCriteria() {
            return criteria;
        }

        protected void addCriterion(String condition) {
            if (condition == null) {
                throw new RuntimeException("Value for condition cannot be null");
            }
            criteria.add(new Criterion(condition));
        }

        protected void addCriterion(String condition, Object value, String property) {
            if (value == null) {
                throw new RuntimeException("Value for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                throw new RuntimeException("Between values for " + property + " cannot be null");
            }
            criteria.add(new Criterion(condition, value1, value2));
        }

        public Criteria andIdIsNull() {
            addCriterion("id is null");
            return (Criteria) this;
        }

        public Criteria andIdIsNotNull() {
            addCriterion("id is not null");
            return (Criteria) this;
        }

        public Criteria andIdEqualTo(Integer value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Integer value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Integer value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Integer value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Integer value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Integer value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Integer> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Integer> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Integer value1, Integer value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Integer value1, Integer value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andConnectNameIsNull() {
            addCriterion("connect_name is null");
            return (Criteria) this;
        }

        public Criteria andConnectNameIsNotNull() {
            addCriterion("connect_name is not null");
            return (Criteria) this;
        }

        public Criteria andConnectNameEqualTo(String value) {
            addCriterion("connect_name =", value, "connectName");
            return (Criteria) this;
        }

        public Criteria andConnectNameNotEqualTo(String value) {
            addCriterion("connect_name <>", value, "connectName");
            return (Criteria) this;
        }

        public Criteria andConnectNameGreaterThan(String value) {
            addCriterion("connect_name >", value, "connectName");
            return (Criteria) this;
        }

        public Criteria andConnectNameGreaterThanOrEqualTo(String value) {
            addCriterion("connect_name >=", value, "connectName");
            return (Criteria) this;
        }

        public Criteria andConnectNameLessThan(String value) {
            addCriterion("connect_name <", value, "connectName");
            return (Criteria) this;
        }

        public Criteria andConnectNameLessThanOrEqualTo(String value) {
            addCriterion("connect_name <=", value, "connectName");
            return (Criteria) this;
        }

        public Criteria andConnectNameLike(String value) {
            addCriterion("connect_name like", value, "connectName");
            return (Criteria) this;
        }

        public Criteria andConnectNameNotLike(String value) {
            addCriterion("connect_name not like", value, "connectName");
            return (Criteria) this;
        }

        public Criteria andConnectNameIn(List<String> values) {
            addCriterion("connect_name in", values, "connectName");
            return (Criteria) this;
        }

        public Criteria andConnectNameNotIn(List<String> values) {
            addCriterion("connect_name not in", values, "connectName");
            return (Criteria) this;
        }

        public Criteria andConnectNameBetween(String value1, String value2) {
            addCriterion("connect_name between", value1, value2, "connectName");
            return (Criteria) this;
        }

        public Criteria andConnectNameNotBetween(String value1, String value2) {
            addCriterion("connect_name not between", value1, value2, "connectName");
            return (Criteria) this;
        }

        public Criteria andAddressIsNull() {
            addCriterion("address is null");
            return (Criteria) this;
        }

        public Criteria andAddressIsNotNull() {
            addCriterion("address is not null");
            return (Criteria) this;
        }

        public Criteria andAddressEqualTo(String value) {
            addCriterion("address =", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotEqualTo(String value) {
            addCriterion("address <>", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThan(String value) {
            addCriterion("address >", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressGreaterThanOrEqualTo(String value) {
            addCriterion("address >=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThan(String value) {
            addCriterion("address <", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLessThanOrEqualTo(String value) {
            addCriterion("address <=", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressLike(String value) {
            addCriterion("address like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotLike(String value) {
            addCriterion("address not like", value, "address");
            return (Criteria) this;
        }

        public Criteria andAddressIn(List<String> values) {
            addCriterion("address in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotIn(List<String> values) {
            addCriterion("address not in", values, "address");
            return (Criteria) this;
        }

        public Criteria andAddressBetween(String value1, String value2) {
            addCriterion("address between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andAddressNotBetween(String value1, String value2) {
            addCriterion("address not between", value1, value2, "address");
            return (Criteria) this;
        }

        public Criteria andUseHttpsModeIsNull() {
            addCriterion("use_https_mode is null");
            return (Criteria) this;
        }

        public Criteria andUseHttpsModeIsNotNull() {
            addCriterion("use_https_mode is not null");
            return (Criteria) this;
        }

        public Criteria andUseHttpsModeEqualTo(Integer value) {
            addCriterion("use_https_mode =", value, "useHttpsMode");
            return (Criteria) this;
        }

        public Criteria andUseHttpsModeNotEqualTo(Integer value) {
            addCriterion("use_https_mode <>", value, "useHttpsMode");
            return (Criteria) this;
        }

        public Criteria andUseHttpsModeGreaterThan(Integer value) {
            addCriterion("use_https_mode >", value, "useHttpsMode");
            return (Criteria) this;
        }

        public Criteria andUseHttpsModeGreaterThanOrEqualTo(Integer value) {
            addCriterion("use_https_mode >=", value, "useHttpsMode");
            return (Criteria) this;
        }

        public Criteria andUseHttpsModeLessThan(Integer value) {
            addCriterion("use_https_mode <", value, "useHttpsMode");
            return (Criteria) this;
        }

        public Criteria andUseHttpsModeLessThanOrEqualTo(Integer value) {
            addCriterion("use_https_mode <=", value, "useHttpsMode");
            return (Criteria) this;
        }

        public Criteria andUseHttpsModeIn(List<Integer> values) {
            addCriterion("use_https_mode in", values, "useHttpsMode");
            return (Criteria) this;
        }

        public Criteria andUseHttpsModeNotIn(List<Integer> values) {
            addCriterion("use_https_mode not in", values, "useHttpsMode");
            return (Criteria) this;
        }

        public Criteria andUseHttpsModeBetween(Integer value1, Integer value2) {
            addCriterion("use_https_mode between", value1, value2, "useHttpsMode");
            return (Criteria) this;
        }

        public Criteria andUseHttpsModeNotBetween(Integer value1, Integer value2) {
            addCriterion("use_https_mode not between", value1, value2, "useHttpsMode");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNull() {
            addCriterion("create_time is null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIsNotNull() {
            addCriterion("create_time is not null");
            return (Criteria) this;
        }

        public Criteria andCreateTimeEqualTo(Date value) {
            addCriterion("create_time =", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotEqualTo(Date value) {
            addCriterion("create_time <>", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThan(Date value) {
            addCriterion("create_time >", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("create_time >=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThan(Date value) {
            addCriterion("create_time <", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeLessThanOrEqualTo(Date value) {
            addCriterion("create_time <=", value, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeIn(List<Date> values) {
            addCriterion("create_time in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotIn(List<Date> values) {
            addCriterion("create_time not in", values, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeBetween(Date value1, Date value2) {
            addCriterion("create_time between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andCreateTimeNotBetween(Date value1, Date value2) {
            addCriterion("create_time not between", value1, value2, "createTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNull() {
            addCriterion("update_time is null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIsNotNull() {
            addCriterion("update_time is not null");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeEqualTo(Date value) {
            addCriterion("update_time =", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotEqualTo(Date value) {
            addCriterion("update_time <>", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThan(Date value) {
            addCriterion("update_time >", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeGreaterThanOrEqualTo(Date value) {
            addCriterion("update_time >=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThan(Date value) {
            addCriterion("update_time <", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeLessThanOrEqualTo(Date value) {
            addCriterion("update_time <=", value, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeIn(List<Date> values) {
            addCriterion("update_time in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotIn(List<Date> values) {
            addCriterion("update_time not in", values, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeBetween(Date value1, Date value2) {
            addCriterion("update_time between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andUpdateTimeNotBetween(Date value1, Date value2) {
            addCriterion("update_time not between", value1, value2, "updateTime");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNull() {
            addCriterion("creator is null");
            return (Criteria) this;
        }

        public Criteria andCreatorIsNotNull() {
            addCriterion("creator is not null");
            return (Criteria) this;
        }

        public Criteria andCreatorEqualTo(String value) {
            addCriterion("creator =", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotEqualTo(String value) {
            addCriterion("creator <>", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThan(String value) {
            addCriterion("creator >", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorGreaterThanOrEqualTo(String value) {
            addCriterion("creator >=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThan(String value) {
            addCriterion("creator <", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLessThanOrEqualTo(String value) {
            addCriterion("creator <=", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorLike(String value) {
            addCriterion("creator like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotLike(String value) {
            addCriterion("creator not like", value, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorIn(List<String> values) {
            addCriterion("creator in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotIn(List<String> values) {
            addCriterion("creator not in", values, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorBetween(String value1, String value2) {
            addCriterion("creator between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andCreatorNotBetween(String value1, String value2) {
            addCriterion("creator not between", value1, value2, "creator");
            return (Criteria) this;
        }

        public Criteria andOperatorIsNull() {
            addCriterion("operator is null");
            return (Criteria) this;
        }

        public Criteria andOperatorIsNotNull() {
            addCriterion("operator is not null");
            return (Criteria) this;
        }

        public Criteria andOperatorEqualTo(String value) {
            addCriterion("operator =", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotEqualTo(String value) {
            addCriterion("operator <>", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorGreaterThan(String value) {
            addCriterion("operator >", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorGreaterThanOrEqualTo(String value) {
            addCriterion("operator >=", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLessThan(String value) {
            addCriterion("operator <", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLessThanOrEqualTo(String value) {
            addCriterion("operator <=", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorLike(String value) {
            addCriterion("operator like", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotLike(String value) {
            addCriterion("operator not like", value, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorIn(List<String> values) {
            addCriterion("operator in", values, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotIn(List<String> values) {
            addCriterion("operator not in", values, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorBetween(String value1, String value2) {
            addCriterion("operator between", value1, value2, "operator");
            return (Criteria) this;
        }

        public Criteria andOperatorNotBetween(String value1, String value2) {
            addCriterion("operator not between", value1, value2, "operator");
            return (Criteria) this;
        }

        public Criteria andAuthModeIsNull() {
            addCriterion("auth_mode is null");
            return (Criteria) this;
        }

        public Criteria andAuthModeIsNotNull() {
            addCriterion("auth_mode is not null");
            return (Criteria) this;
        }

        public Criteria andAuthModeEqualTo(String value) {
            addCriterion("auth_mode =", value, "authMode");
            return (Criteria) this;
        }

        public Criteria andAuthModeNotEqualTo(String value) {
            addCriterion("auth_mode <>", value, "authMode");
            return (Criteria) this;
        }

        public Criteria andAuthModeGreaterThan(String value) {
            addCriterion("auth_mode >", value, "authMode");
            return (Criteria) this;
        }

        public Criteria andAuthModeGreaterThanOrEqualTo(String value) {
            addCriterion("auth_mode >=", value, "authMode");
            return (Criteria) this;
        }

        public Criteria andAuthModeLessThan(String value) {
            addCriterion("auth_mode <", value, "authMode");
            return (Criteria) this;
        }

        public Criteria andAuthModeLessThanOrEqualTo(String value) {
            addCriterion("auth_mode <=", value, "authMode");
            return (Criteria) this;
        }

        public Criteria andAuthModeLike(String value) {
            addCriterion("auth_mode like", value, "authMode");
            return (Criteria) this;
        }

        public Criteria andAuthModeNotLike(String value) {
            addCriterion("auth_mode not like", value, "authMode");
            return (Criteria) this;
        }

        public Criteria andAuthModeIn(List<String> values) {
            addCriterion("auth_mode in", values, "authMode");
            return (Criteria) this;
        }

        public Criteria andAuthModeNotIn(List<String> values) {
            addCriterion("auth_mode not in", values, "authMode");
            return (Criteria) this;
        }

        public Criteria andAuthModeBetween(String value1, String value2) {
            addCriterion("auth_mode between", value1, value2, "authMode");
            return (Criteria) this;
        }

        public Criteria andAuthModeNotBetween(String value1, String value2) {
            addCriterion("auth_mode not between", value1, value2, "authMode");
            return (Criteria) this;
        }

        public Criteria andAuthInfoIsNull() {
            addCriterion("auth_info is null");
            return (Criteria) this;
        }

        public Criteria andAuthInfoIsNotNull() {
            addCriterion("auth_info is not null");
            return (Criteria) this;
        }

        public Criteria andAuthInfoEqualTo(String value) {
            addCriterion("auth_info =", value, "authInfo");
            return (Criteria) this;
        }

        public Criteria andAuthInfoNotEqualTo(String value) {
            addCriterion("auth_info <>", value, "authInfo");
            return (Criteria) this;
        }

        public Criteria andAuthInfoGreaterThan(String value) {
            addCriterion("auth_info >", value, "authInfo");
            return (Criteria) this;
        }

        public Criteria andAuthInfoGreaterThanOrEqualTo(String value) {
            addCriterion("auth_info >=", value, "authInfo");
            return (Criteria) this;
        }

        public Criteria andAuthInfoLessThan(String value) {
            addCriterion("auth_info <", value, "authInfo");
            return (Criteria) this;
        }

        public Criteria andAuthInfoLessThanOrEqualTo(String value) {
            addCriterion("auth_info <=", value, "authInfo");
            return (Criteria) this;
        }

        public Criteria andAuthInfoLike(String value) {
            addCriterion("auth_info like", value, "authInfo");
            return (Criteria) this;
        }

        public Criteria andAuthInfoNotLike(String value) {
            addCriterion("auth_info not like", value, "authInfo");
            return (Criteria) this;
        }

        public Criteria andAuthInfoIn(List<String> values) {
            addCriterion("auth_info in", values, "authInfo");
            return (Criteria) this;
        }

        public Criteria andAuthInfoNotIn(List<String> values) {
            addCriterion("auth_info not in", values, "authInfo");
            return (Criteria) this;
        }

        public Criteria andAuthInfoBetween(String value1, String value2) {
            addCriterion("auth_info between", value1, value2, "authInfo");
            return (Criteria) this;
        }

        public Criteria andAuthInfoNotBetween(String value1, String value2) {
            addCriterion("auth_info not between", value1, value2, "authInfo");
            return (Criteria) this;
        }

        public Criteria andSourceIsNull() {
            addCriterion("source is null");
            return (Criteria) this;
        }

        public Criteria andSourceIsNotNull() {
            addCriterion("source is not null");
            return (Criteria) this;
        }

        public Criteria andSourceEqualTo(String value) {
            addCriterion("source =", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotEqualTo(String value) {
            addCriterion("source <>", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThan(String value) {
            addCriterion("source >", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceGreaterThanOrEqualTo(String value) {
            addCriterion("source >=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThan(String value) {
            addCriterion("source <", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLessThanOrEqualTo(String value) {
            addCriterion("source <=", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceLike(String value) {
            addCriterion("source like", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotLike(String value) {
            addCriterion("source not like", value, "source");
            return (Criteria) this;
        }

        public Criteria andSourceIn(List<String> values) {
            addCriterion("source in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotIn(List<String> values) {
            addCriterion("source not in", values, "source");
            return (Criteria) this;
        }

        public Criteria andSourceBetween(String value1, String value2) {
            addCriterion("source between", value1, value2, "source");
            return (Criteria) this;
        }

        public Criteria andSourceNotBetween(String value1, String value2) {
            addCriterion("source not between", value1, value2, "source");
            return (Criteria) this;
        }

        public Criteria andConnectNameLikeInsensitive(String value) {
            addCriterion("upper(connect_name) like", value.toUpperCase(), "connectName");
            return (Criteria) this;
        }

        public Criteria andAddressLikeInsensitive(String value) {
            addCriterion("upper(address) like", value.toUpperCase(), "address");
            return (Criteria) this;
        }

        public Criteria andCreatorLikeInsensitive(String value) {
            addCriterion("upper(creator) like", value.toUpperCase(), "creator");
            return (Criteria) this;
        }

        public Criteria andOperatorLikeInsensitive(String value) {
            addCriterion("upper(operator) like", value.toUpperCase(), "operator");
            return (Criteria) this;
        }

        public Criteria andAuthModeLikeInsensitive(String value) {
            addCriterion("upper(auth_mode) like", value.toUpperCase(), "authMode");
            return (Criteria) this;
        }

        public Criteria andAuthInfoLikeInsensitive(String value) {
            addCriterion("upper(auth_info) like", value.toUpperCase(), "authInfo");
            return (Criteria) this;
        }

        public Criteria andSourceLikeInsensitive(String value) {
            addCriterion("upper(source) like", value.toUpperCase(), "source");
            return (Criteria) this;
        }
    }

    /**
     * table: connect
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * table: connect
     */
    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        public String getCondition() {
            return condition;
        }

        public Object getValue() {
            return value;
        }

        public Object getSecondValue() {
            return secondValue;
        }

        public boolean isNoValue() {
            return noValue;
        }

        public boolean isSingleValue() {
            return singleValue;
        }

        public boolean isBetweenValue() {
            return betweenValue;
        }

        public boolean isListValue() {
            return listValue;
        }

        public String getTypeHandler() {
            return typeHandler;
        }

        protected Criterion(String condition) {
            super();
            this.condition = condition;
            this.typeHandler = null;
            this.noValue = true;
        }

        protected Criterion(String condition, Object value, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.typeHandler = typeHandler;
            if (value instanceof List<?>) {
                this.listValue = true;
            } else {
                this.singleValue = true;
            }
        }

        protected Criterion(String condition, Object value) {
            this(condition, value, null);
        }

        protected Criterion(String condition, Object value, Object secondValue, String typeHandler) {
            super();
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.typeHandler = typeHandler;
            this.betweenValue = true;
        }

        protected Criterion(String condition, Object value, Object secondValue) {
            this(condition, value, secondValue, null);
        }
    }
}