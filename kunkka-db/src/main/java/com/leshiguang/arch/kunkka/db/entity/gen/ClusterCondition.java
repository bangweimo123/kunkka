/**
 * generate by mybatisgen in http://git.dianpingoa.com/v1/sh/projects/opplatform/repos/mybatisgen/browse
 */
package com.leshiguang.arch.kunkka.db.entity.gen;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ClusterCondition {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer mysqlOffset;

    private Integer mysqlLength;

    public ClusterCondition() {
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
     * table: cluster
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

        public Criteria andClusterNameIsNull() {
            addCriterion("cluster_name is null");
            return (Criteria) this;
        }

        public Criteria andClusterNameIsNotNull() {
            addCriterion("cluster_name is not null");
            return (Criteria) this;
        }

        public Criteria andClusterNameEqualTo(String value) {
            addCriterion("cluster_name =", value, "clusterName");
            return (Criteria) this;
        }

        public Criteria andClusterNameNotEqualTo(String value) {
            addCriterion("cluster_name <>", value, "clusterName");
            return (Criteria) this;
        }

        public Criteria andClusterNameGreaterThan(String value) {
            addCriterion("cluster_name >", value, "clusterName");
            return (Criteria) this;
        }

        public Criteria andClusterNameGreaterThanOrEqualTo(String value) {
            addCriterion("cluster_name >=", value, "clusterName");
            return (Criteria) this;
        }

        public Criteria andClusterNameLessThan(String value) {
            addCriterion("cluster_name <", value, "clusterName");
            return (Criteria) this;
        }

        public Criteria andClusterNameLessThanOrEqualTo(String value) {
            addCriterion("cluster_name <=", value, "clusterName");
            return (Criteria) this;
        }

        public Criteria andClusterNameLike(String value) {
            addCriterion("cluster_name like", value, "clusterName");
            return (Criteria) this;
        }

        public Criteria andClusterNameNotLike(String value) {
            addCriterion("cluster_name not like", value, "clusterName");
            return (Criteria) this;
        }

        public Criteria andClusterNameIn(List<String> values) {
            addCriterion("cluster_name in", values, "clusterName");
            return (Criteria) this;
        }

        public Criteria andClusterNameNotIn(List<String> values) {
            addCriterion("cluster_name not in", values, "clusterName");
            return (Criteria) this;
        }

        public Criteria andClusterNameBetween(String value1, String value2) {
            addCriterion("cluster_name between", value1, value2, "clusterName");
            return (Criteria) this;
        }

        public Criteria andClusterNameNotBetween(String value1, String value2) {
            addCriterion("cluster_name not between", value1, value2, "clusterName");
            return (Criteria) this;
        }

        public Criteria andClusterModeIsNull() {
            addCriterion("cluster_mode is null");
            return (Criteria) this;
        }

        public Criteria andClusterModeIsNotNull() {
            addCriterion("cluster_mode is not null");
            return (Criteria) this;
        }

        public Criteria andClusterModeEqualTo(String value) {
            addCriterion("cluster_mode =", value, "clusterMode");
            return (Criteria) this;
        }

        public Criteria andClusterModeNotEqualTo(String value) {
            addCriterion("cluster_mode <>", value, "clusterMode");
            return (Criteria) this;
        }

        public Criteria andClusterModeGreaterThan(String value) {
            addCriterion("cluster_mode >", value, "clusterMode");
            return (Criteria) this;
        }

        public Criteria andClusterModeGreaterThanOrEqualTo(String value) {
            addCriterion("cluster_mode >=", value, "clusterMode");
            return (Criteria) this;
        }

        public Criteria andClusterModeLessThan(String value) {
            addCriterion("cluster_mode <", value, "clusterMode");
            return (Criteria) this;
        }

        public Criteria andClusterModeLessThanOrEqualTo(String value) {
            addCriterion("cluster_mode <=", value, "clusterMode");
            return (Criteria) this;
        }

        public Criteria andClusterModeLike(String value) {
            addCriterion("cluster_mode like", value, "clusterMode");
            return (Criteria) this;
        }

        public Criteria andClusterModeNotLike(String value) {
            addCriterion("cluster_mode not like", value, "clusterMode");
            return (Criteria) this;
        }

        public Criteria andClusterModeIn(List<String> values) {
            addCriterion("cluster_mode in", values, "clusterMode");
            return (Criteria) this;
        }

        public Criteria andClusterModeNotIn(List<String> values) {
            addCriterion("cluster_mode not in", values, "clusterMode");
            return (Criteria) this;
        }

        public Criteria andClusterModeBetween(String value1, String value2) {
            addCriterion("cluster_mode between", value1, value2, "clusterMode");
            return (Criteria) this;
        }

        public Criteria andClusterModeNotBetween(String value1, String value2) {
            addCriterion("cluster_mode not between", value1, value2, "clusterMode");
            return (Criteria) this;
        }

        public Criteria andClusterEngineIsNull() {
            addCriterion("cluster_engine is null");
            return (Criteria) this;
        }

        public Criteria andClusterEngineIsNotNull() {
            addCriterion("cluster_engine is not null");
            return (Criteria) this;
        }

        public Criteria andClusterEngineEqualTo(String value) {
            addCriterion("cluster_engine =", value, "clusterEngine");
            return (Criteria) this;
        }

        public Criteria andClusterEngineNotEqualTo(String value) {
            addCriterion("cluster_engine <>", value, "clusterEngine");
            return (Criteria) this;
        }

        public Criteria andClusterEngineGreaterThan(String value) {
            addCriterion("cluster_engine >", value, "clusterEngine");
            return (Criteria) this;
        }

        public Criteria andClusterEngineGreaterThanOrEqualTo(String value) {
            addCriterion("cluster_engine >=", value, "clusterEngine");
            return (Criteria) this;
        }

        public Criteria andClusterEngineLessThan(String value) {
            addCriterion("cluster_engine <", value, "clusterEngine");
            return (Criteria) this;
        }

        public Criteria andClusterEngineLessThanOrEqualTo(String value) {
            addCriterion("cluster_engine <=", value, "clusterEngine");
            return (Criteria) this;
        }

        public Criteria andClusterEngineLike(String value) {
            addCriterion("cluster_engine like", value, "clusterEngine");
            return (Criteria) this;
        }

        public Criteria andClusterEngineNotLike(String value) {
            addCriterion("cluster_engine not like", value, "clusterEngine");
            return (Criteria) this;
        }

        public Criteria andClusterEngineIn(List<String> values) {
            addCriterion("cluster_engine in", values, "clusterEngine");
            return (Criteria) this;
        }

        public Criteria andClusterEngineNotIn(List<String> values) {
            addCriterion("cluster_engine not in", values, "clusterEngine");
            return (Criteria) this;
        }

        public Criteria andClusterEngineBetween(String value1, String value2) {
            addCriterion("cluster_engine between", value1, value2, "clusterEngine");
            return (Criteria) this;
        }

        public Criteria andClusterEngineNotBetween(String value1, String value2) {
            addCriterion("cluster_engine not between", value1, value2, "clusterEngine");
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

        public Criteria andCStatusIsNull() {
            addCriterion("c_status is null");
            return (Criteria) this;
        }

        public Criteria andCStatusIsNotNull() {
            addCriterion("c_status is not null");
            return (Criteria) this;
        }

        public Criteria andCStatusEqualTo(Integer value) {
            addCriterion("c_status =", value, "cStatus");
            return (Criteria) this;
        }

        public Criteria andCStatusNotEqualTo(Integer value) {
            addCriterion("c_status <>", value, "cStatus");
            return (Criteria) this;
        }

        public Criteria andCStatusGreaterThan(Integer value) {
            addCriterion("c_status >", value, "cStatus");
            return (Criteria) this;
        }

        public Criteria andCStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("c_status >=", value, "cStatus");
            return (Criteria) this;
        }

        public Criteria andCStatusLessThan(Integer value) {
            addCriterion("c_status <", value, "cStatus");
            return (Criteria) this;
        }

        public Criteria andCStatusLessThanOrEqualTo(Integer value) {
            addCriterion("c_status <=", value, "cStatus");
            return (Criteria) this;
        }

        public Criteria andCStatusIn(List<Integer> values) {
            addCriterion("c_status in", values, "cStatus");
            return (Criteria) this;
        }

        public Criteria andCStatusNotIn(List<Integer> values) {
            addCriterion("c_status not in", values, "cStatus");
            return (Criteria) this;
        }

        public Criteria andCStatusBetween(Integer value1, Integer value2) {
            addCriterion("c_status between", value1, value2, "cStatus");
            return (Criteria) this;
        }

        public Criteria andCStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("c_status not between", value1, value2, "cStatus");
            return (Criteria) this;
        }

        public Criteria andOwnerListIsNull() {
            addCriterion("owner_list is null");
            return (Criteria) this;
        }

        public Criteria andOwnerListIsNotNull() {
            addCriterion("owner_list is not null");
            return (Criteria) this;
        }

        public Criteria andOwnerListEqualTo(String value) {
            addCriterion("owner_list =", value, "ownerList");
            return (Criteria) this;
        }

        public Criteria andOwnerListNotEqualTo(String value) {
            addCriterion("owner_list <>", value, "ownerList");
            return (Criteria) this;
        }

        public Criteria andOwnerListGreaterThan(String value) {
            addCriterion("owner_list >", value, "ownerList");
            return (Criteria) this;
        }

        public Criteria andOwnerListGreaterThanOrEqualTo(String value) {
            addCriterion("owner_list >=", value, "ownerList");
            return (Criteria) this;
        }

        public Criteria andOwnerListLessThan(String value) {
            addCriterion("owner_list <", value, "ownerList");
            return (Criteria) this;
        }

        public Criteria andOwnerListLessThanOrEqualTo(String value) {
            addCriterion("owner_list <=", value, "ownerList");
            return (Criteria) this;
        }

        public Criteria andOwnerListLike(String value) {
            addCriterion("owner_list like", value, "ownerList");
            return (Criteria) this;
        }

        public Criteria andOwnerListNotLike(String value) {
            addCriterion("owner_list not like", value, "ownerList");
            return (Criteria) this;
        }

        public Criteria andOwnerListIn(List<String> values) {
            addCriterion("owner_list in", values, "ownerList");
            return (Criteria) this;
        }

        public Criteria andOwnerListNotIn(List<String> values) {
            addCriterion("owner_list not in", values, "ownerList");
            return (Criteria) this;
        }

        public Criteria andOwnerListBetween(String value1, String value2) {
            addCriterion("owner_list between", value1, value2, "ownerList");
            return (Criteria) this;
        }

        public Criteria andOwnerListNotBetween(String value1, String value2) {
            addCriterion("owner_list not between", value1, value2, "ownerList");
            return (Criteria) this;
        }

        public Criteria andMemberListIsNull() {
            addCriterion("member_list is null");
            return (Criteria) this;
        }

        public Criteria andMemberListIsNotNull() {
            addCriterion("member_list is not null");
            return (Criteria) this;
        }

        public Criteria andMemberListEqualTo(String value) {
            addCriterion("member_list =", value, "memberList");
            return (Criteria) this;
        }

        public Criteria andMemberListNotEqualTo(String value) {
            addCriterion("member_list <>", value, "memberList");
            return (Criteria) this;
        }

        public Criteria andMemberListGreaterThan(String value) {
            addCriterion("member_list >", value, "memberList");
            return (Criteria) this;
        }

        public Criteria andMemberListGreaterThanOrEqualTo(String value) {
            addCriterion("member_list >=", value, "memberList");
            return (Criteria) this;
        }

        public Criteria andMemberListLessThan(String value) {
            addCriterion("member_list <", value, "memberList");
            return (Criteria) this;
        }

        public Criteria andMemberListLessThanOrEqualTo(String value) {
            addCriterion("member_list <=", value, "memberList");
            return (Criteria) this;
        }

        public Criteria andMemberListLike(String value) {
            addCriterion("member_list like", value, "memberList");
            return (Criteria) this;
        }

        public Criteria andMemberListNotLike(String value) {
            addCriterion("member_list not like", value, "memberList");
            return (Criteria) this;
        }

        public Criteria andMemberListIn(List<String> values) {
            addCriterion("member_list in", values, "memberList");
            return (Criteria) this;
        }

        public Criteria andMemberListNotIn(List<String> values) {
            addCriterion("member_list not in", values, "memberList");
            return (Criteria) this;
        }

        public Criteria andMemberListBetween(String value1, String value2) {
            addCriterion("member_list between", value1, value2, "memberList");
            return (Criteria) this;
        }

        public Criteria andMemberListNotBetween(String value1, String value2) {
            addCriterion("member_list not between", value1, value2, "memberList");
            return (Criteria) this;
        }

        public Criteria andClusterNameLikeInsensitive(String value) {
            addCriterion("upper(cluster_name) like", value.toUpperCase(), "clusterName");
            return (Criteria) this;
        }

        public Criteria andClusterModeLikeInsensitive(String value) {
            addCriterion("upper(cluster_mode) like", value.toUpperCase(), "clusterMode");
            return (Criteria) this;
        }

        public Criteria andClusterEngineLikeInsensitive(String value) {
            addCriterion("upper(cluster_engine) like", value.toUpperCase(), "clusterEngine");
            return (Criteria) this;
        }

        public Criteria andOwnerListLikeInsensitive(String value) {
            addCriterion("upper(owner_list) like", value.toUpperCase(), "ownerList");
            return (Criteria) this;
        }

        public Criteria andMemberListLikeInsensitive(String value) {
            addCriterion("upper(member_list) like", value.toUpperCase(), "memberList");
            return (Criteria) this;
        }
    }

    /**
     * table: cluster
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * table: cluster
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