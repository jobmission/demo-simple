package com.example.demo.persistence.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SequenceEntityExample {
    protected boolean distinct;

    protected List<Criteria> oredCriteria;

    private Map<String, String> orderByClause;

    private Set<String> tableFields;

    /**
     * 期望返回部分字段，以逗号分割开
     */
    private String commaSeparatedColumns;

    public SequenceEntityExample() {
        oredCriteria = new ArrayList<>();
        tableFields = new HashSet<>();
        tableFields.add("id");
        tableFields.add("sequence_name");
        tableFields.add("current_value");
        tableFields.add("version");
        tableFields.add("record_status");
        tableFields.add("sort_priority");
        tableFields.add("remark");
        tableFields.add("date_created");
        tableFields.add("last_modified");
    }

    public String getOrderByClause() {
        if (orderByClause != null && orderByClause.size() > 0) {
            StringBuffer sb = new StringBuffer();
            orderByClause.forEach((k, v) -> {
                sb.append(',' + k + ' ' + v);
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
        if (orderBys == null || orderBys.size() == 0) {
            return;
        }
        orderBys.forEach((k, v) -> {
            addOrderBy(k.trim(), v);
        });
    }

    /**
     * @param commaSeparatedColumns 期望返回部分字段，以逗号分割开
     */
    public void setCommaSeparatedColumns(String commaSeparatedColumns) {
        this.commaSeparatedColumns = commaSeparatedColumns;
    }

    public String getCommaSeparatedColumns() {
        return commaSeparatedColumns;
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

        public Criteria andIdEqualTo(Long value) {
            addCriterion("id =", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotEqualTo(Long value) {
            addCriterion("id <>", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThan(Long value) {
            addCriterion("id >", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdGreaterThanOrEqualTo(Long value) {
            addCriterion("id >=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThan(Long value) {
            addCriterion("id <", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdLessThanOrEqualTo(Long value) {
            addCriterion("id <=", value, "id");
            return (Criteria) this;
        }

        public Criteria andIdIn(List<Long> values) {
            addCriterion("id in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotIn(List<Long> values) {
            addCriterion("id not in", values, "id");
            return (Criteria) this;
        }

        public Criteria andIdBetween(Long value1, Long value2) {
            addCriterion("id between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andIdNotBetween(Long value1, Long value2) {
            addCriterion("id not between", value1, value2, "id");
            return (Criteria) this;
        }

        public Criteria andSequenceNameIsNull() {
            addCriterion("sequence_name is null");
            return (Criteria) this;
        }

        public Criteria andSequenceNameIsNotNull() {
            addCriterion("sequence_name is not null");
            return (Criteria) this;
        }

        public Criteria andSequenceNameEqualTo(String value) {
            addCriterion("sequence_name =", value, "sequenceName");
            return (Criteria) this;
        }

        public Criteria andSequenceNameNotEqualTo(String value) {
            addCriterion("sequence_name <>", value, "sequenceName");
            return (Criteria) this;
        }

        public Criteria andSequenceNameGreaterThan(String value) {
            addCriterion("sequence_name >", value, "sequenceName");
            return (Criteria) this;
        }

        public Criteria andSequenceNameGreaterThanOrEqualTo(String value) {
            addCriterion("sequence_name >=", value, "sequenceName");
            return (Criteria) this;
        }

        public Criteria andSequenceNameLessThan(String value) {
            addCriterion("sequence_name <", value, "sequenceName");
            return (Criteria) this;
        }

        public Criteria andSequenceNameLessThanOrEqualTo(String value) {
            addCriterion("sequence_name <=", value, "sequenceName");
            return (Criteria) this;
        }

        public Criteria andSequenceNameLike(String value) {
            addCriterion("sequence_name like", value, "sequenceName");
            return (Criteria) this;
        }

        public Criteria andSequenceNameNotLike(String value) {
            addCriterion("sequence_name not like", value, "sequenceName");
            return (Criteria) this;
        }

        public Criteria andSequenceNameIn(List<String> values) {
            addCriterion("sequence_name in", values, "sequenceName");
            return (Criteria) this;
        }

        public Criteria andSequenceNameNotIn(List<String> values) {
            addCriterion("sequence_name not in", values, "sequenceName");
            return (Criteria) this;
        }

        public Criteria andSequenceNameBetween(String value1, String value2) {
            addCriterion("sequence_name between", value1, value2, "sequenceName");
            return (Criteria) this;
        }

        public Criteria andSequenceNameNotBetween(String value1, String value2) {
            addCriterion("sequence_name not between", value1, value2, "sequenceName");
            return (Criteria) this;
        }

        public Criteria andCurrentValueIsNull() {
            addCriterion("current_value is null");
            return (Criteria) this;
        }

        public Criteria andCurrentValueIsNotNull() {
            addCriterion("current_value is not null");
            return (Criteria) this;
        }

        public Criteria andCurrentValueEqualTo(Long value) {
            addCriterion("current_value =", value, "currentValue");
            return (Criteria) this;
        }

        public Criteria andCurrentValueNotEqualTo(Long value) {
            addCriterion("current_value <>", value, "currentValue");
            return (Criteria) this;
        }

        public Criteria andCurrentValueGreaterThan(Long value) {
            addCriterion("current_value >", value, "currentValue");
            return (Criteria) this;
        }

        public Criteria andCurrentValueGreaterThanOrEqualTo(Long value) {
            addCriterion("current_value >=", value, "currentValue");
            return (Criteria) this;
        }

        public Criteria andCurrentValueLessThan(Long value) {
            addCriterion("current_value <", value, "currentValue");
            return (Criteria) this;
        }

        public Criteria andCurrentValueLessThanOrEqualTo(Long value) {
            addCriterion("current_value <=", value, "currentValue");
            return (Criteria) this;
        }

        public Criteria andCurrentValueIn(List<Long> values) {
            addCriterion("current_value in", values, "currentValue");
            return (Criteria) this;
        }

        public Criteria andCurrentValueNotIn(List<Long> values) {
            addCriterion("current_value not in", values, "currentValue");
            return (Criteria) this;
        }

        public Criteria andCurrentValueBetween(Long value1, Long value2) {
            addCriterion("current_value between", value1, value2, "currentValue");
            return (Criteria) this;
        }

        public Criteria andCurrentValueNotBetween(Long value1, Long value2) {
            addCriterion("current_value not between", value1, value2, "currentValue");
            return (Criteria) this;
        }

        public Criteria andVersionIsNull() {
            addCriterion("version is null");
            return (Criteria) this;
        }

        public Criteria andVersionIsNotNull() {
            addCriterion("version is not null");
            return (Criteria) this;
        }

        public Criteria andVersionEqualTo(Integer value) {
            addCriterion("version =", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotEqualTo(Integer value) {
            addCriterion("version <>", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThan(Integer value) {
            addCriterion("version >", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionGreaterThanOrEqualTo(Integer value) {
            addCriterion("version >=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThan(Integer value) {
            addCriterion("version <", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionLessThanOrEqualTo(Integer value) {
            addCriterion("version <=", value, "version");
            return (Criteria) this;
        }

        public Criteria andVersionIn(List<Integer> values) {
            addCriterion("version in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotIn(List<Integer> values) {
            addCriterion("version not in", values, "version");
            return (Criteria) this;
        }

        public Criteria andVersionBetween(Integer value1, Integer value2) {
            addCriterion("version between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andVersionNotBetween(Integer value1, Integer value2) {
            addCriterion("version not between", value1, value2, "version");
            return (Criteria) this;
        }

        public Criteria andRecordStatusIsNull() {
            addCriterion("record_status is null");
            return (Criteria) this;
        }

        public Criteria andRecordStatusIsNotNull() {
            addCriterion("record_status is not null");
            return (Criteria) this;
        }

        public Criteria andRecordStatusEqualTo(Integer value) {
            addCriterion("record_status =", value, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andRecordStatusNotEqualTo(Integer value) {
            addCriterion("record_status <>", value, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andRecordStatusGreaterThan(Integer value) {
            addCriterion("record_status >", value, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andRecordStatusGreaterThanOrEqualTo(Integer value) {
            addCriterion("record_status >=", value, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andRecordStatusLessThan(Integer value) {
            addCriterion("record_status <", value, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andRecordStatusLessThanOrEqualTo(Integer value) {
            addCriterion("record_status <=", value, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andRecordStatusIn(List<Integer> values) {
            addCriterion("record_status in", values, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andRecordStatusNotIn(List<Integer> values) {
            addCriterion("record_status not in", values, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andRecordStatusBetween(Integer value1, Integer value2) {
            addCriterion("record_status between", value1, value2, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andRecordStatusNotBetween(Integer value1, Integer value2) {
            addCriterion("record_status not between", value1, value2, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andSortPriorityIsNull() {
            addCriterion("sort_priority is null");
            return (Criteria) this;
        }

        public Criteria andSortPriorityIsNotNull() {
            addCriterion("sort_priority is not null");
            return (Criteria) this;
        }

        public Criteria andSortPriorityEqualTo(Integer value) {
            addCriterion("sort_priority =", value, "sortPriority");
            return (Criteria) this;
        }

        public Criteria andSortPriorityNotEqualTo(Integer value) {
            addCriterion("sort_priority <>", value, "sortPriority");
            return (Criteria) this;
        }

        public Criteria andSortPriorityGreaterThan(Integer value) {
            addCriterion("sort_priority >", value, "sortPriority");
            return (Criteria) this;
        }

        public Criteria andSortPriorityGreaterThanOrEqualTo(Integer value) {
            addCriterion("sort_priority >=", value, "sortPriority");
            return (Criteria) this;
        }

        public Criteria andSortPriorityLessThan(Integer value) {
            addCriterion("sort_priority <", value, "sortPriority");
            return (Criteria) this;
        }

        public Criteria andSortPriorityLessThanOrEqualTo(Integer value) {
            addCriterion("sort_priority <=", value, "sortPriority");
            return (Criteria) this;
        }

        public Criteria andSortPriorityIn(List<Integer> values) {
            addCriterion("sort_priority in", values, "sortPriority");
            return (Criteria) this;
        }

        public Criteria andSortPriorityNotIn(List<Integer> values) {
            addCriterion("sort_priority not in", values, "sortPriority");
            return (Criteria) this;
        }

        public Criteria andSortPriorityBetween(Integer value1, Integer value2) {
            addCriterion("sort_priority between", value1, value2, "sortPriority");
            return (Criteria) this;
        }

        public Criteria andSortPriorityNotBetween(Integer value1, Integer value2) {
            addCriterion("sort_priority not between", value1, value2, "sortPriority");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNull() {
            addCriterion("remark is null");
            return (Criteria) this;
        }

        public Criteria andRemarkIsNotNull() {
            addCriterion("remark is not null");
            return (Criteria) this;
        }

        public Criteria andRemarkEqualTo(String value) {
            addCriterion("remark =", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotEqualTo(String value) {
            addCriterion("remark <>", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThan(String value) {
            addCriterion("remark >", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkGreaterThanOrEqualTo(String value) {
            addCriterion("remark >=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThan(String value) {
            addCriterion("remark <", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLessThanOrEqualTo(String value) {
            addCriterion("remark <=", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkLike(String value) {
            addCriterion("remark like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotLike(String value) {
            addCriterion("remark not like", value, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkIn(List<String> values) {
            addCriterion("remark in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotIn(List<String> values) {
            addCriterion("remark not in", values, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkBetween(String value1, String value2) {
            addCriterion("remark between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andRemarkNotBetween(String value1, String value2) {
            addCriterion("remark not between", value1, value2, "remark");
            return (Criteria) this;
        }

        public Criteria andDateCreatedIsNull() {
            addCriterion("date_created is null");
            return (Criteria) this;
        }

        public Criteria andDateCreatedIsNotNull() {
            addCriterion("date_created is not null");
            return (Criteria) this;
        }

        public Criteria andDateCreatedEqualTo(LocalDateTime value) {
            addCriterion("date_created =", value, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andDateCreatedNotEqualTo(LocalDateTime value) {
            addCriterion("date_created <>", value, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andDateCreatedGreaterThan(LocalDateTime value) {
            addCriterion("date_created >", value, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andDateCreatedGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("date_created >=", value, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andDateCreatedLessThan(LocalDateTime value) {
            addCriterion("date_created <", value, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andDateCreatedLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("date_created <=", value, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andDateCreatedIn(List<LocalDateTime> values) {
            addCriterion("date_created in", values, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andDateCreatedNotIn(List<LocalDateTime> values) {
            addCriterion("date_created not in", values, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andDateCreatedBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("date_created between", value1, value2, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andDateCreatedNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("date_created not between", value1, value2, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andLastModifiedIsNull() {
            addCriterion("last_modified is null");
            return (Criteria) this;
        }

        public Criteria andLastModifiedIsNotNull() {
            addCriterion("last_modified is not null");
            return (Criteria) this;
        }

        public Criteria andLastModifiedEqualTo(LocalDateTime value) {
            addCriterion("last_modified =", value, "lastModified");
            return (Criteria) this;
        }

        public Criteria andLastModifiedNotEqualTo(LocalDateTime value) {
            addCriterion("last_modified <>", value, "lastModified");
            return (Criteria) this;
        }

        public Criteria andLastModifiedGreaterThan(LocalDateTime value) {
            addCriterion("last_modified >", value, "lastModified");
            return (Criteria) this;
        }

        public Criteria andLastModifiedGreaterThanOrEqualTo(LocalDateTime value) {
            addCriterion("last_modified >=", value, "lastModified");
            return (Criteria) this;
        }

        public Criteria andLastModifiedLessThan(LocalDateTime value) {
            addCriterion("last_modified <", value, "lastModified");
            return (Criteria) this;
        }

        public Criteria andLastModifiedLessThanOrEqualTo(LocalDateTime value) {
            addCriterion("last_modified <=", value, "lastModified");
            return (Criteria) this;
        }

        public Criteria andLastModifiedIn(List<LocalDateTime> values) {
            addCriterion("last_modified in", values, "lastModified");
            return (Criteria) this;
        }

        public Criteria andLastModifiedNotIn(List<LocalDateTime> values) {
            addCriterion("last_modified not in", values, "lastModified");
            return (Criteria) this;
        }

        public Criteria andLastModifiedBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("last_modified between", value1, value2, "lastModified");
            return (Criteria) this;
        }

        public Criteria andLastModifiedNotBetween(LocalDateTime value1, LocalDateTime value2) {
            addCriterion("last_modified not between", value1, value2, "lastModified");
            return (Criteria) this;
        }

        protected void addCriterion(int additionalCondition, String functionName, Object value, Object secondValue) {
            criteria.add(new Criterion(additionalCondition, functionName, value, secondValue));
        }

        public Criteria andConditionValue(String searchCondition, Object searchValue) {
            addCriterion(3, "conditionValue", searchCondition, searchValue);
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

        Criterion(int additionalCondition, String functionName, Object value, Object secondValue) {
            super();
            this.additionalCondition = additionalCondition;
            this.condition = functionName;
            this.value = value;
            this.secondValue = secondValue;
        }
    }
}