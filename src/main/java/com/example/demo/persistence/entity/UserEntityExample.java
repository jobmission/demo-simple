package com.example.demo.persistence.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UserEntityExample {
    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private String allowLettersPattern = "[_0-9a-zA-Z]+";

    private Map<String, String> orderByClause;

    private Set<String> tableFields;

    /**
     * 期望返回字段，以逗号分割开
     */
    private String commaSeparatedColumns;

    /**
     * aggregate query clause 语句, 注意未做防注入处理
     */
    private String aggregateByClause;

    public UserEntityExample() {
        oredCriteria = new ArrayList<>();
        tableFields = new HashSet<>();
        tableFields.add("id");
        tableFields.add("username");
    }

    public String getOrderByClause() {
        if (orderByClause != null && !orderByClause.isEmpty()) {
            StringBuffer sb = new StringBuffer();
            orderByClause.forEach((k, v) -> {
                sb.append(',').append(k).append(' ').append(v);
            });
            return sb.toString().replaceFirst(",", "");
        } else {
            return null;
        }
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

    private String underlineName(String name) {
        StringBuilder result = new StringBuilder();
        if (name != null && name.length() > 0) {
            result.append(name, 0, 1);
            for (int i = 1; i < name.length(); i++) {
                String s = name.substring(i, i + 1);
                if (s.equals(s.toUpperCase()) && !Character.isDigit(s.charAt(0))) {
                    result.append("_");
                }
                result.append(s.toLowerCase());
            }
        }
        return result.toString();
    }

    public void addOrderBySpecial(String fieldName, String sortOrder) {
        if (fieldName.matches(allowLettersPattern)) {
            String sortDirection = "desc";
            if (("asc".equalsIgnoreCase(sortOrder))) {
                sortDirection = "asc";
            }
            if (orderByClause != null) {
                orderByClause.put(fieldName, sortDirection);
            } else {
                orderByClause = new LinkedHashMap<>();
                orderByClause.put(fieldName, sortDirection);
            }
        }
    }

    public void addOrderBy(String fieldName, String sortOrder) {
        boolean findFieldName = false;
        if (tableFields.contains(fieldName)) {
            findFieldName = true;
        } else {
            fieldName = underlineName(fieldName);
            if (tableFields.contains(fieldName)) {
                findFieldName = true;
            }
        }
        if (findFieldName) {
            String sortDirection = "desc";
            if (("asc".equalsIgnoreCase(sortOrder))) {
                sortDirection = "asc";
            }
            if (orderByClause != null) {
                orderByClause.put(fieldName, sortDirection);
            } else {
                orderByClause = new LinkedHashMap<>();
                orderByClause.put(fieldName, sortDirection);
            }
        }
    }

    /**
     * 重载addOrderBy
     *
     * @param orderBys 排序子句，不要带 order by
     */
    public void addOrderBy(String orderBys) {
        if (orderBys == null || "".equals(orderBys.trim())) {
            return;
        }
        String[] orders = orderBys.trim().split(",");
        for (String order : orders) {
            String[] fieldOrder = order.trim().split(" ");
            if (fieldOrder.length == 2) {
                addOrderBy(fieldOrder[0], fieldOrder[1]);
            }
        }
    }

    /**
     * 重载addOrderBy
     *
     * @param orderBys 字段名,asc|desc
     */
    public void addOrderBy(Map<String, String> orderBys) {
        if (orderBys == null || orderBys.isEmpty()) {
            return;
        }
        orderBys.forEach((k, v) -> {
            addOrderBy(k.trim(), v);
        });
    }

    /**
     * clearOrderBy
     */
    public void clearOrderBy() {
        if (orderByClause != null) {
            orderByClause.clear();
        }
    }

    /**
     * @param commaSeparatedColumns 期望返回字段，以逗号分割开
     */
    public void setCommaSeparatedColumns(String commaSeparatedColumns) {
        this.commaSeparatedColumns = commaSeparatedColumns;
    }

    public String getCommaSeparatedColumns() {
        return commaSeparatedColumns;
    }

    /**
     * @param aggregateByClause aggregate query 语句, 注意未做防注入处理
     */
    public void setAggregateByClause(String aggregateByClause) {
        this.aggregateByClause = aggregateByClause;
    }

    public String getAggregateByClause() {
        return aggregateByClause;
    }

    protected abstract static class GeneratedCriteria {
        protected List<Criterion> criteria;

        protected GeneratedCriteria() {
            super();
            criteria = new ArrayList<>();
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
                return;
            }
            criteria.add(new Criterion(condition, value));
        }

        protected void addCriterion(String condition, Object value1, Object value2, String property) {
            if (value1 == null || value2 == null) {
                return;
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

        public Criteria andUsernameIsNull() {
            addCriterion("username is null");
            return (Criteria) this;
        }

        public Criteria andUsernameIsNotNull() {
            addCriterion("username is not null");
            return (Criteria) this;
        }

        public Criteria andUsernameEqualTo(String value) {
            addCriterion("username =", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotEqualTo(String value) {
            addCriterion("username <>", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThan(String value) {
            addCriterion("username >", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameGreaterThanOrEqualTo(String value) {
            addCriterion("username >=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThan(String value) {
            addCriterion("username <", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLessThanOrEqualTo(String value) {
            addCriterion("username <=", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameLike(String value) {
            addCriterion("username like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotLike(String value) {
            addCriterion("username not like", value, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameIn(List<String> values) {
            addCriterion("username in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotIn(List<String> values) {
            addCriterion("username not in", values, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameBetween(String value1, String value2) {
            addCriterion("username between", value1, value2, "username");
            return (Criteria) this;
        }

        public Criteria andUsernameNotBetween(String value1, String value2) {
            addCriterion("username not between", value1, value2, "username");
            return (Criteria) this;
        }

        protected void addCriterion(int additionalCondition, String functionName, Object value, Object secondValue) {
            criteria.add(new Criterion(additionalCondition, functionName, value, secondValue));
        }

        protected void addCriterion(int additionalCondition, String functionName, Object value, Object secondValue, Object thirdValue) {
            criteria.add(new Criterion(additionalCondition, functionName, value, secondValue, thirdValue));
        }

        public Criteria andConditionValue(String searchCondition, Object searchValue) {
            addCriterion(searchCondition, searchValue, "");
            return (Criteria) this;
        }

        public Criteria andConditionJsonFieldValue(String jsonColumn, String jsonField, String condition, Object searchValue) {
            addCriterion(4, condition, jsonColumn, jsonField, searchValue);
            return (Criteria) this;
        }

        public Criteria andConditionJsonFieldContains(String jsonColumn, String jsonField, Object searchValue) {
            addCriterion(7, "JSON_CONTAINS", jsonColumn, jsonField, searchValue);
            return (Criteria) this;
        }

        public Criteria andFunctionLeftKey(String functionName, String searchKey, Object searchValue) {
            addCriterion(5, functionName, searchKey, searchValue);
            return (Criteria) this;
        }

        public Criteria andFunctionRightKey(String functionName, String searchKey, Object searchValue) {
            addCriterion(6, functionName, searchKey, searchValue);
            return (Criteria) this;
        }

        public Criteria andIdRegexp(String regexp) {
            addCriterion("id regexp", regexp, "id");
            return (Criteria) this;
        }

        public Criteria andUsernameRegexp(String regexp) {
            addCriterion("username regexp", regexp, "username");
            return (Criteria) this;
        }
    }

    public static class Criteria extends GeneratedCriteria {
        protected Criteria() {
            super();
        }
    }

    public static class Criterion {
        private String condition;

        private Object value;

        private Object secondValue;

        private boolean noValue;

        private boolean singleValue;

        private boolean betweenValue;

        private boolean listValue;

        private String typeHandler;

        private int additionalCondition = 0;

        private Object thirdValue;

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

        public int getAdditionalCondition() {
            return additionalCondition;
        }

        public Object getThirdValue() {
            return thirdValue;
        }

        protected Criterion(int additionalCondition, String condition, Object value, Object secondValue) {
            super();
            this.additionalCondition = additionalCondition;
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
        }

        protected Criterion(int additionalCondition, String condition, Object value, Object secondValue, Object thirdValue) {
            super();
            this.additionalCondition = additionalCondition;
            this.condition = condition;
            this.value = value;
            this.secondValue = secondValue;
            this.thirdValue = thirdValue;
        }
    }
}