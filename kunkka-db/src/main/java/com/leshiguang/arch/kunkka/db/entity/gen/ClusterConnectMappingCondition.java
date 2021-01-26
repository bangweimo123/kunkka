/**
 * generate by mybatisgen in http://git.dianpingoa.com/v1/sh/projects/opplatform/repos/mybatisgen/browse
 */
package com.leshiguang.arch.kunkka.db.entity.gen;

import java.util.ArrayList;
import java.util.List;

public class ClusterConnectMappingCondition {
    protected String orderByClause;

    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Integer mysqlOffset;

    private Integer mysqlLength;

    public ClusterConnectMappingCondition() {
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
     * table: cluster_connect_mapping
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

        public Criteria andRegionIsNull() {
            addCriterion("region is null");
            return (Criteria) this;
        }

        public Criteria andRegionIsNotNull() {
            addCriterion("region is not null");
            return (Criteria) this;
        }

        public Criteria andRegionEqualTo(String value) {
            addCriterion("region =", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionNotEqualTo(String value) {
            addCriterion("region <>", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionGreaterThan(String value) {
            addCriterion("region >", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionGreaterThanOrEqualTo(String value) {
            addCriterion("region >=", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionLessThan(String value) {
            addCriterion("region <", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionLessThanOrEqualTo(String value) {
            addCriterion("region <=", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionLike(String value) {
            addCriterion("region like", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionNotLike(String value) {
            addCriterion("region not like", value, "region");
            return (Criteria) this;
        }

        public Criteria andRegionIn(List<String> values) {
            addCriterion("region in", values, "region");
            return (Criteria) this;
        }

        public Criteria andRegionNotIn(List<String> values) {
            addCriterion("region not in", values, "region");
            return (Criteria) this;
        }

        public Criteria andRegionBetween(String value1, String value2) {
            addCriterion("region between", value1, value2, "region");
            return (Criteria) this;
        }

        public Criteria andRegionNotBetween(String value1, String value2) {
            addCriterion("region not between", value1, value2, "region");
            return (Criteria) this;
        }

        public Criteria andMasterNodeIsNull() {
            addCriterion("master_node is null");
            return (Criteria) this;
        }

        public Criteria andMasterNodeIsNotNull() {
            addCriterion("master_node is not null");
            return (Criteria) this;
        }

        public Criteria andMasterNodeEqualTo(String value) {
            addCriterion("master_node =", value, "masterNode");
            return (Criteria) this;
        }

        public Criteria andMasterNodeNotEqualTo(String value) {
            addCriterion("master_node <>", value, "masterNode");
            return (Criteria) this;
        }

        public Criteria andMasterNodeGreaterThan(String value) {
            addCriterion("master_node >", value, "masterNode");
            return (Criteria) this;
        }

        public Criteria andMasterNodeGreaterThanOrEqualTo(String value) {
            addCriterion("master_node >=", value, "masterNode");
            return (Criteria) this;
        }

        public Criteria andMasterNodeLessThan(String value) {
            addCriterion("master_node <", value, "masterNode");
            return (Criteria) this;
        }

        public Criteria andMasterNodeLessThanOrEqualTo(String value) {
            addCriterion("master_node <=", value, "masterNode");
            return (Criteria) this;
        }

        public Criteria andMasterNodeLike(String value) {
            addCriterion("master_node like", value, "masterNode");
            return (Criteria) this;
        }

        public Criteria andMasterNodeNotLike(String value) {
            addCriterion("master_node not like", value, "masterNode");
            return (Criteria) this;
        }

        public Criteria andMasterNodeIn(List<String> values) {
            addCriterion("master_node in", values, "masterNode");
            return (Criteria) this;
        }

        public Criteria andMasterNodeNotIn(List<String> values) {
            addCriterion("master_node not in", values, "masterNode");
            return (Criteria) this;
        }

        public Criteria andMasterNodeBetween(String value1, String value2) {
            addCriterion("master_node between", value1, value2, "masterNode");
            return (Criteria) this;
        }

        public Criteria andMasterNodeNotBetween(String value1, String value2) {
            addCriterion("master_node not between", value1, value2, "masterNode");
            return (Criteria) this;
        }

        public Criteria andSlaveNodesIsNull() {
            addCriterion("slave_nodes is null");
            return (Criteria) this;
        }

        public Criteria andSlaveNodesIsNotNull() {
            addCriterion("slave_nodes is not null");
            return (Criteria) this;
        }

        public Criteria andSlaveNodesEqualTo(String value) {
            addCriterion("slave_nodes =", value, "slaveNodes");
            return (Criteria) this;
        }

        public Criteria andSlaveNodesNotEqualTo(String value) {
            addCriterion("slave_nodes <>", value, "slaveNodes");
            return (Criteria) this;
        }

        public Criteria andSlaveNodesGreaterThan(String value) {
            addCriterion("slave_nodes >", value, "slaveNodes");
            return (Criteria) this;
        }

        public Criteria andSlaveNodesGreaterThanOrEqualTo(String value) {
            addCriterion("slave_nodes >=", value, "slaveNodes");
            return (Criteria) this;
        }

        public Criteria andSlaveNodesLessThan(String value) {
            addCriterion("slave_nodes <", value, "slaveNodes");
            return (Criteria) this;
        }

        public Criteria andSlaveNodesLessThanOrEqualTo(String value) {
            addCriterion("slave_nodes <=", value, "slaveNodes");
            return (Criteria) this;
        }

        public Criteria andSlaveNodesLike(String value) {
            addCriterion("slave_nodes like", value, "slaveNodes");
            return (Criteria) this;
        }

        public Criteria andSlaveNodesNotLike(String value) {
            addCriterion("slave_nodes not like", value, "slaveNodes");
            return (Criteria) this;
        }

        public Criteria andSlaveNodesIn(List<String> values) {
            addCriterion("slave_nodes in", values, "slaveNodes");
            return (Criteria) this;
        }

        public Criteria andSlaveNodesNotIn(List<String> values) {
            addCriterion("slave_nodes not in", values, "slaveNodes");
            return (Criteria) this;
        }

        public Criteria andSlaveNodesBetween(String value1, String value2) {
            addCriterion("slave_nodes between", value1, value2, "slaveNodes");
            return (Criteria) this;
        }

        public Criteria andSlaveNodesNotBetween(String value1, String value2) {
            addCriterion("slave_nodes not between", value1, value2, "slaveNodes");
            return (Criteria) this;
        }

        public Criteria andDbIsNull() {
            addCriterion("db is null");
            return (Criteria) this;
        }

        public Criteria andDbIsNotNull() {
            addCriterion("db is not null");
            return (Criteria) this;
        }

        public Criteria andDbEqualTo(Integer value) {
            addCriterion("db =", value, "db");
            return (Criteria) this;
        }

        public Criteria andDbNotEqualTo(Integer value) {
            addCriterion("db <>", value, "db");
            return (Criteria) this;
        }

        public Criteria andDbGreaterThan(Integer value) {
            addCriterion("db >", value, "db");
            return (Criteria) this;
        }

        public Criteria andDbGreaterThanOrEqualTo(Integer value) {
            addCriterion("db >=", value, "db");
            return (Criteria) this;
        }

        public Criteria andDbLessThan(Integer value) {
            addCriterion("db <", value, "db");
            return (Criteria) this;
        }

        public Criteria andDbLessThanOrEqualTo(Integer value) {
            addCriterion("db <=", value, "db");
            return (Criteria) this;
        }

        public Criteria andDbIn(List<Integer> values) {
            addCriterion("db in", values, "db");
            return (Criteria) this;
        }

        public Criteria andDbNotIn(List<Integer> values) {
            addCriterion("db not in", values, "db");
            return (Criteria) this;
        }

        public Criteria andDbBetween(Integer value1, Integer value2) {
            addCriterion("db between", value1, value2, "db");
            return (Criteria) this;
        }

        public Criteria andDbNotBetween(Integer value1, Integer value2) {
            addCriterion("db not between", value1, value2, "db");
            return (Criteria) this;
        }

        public Criteria andPasswordModeIsNull() {
            addCriterion("password_mode is null");
            return (Criteria) this;
        }

        public Criteria andPasswordModeIsNotNull() {
            addCriterion("password_mode is not null");
            return (Criteria) this;
        }

        public Criteria andPasswordModeEqualTo(Integer value) {
            addCriterion("password_mode =", value, "passwordMode");
            return (Criteria) this;
        }

        public Criteria andPasswordModeNotEqualTo(Integer value) {
            addCriterion("password_mode <>", value, "passwordMode");
            return (Criteria) this;
        }

        public Criteria andPasswordModeGreaterThan(Integer value) {
            addCriterion("password_mode >", value, "passwordMode");
            return (Criteria) this;
        }

        public Criteria andPasswordModeGreaterThanOrEqualTo(Integer value) {
            addCriterion("password_mode >=", value, "passwordMode");
            return (Criteria) this;
        }

        public Criteria andPasswordModeLessThan(Integer value) {
            addCriterion("password_mode <", value, "passwordMode");
            return (Criteria) this;
        }

        public Criteria andPasswordModeLessThanOrEqualTo(Integer value) {
            addCriterion("password_mode <=", value, "passwordMode");
            return (Criteria) this;
        }

        public Criteria andPasswordModeIn(List<Integer> values) {
            addCriterion("password_mode in", values, "passwordMode");
            return (Criteria) this;
        }

        public Criteria andPasswordModeNotIn(List<Integer> values) {
            addCriterion("password_mode not in", values, "passwordMode");
            return (Criteria) this;
        }

        public Criteria andPasswordModeBetween(Integer value1, Integer value2) {
            addCriterion("password_mode between", value1, value2, "passwordMode");
            return (Criteria) this;
        }

        public Criteria andPasswordModeNotBetween(Integer value1, Integer value2) {
            addCriterion("password_mode not between", value1, value2, "passwordMode");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNull() {
            addCriterion("password is null");
            return (Criteria) this;
        }

        public Criteria andPasswordIsNotNull() {
            addCriterion("password is not null");
            return (Criteria) this;
        }

        public Criteria andPasswordEqualTo(String value) {
            addCriterion("password =", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotEqualTo(String value) {
            addCriterion("password <>", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThan(String value) {
            addCriterion("password >", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordGreaterThanOrEqualTo(String value) {
            addCriterion("password >=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThan(String value) {
            addCriterion("password <", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLessThanOrEqualTo(String value) {
            addCriterion("password <=", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordLike(String value) {
            addCriterion("password like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotLike(String value) {
            addCriterion("password not like", value, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordIn(List<String> values) {
            addCriterion("password in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotIn(List<String> values) {
            addCriterion("password not in", values, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordBetween(String value1, String value2) {
            addCriterion("password between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andPasswordNotBetween(String value1, String value2) {
            addCriterion("password not between", value1, value2, "password");
            return (Criteria) this;
        }

        public Criteria andConnectParamsIsNull() {
            addCriterion("connect_params is null");
            return (Criteria) this;
        }

        public Criteria andConnectParamsIsNotNull() {
            addCriterion("connect_params is not null");
            return (Criteria) this;
        }

        public Criteria andConnectParamsEqualTo(String value) {
            addCriterion("connect_params =", value, "connectParams");
            return (Criteria) this;
        }

        public Criteria andConnectParamsNotEqualTo(String value) {
            addCriterion("connect_params <>", value, "connectParams");
            return (Criteria) this;
        }

        public Criteria andConnectParamsGreaterThan(String value) {
            addCriterion("connect_params >", value, "connectParams");
            return (Criteria) this;
        }

        public Criteria andConnectParamsGreaterThanOrEqualTo(String value) {
            addCriterion("connect_params >=", value, "connectParams");
            return (Criteria) this;
        }

        public Criteria andConnectParamsLessThan(String value) {
            addCriterion("connect_params <", value, "connectParams");
            return (Criteria) this;
        }

        public Criteria andConnectParamsLessThanOrEqualTo(String value) {
            addCriterion("connect_params <=", value, "connectParams");
            return (Criteria) this;
        }

        public Criteria andConnectParamsLike(String value) {
            addCriterion("connect_params like", value, "connectParams");
            return (Criteria) this;
        }

        public Criteria andConnectParamsNotLike(String value) {
            addCriterion("connect_params not like", value, "connectParams");
            return (Criteria) this;
        }

        public Criteria andConnectParamsIn(List<String> values) {
            addCriterion("connect_params in", values, "connectParams");
            return (Criteria) this;
        }

        public Criteria andConnectParamsNotIn(List<String> values) {
            addCriterion("connect_params not in", values, "connectParams");
            return (Criteria) this;
        }

        public Criteria andConnectParamsBetween(String value1, String value2) {
            addCriterion("connect_params between", value1, value2, "connectParams");
            return (Criteria) this;
        }

        public Criteria andConnectParamsNotBetween(String value1, String value2) {
            addCriterion("connect_params not between", value1, value2, "connectParams");
            return (Criteria) this;
        }

        public Criteria andClusterNameLikeInsensitive(String value) {
            addCriterion("upper(cluster_name) like", value.toUpperCase(), "clusterName");
            return (Criteria) this;
        }

        public Criteria andRegionLikeInsensitive(String value) {
            addCriterion("upper(region) like", value.toUpperCase(), "region");
            return (Criteria) this;
        }

        public Criteria andMasterNodeLikeInsensitive(String value) {
            addCriterion("upper(master_node) like", value.toUpperCase(), "masterNode");
            return (Criteria) this;
        }

        public Criteria andSlaveNodesLikeInsensitive(String value) {
            addCriterion("upper(slave_nodes) like", value.toUpperCase(), "slaveNodes");
            return (Criteria) this;
        }

        public Criteria andPasswordLikeInsensitive(String value) {
            addCriterion("upper(password) like", value.toUpperCase(), "password");
            return (Criteria) this;
        }

        public Criteria andConnectParamsLikeInsensitive(String value) {
            addCriterion("upper(connect_params) like", value.toUpperCase(), "connectParams");
            return (Criteria) this;
        }
    }

    /**
     * table: cluster_connect_mapping
     */
    public static class Criteria extends GeneratedCriteria {

        protected Criteria() {
            super();
        }
    }

    /**
     * table: cluster_connect_mapping
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