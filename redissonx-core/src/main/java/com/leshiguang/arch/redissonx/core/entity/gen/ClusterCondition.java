/**
 * generate by mybatisgen in http://git.dianpingoa.com/v1/sh/projects/opplatform/repos/mybatisgen/browse
 */
package com.leshiguang.arch.redissonx.core.entity.gen;

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

        public Criteria andModeIsNull() {
            addCriterion("mode is null");
            return (Criteria) this;
        }

        public Criteria andModeIsNotNull() {
            addCriterion("mode is not null");
            return (Criteria) this;
        }

        public Criteria andModeEqualTo(String value) {
            addCriterion("mode =", value, "mode");
            return (Criteria) this;
        }

        public Criteria andModeNotEqualTo(String value) {
            addCriterion("mode <>", value, "mode");
            return (Criteria) this;
        }

        public Criteria andModeGreaterThan(String value) {
            addCriterion("mode >", value, "mode");
            return (Criteria) this;
        }

        public Criteria andModeGreaterThanOrEqualTo(String value) {
            addCriterion("mode >=", value, "mode");
            return (Criteria) this;
        }

        public Criteria andModeLessThan(String value) {
            addCriterion("mode <", value, "mode");
            return (Criteria) this;
        }

        public Criteria andModeLessThanOrEqualTo(String value) {
            addCriterion("mode <=", value, "mode");
            return (Criteria) this;
        }

        public Criteria andModeLike(String value) {
            addCriterion("mode like", value, "mode");
            return (Criteria) this;
        }

        public Criteria andModeNotLike(String value) {
            addCriterion("mode not like", value, "mode");
            return (Criteria) this;
        }

        public Criteria andModeIn(List<String> values) {
            addCriterion("mode in", values, "mode");
            return (Criteria) this;
        }

        public Criteria andModeNotIn(List<String> values) {
            addCriterion("mode not in", values, "mode");
            return (Criteria) this;
        }

        public Criteria andModeBetween(String value1, String value2) {
            addCriterion("mode between", value1, value2, "mode");
            return (Criteria) this;
        }

        public Criteria andModeNotBetween(String value1, String value2) {
            addCriterion("mode not between", value1, value2, "mode");
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

        public Criteria andOwnerIsNull() {
            addCriterion("owner is null");
            return (Criteria) this;
        }

        public Criteria andOwnerIsNotNull() {
            addCriterion("owner is not null");
            return (Criteria) this;
        }

        public Criteria andOwnerEqualTo(String value) {
            addCriterion("owner =", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerNotEqualTo(String value) {
            addCriterion("owner <>", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerGreaterThan(String value) {
            addCriterion("owner >", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerGreaterThanOrEqualTo(String value) {
            addCriterion("owner >=", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerLessThan(String value) {
            addCriterion("owner <", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerLessThanOrEqualTo(String value) {
            addCriterion("owner <=", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerLike(String value) {
            addCriterion("owner like", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerNotLike(String value) {
            addCriterion("owner not like", value, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerIn(List<String> values) {
            addCriterion("owner in", values, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerNotIn(List<String> values) {
            addCriterion("owner not in", values, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerBetween(String value1, String value2) {
            addCriterion("owner between", value1, value2, "owner");
            return (Criteria) this;
        }

        public Criteria andOwnerNotBetween(String value1, String value2) {
            addCriterion("owner not between", value1, value2, "owner");
            return (Criteria) this;
        }

        public Criteria andMemberIsNull() {
            addCriterion("member is null");
            return (Criteria) this;
        }

        public Criteria andMemberIsNotNull() {
            addCriterion("member is not null");
            return (Criteria) this;
        }

        public Criteria andMemberEqualTo(String value) {
            addCriterion("member =", value, "member");
            return (Criteria) this;
        }

        public Criteria andMemberNotEqualTo(String value) {
            addCriterion("member <>", value, "member");
            return (Criteria) this;
        }

        public Criteria andMemberGreaterThan(String value) {
            addCriterion("member >", value, "member");
            return (Criteria) this;
        }

        public Criteria andMemberGreaterThanOrEqualTo(String value) {
            addCriterion("member >=", value, "member");
            return (Criteria) this;
        }

        public Criteria andMemberLessThan(String value) {
            addCriterion("member <", value, "member");
            return (Criteria) this;
        }

        public Criteria andMemberLessThanOrEqualTo(String value) {
            addCriterion("member <=", value, "member");
            return (Criteria) this;
        }

        public Criteria andMemberLike(String value) {
            addCriterion("member like", value, "member");
            return (Criteria) this;
        }

        public Criteria andMemberNotLike(String value) {
            addCriterion("member not like", value, "member");
            return (Criteria) this;
        }

        public Criteria andMemberIn(List<String> values) {
            addCriterion("member in", values, "member");
            return (Criteria) this;
        }

        public Criteria andMemberNotIn(List<String> values) {
            addCriterion("member not in", values, "member");
            return (Criteria) this;
        }

        public Criteria andMemberBetween(String value1, String value2) {
            addCriterion("member between", value1, value2, "member");
            return (Criteria) this;
        }

        public Criteria andMemberNotBetween(String value1, String value2) {
            addCriterion("member not between", value1, value2, "member");
            return (Criteria) this;
        }

        public Criteria andStatusIsNull() {
            addCriterion("status is null");
            return (Criteria) this;
        }

        public Criteria andStatusIsNotNull() {
            addCriterion("status is not null");
            return (Criteria) this;
        }

        public Criteria andStatusEqualTo(Integer value) {
            addCriterion("status =", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotEqualTo(Integer value) {
            addCriterion("status <>", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThan(Integer value) {
            addCriterion("status >", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("status >=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThan(Integer value) {
            addCriterion("status <", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusLessThanOrEqualTo(Integer value) {
            addCriterion("status <=", value, "status");
            return (Criteria) this;
        }

        public Criteria andStatusIn(List<Integer> values) {
            addCriterion("status in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotIn(List<Integer> values) {
            addCriterion("status not in", values, "status");
            return (Criteria) this;
        }

        public Criteria andStatusBetween(Integer value1, Integer value2) {
            addCriterion("status between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("status not between", value1, value2, "status");
            return (Criteria) this;
        }

        public Criteria andApplicationListIsNull() {
            addCriterion("application_list is null");
            return (Criteria) this;
        }

        public Criteria andApplicationListIsNotNull() {
            addCriterion("application_list is not null");
            return (Criteria) this;
        }

        public Criteria andApplicationListEqualTo(String value) {
            addCriterion("application_list =", value, "applicationList");
            return (Criteria) this;
        }

        public Criteria andApplicationListNotEqualTo(String value) {
            addCriterion("application_list <>", value, "applicationList");
            return (Criteria) this;
        }

        public Criteria andApplicationListGreaterThan(String value) {
            addCriterion("application_list >", value, "applicationList");
            return (Criteria) this;
        }

        public Criteria andApplicationListGreaterThanOrEqualTo(String value) {
            addCriterion("application_list >=", value, "applicationList");
            return (Criteria) this;
        }

        public Criteria andApplicationListLessThan(String value) {
            addCriterion("application_list <", value, "applicationList");
            return (Criteria) this;
        }

        public Criteria andApplicationListLessThanOrEqualTo(String value) {
            addCriterion("application_list <=", value, "applicationList");
            return (Criteria) this;
        }

        public Criteria andApplicationListLike(String value) {
            addCriterion("application_list like", value, "applicationList");
            return (Criteria) this;
        }

        public Criteria andApplicationListNotLike(String value) {
            addCriterion("application_list not like", value, "applicationList");
            return (Criteria) this;
        }

        public Criteria andApplicationListIn(List<String> values) {
            addCriterion("application_list in", values, "applicationList");
            return (Criteria) this;
        }

        public Criteria andApplicationListNotIn(List<String> values) {
            addCriterion("application_list not in", values, "applicationList");
            return (Criteria) this;
        }

        public Criteria andApplicationListBetween(String value1, String value2) {
            addCriterion("application_list between", value1, value2, "applicationList");
            return (Criteria) this;
        }

        public Criteria andApplicationListNotBetween(String value1, String value2) {
            addCriterion("application_list not between", value1, value2, "applicationList");
            return (Criteria) this;
        }

        public Criteria andTenantListIsNull() {
            addCriterion("tenant_list is null");
            return (Criteria) this;
        }

        public Criteria andTenantListIsNotNull() {
            addCriterion("tenant_list is not null");
            return (Criteria) this;
        }

        public Criteria andTenantListEqualTo(String value) {
            addCriterion("tenant_list =", value, "tenantList");
            return (Criteria) this;
        }

        public Criteria andTenantListNotEqualTo(String value) {
            addCriterion("tenant_list <>", value, "tenantList");
            return (Criteria) this;
        }

        public Criteria andTenantListGreaterThan(String value) {
            addCriterion("tenant_list >", value, "tenantList");
            return (Criteria) this;
        }

        public Criteria andTenantListGreaterThanOrEqualTo(String value) {
            addCriterion("tenant_list >=", value, "tenantList");
            return (Criteria) this;
        }

        public Criteria andTenantListLessThan(String value) {
            addCriterion("tenant_list <", value, "tenantList");
            return (Criteria) this;
        }

        public Criteria andTenantListLessThanOrEqualTo(String value) {
            addCriterion("tenant_list <=", value, "tenantList");
            return (Criteria) this;
        }

        public Criteria andTenantListLike(String value) {
            addCriterion("tenant_list like", value, "tenantList");
            return (Criteria) this;
        }

        public Criteria andTenantListNotLike(String value) {
            addCriterion("tenant_list not like", value, "tenantList");
            return (Criteria) this;
        }

        public Criteria andTenantListIn(List<String> values) {
            addCriterion("tenant_list in", values, "tenantList");
            return (Criteria) this;
        }

        public Criteria andTenantListNotIn(List<String> values) {
            addCriterion("tenant_list not in", values, "tenantList");
            return (Criteria) this;
        }

        public Criteria andTenantListBetween(String value1, String value2) {
            addCriterion("tenant_list between", value1, value2, "tenantList");
            return (Criteria) this;
        }

        public Criteria andTenantListNotBetween(String value1, String value2) {
            addCriterion("tenant_list not between", value1, value2, "tenantList");
            return (Criteria) this;
        }

        public Criteria andDsIsNull() {
            addCriterion("ds is null");
            return (Criteria) this;
        }

        public Criteria andDsIsNotNull() {
            addCriterion("ds is not null");
            return (Criteria) this;
        }

        public Criteria andDsEqualTo(Integer value) {
            addCriterion("ds =", value, "ds");
            return (Criteria) this;
        }

        public Criteria andDsNotEqualTo(Integer value) {
            addCriterion("ds <>", value, "ds");
            return (Criteria) this;
        }

        public Criteria andDsGreaterThan(Integer value) {
            addCriterion("ds >", value, "ds");
            return (Criteria) this;
        }

        public Criteria andDsGreaterThanOrEqualTo(Integer value) {
            addCriterion("ds >=", value, "ds");
            return (Criteria) this;
        }

        public Criteria andDsLessThan(Integer value) {
            addCriterion("ds <", value, "ds");
            return (Criteria) this;
        }

        public Criteria andDsLessThanOrEqualTo(Integer value) {
            addCriterion("ds <=", value, "ds");
            return (Criteria) this;
        }

        public Criteria andDsIn(List<Integer> values) {
            addCriterion("ds in", values, "ds");
            return (Criteria) this;
        }

        public Criteria andDsNotIn(List<Integer> values) {
            addCriterion("ds not in", values, "ds");
            return (Criteria) this;
        }

        public Criteria andDsBetween(Integer value1, Integer value2) {
            addCriterion("ds between", value1, value2, "ds");
            return (Criteria) this;
        }

        public Criteria andDsNotBetween(Integer value1, Integer value2) {
            addCriterion("ds not between", value1, value2, "ds");
            return (Criteria) this;
        }

        public Criteria andClusterNameLikeInsensitive(String value) {
            addCriterion("upper(cluster_name) like", value.toUpperCase(), "clusterName");
            return (Criteria) this;
        }

        public Criteria andModeLikeInsensitive(String value) {
            addCriterion("upper(mode) like", value.toUpperCase(), "mode");
            return (Criteria) this;
        }

        public Criteria andConnectNameLikeInsensitive(String value) {
            addCriterion("upper(connect_name) like", value.toUpperCase(), "connectName");
            return (Criteria) this;
        }

        public Criteria andOwnerLikeInsensitive(String value) {
            addCriterion("upper(owner) like", value.toUpperCase(), "owner");
            return (Criteria) this;
        }

        public Criteria andMemberLikeInsensitive(String value) {
            addCriterion("upper(member) like", value.toUpperCase(), "member");
            return (Criteria) this;
        }

        public Criteria andApplicationListLikeInsensitive(String value) {
            addCriterion("upper(application_list) like", value.toUpperCase(), "applicationList");
            return (Criteria) this;
        }

        public Criteria andTenantListLikeInsensitive(String value) {
            addCriterion("upper(tenant_list) like", value.toUpperCase(), "tenantList");
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