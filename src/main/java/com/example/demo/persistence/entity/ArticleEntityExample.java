package com.example.demo.persistence.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ArticleEntityExample {
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

    public ArticleEntityExample() {
        oredCriteria = new ArrayList<>();
        tableFields = new HashSet<>();
        tableFields.add("id");
        tableFields.add("author");
        tableFields.add("title");
        tableFields.add("tags");
        tableFields.add("content");
        tableFields.add("pj");
        tableFields.add("record_status");
        tableFields.add("remarks");
        tableFields.add("sort_priority");
        tableFields.add("version");
        tableFields.add("date_created");
        tableFields.add("last_modified");
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

        public Criteria andAuthorIsNull() {
            addCriterion("author is null");
            return (Criteria) this;
        }

        public Criteria andAuthorIsNotNull() {
            addCriterion("author is not null");
            return (Criteria) this;
        }

        public Criteria andAuthorEqualTo(String value) {
            addCriterion("author =", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorNotEqualTo(String value) {
            addCriterion("author <>", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorGreaterThan(String value) {
            addCriterion("author >", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorGreaterThanOrEqualTo(String value) {
            addCriterion("author >=", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorLessThan(String value) {
            addCriterion("author <", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorLessThanOrEqualTo(String value) {
            addCriterion("author <=", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorLike(String value) {
            addCriterion("author like", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorNotLike(String value) {
            addCriterion("author not like", value, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorIn(List<String> values) {
            addCriterion("author in", values, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorNotIn(List<String> values) {
            addCriterion("author not in", values, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorBetween(String value1, String value2) {
            addCriterion("author between", value1, value2, "author");
            return (Criteria) this;
        }

        public Criteria andAuthorNotBetween(String value1, String value2) {
            addCriterion("author not between", value1, value2, "author");
            return (Criteria) this;
        }

        public Criteria andTitleIsNull() {
            addCriterion("title is null");
            return (Criteria) this;
        }

        public Criteria andTitleIsNotNull() {
            addCriterion("title is not null");
            return (Criteria) this;
        }

        public Criteria andTitleEqualTo(String value) {
            addCriterion("title =", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotEqualTo(String value) {
            addCriterion("title <>", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThan(String value) {
            addCriterion("title >", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleGreaterThanOrEqualTo(String value) {
            addCriterion("title >=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThan(String value) {
            addCriterion("title <", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLessThanOrEqualTo(String value) {
            addCriterion("title <=", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleLike(String value) {
            addCriterion("title like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotLike(String value) {
            addCriterion("title not like", value, "title");
            return (Criteria) this;
        }

        public Criteria andTitleIn(List<String> values) {
            addCriterion("title in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotIn(List<String> values) {
            addCriterion("title not in", values, "title");
            return (Criteria) this;
        }

        public Criteria andTitleBetween(String value1, String value2) {
            addCriterion("title between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTitleNotBetween(String value1, String value2) {
            addCriterion("title not between", value1, value2, "title");
            return (Criteria) this;
        }

        public Criteria andTagsIsNull() {
            addCriterion("tags is null");
            return (Criteria) this;
        }

        public Criteria andTagsIsNotNull() {
            addCriterion("tags is not null");
            return (Criteria) this;
        }

        public Criteria andTagsEqualTo(String value) {
            addCriterion("tags =", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsNotEqualTo(String value) {
            addCriterion("tags <>", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsGreaterThan(String value) {
            addCriterion("tags >", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsGreaterThanOrEqualTo(String value) {
            addCriterion("tags >=", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsLessThan(String value) {
            addCriterion("tags <", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsLessThanOrEqualTo(String value) {
            addCriterion("tags <=", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsLike(String value) {
            addCriterion("tags like", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsNotLike(String value) {
            addCriterion("tags not like", value, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsIn(List<String> values) {
            addCriterion("tags in", values, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsNotIn(List<String> values) {
            addCriterion("tags not in", values, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsBetween(String value1, String value2) {
            addCriterion("tags between", value1, value2, "tags");
            return (Criteria) this;
        }

        public Criteria andTagsNotBetween(String value1, String value2) {
            addCriterion("tags not between", value1, value2, "tags");
            return (Criteria) this;
        }

        public Criteria andContentIsNull() {
            addCriterion("content is null");
            return (Criteria) this;
        }

        public Criteria andContentIsNotNull() {
            addCriterion("content is not null");
            return (Criteria) this;
        }

        public Criteria andContentEqualTo(String value) {
            addCriterion("content =", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotEqualTo(String value) {
            addCriterion("content <>", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThan(String value) {
            addCriterion("content >", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentGreaterThanOrEqualTo(String value) {
            addCriterion("content >=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThan(String value) {
            addCriterion("content <", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLessThanOrEqualTo(String value) {
            addCriterion("content <=", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentLike(String value) {
            addCriterion("content like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotLike(String value) {
            addCriterion("content not like", value, "content");
            return (Criteria) this;
        }

        public Criteria andContentIn(List<String> values) {
            addCriterion("content in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotIn(List<String> values) {
            addCriterion("content not in", values, "content");
            return (Criteria) this;
        }

        public Criteria andContentBetween(String value1, String value2) {
            addCriterion("content between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andContentNotBetween(String value1, String value2) {
            addCriterion("content not between", value1, value2, "content");
            return (Criteria) this;
        }

        public Criteria andPjIsNull() {
            addCriterion("pj is null");
            return (Criteria) this;
        }

        public Criteria andPjIsNotNull() {
            addCriterion("pj is not null");
            return (Criteria) this;
        }

        public Criteria andPjEqualTo(Object value) {
            addCriterion("pj =", value, "pj");
            return (Criteria) this;
        }

        public Criteria andPjNotEqualTo(Object value) {
            addCriterion("pj <>", value, "pj");
            return (Criteria) this;
        }

        public Criteria andPjGreaterThan(Object value) {
            addCriterion("pj >", value, "pj");
            return (Criteria) this;
        }

        public Criteria andPjGreaterThanOrEqualTo(Object value) {
            addCriterion("pj >=", value, "pj");
            return (Criteria) this;
        }

        public Criteria andPjLessThan(Object value) {
            addCriterion("pj <", value, "pj");
            return (Criteria) this;
        }

        public Criteria andPjLessThanOrEqualTo(Object value) {
            addCriterion("pj <=", value, "pj");
            return (Criteria) this;
        }

        public Criteria andPjIn(List<Object> values) {
            addCriterion("pj in", values, "pj");
            return (Criteria) this;
        }

        public Criteria andPjNotIn(List<Object> values) {
            addCriterion("pj not in", values, "pj");
            return (Criteria) this;
        }

        public Criteria andPjBetween(Object value1, Object value2) {
            addCriterion("pj between", value1, value2, "pj");
            return (Criteria) this;
        }

        public Criteria andPjNotBetween(Object value1, Object value2) {
            addCriterion("pj not between", value1, value2, "pj");
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

        public Criteria andRemarksIsNull() {
            addCriterion("remarks is null");
            return (Criteria) this;
        }

        public Criteria andRemarksIsNotNull() {
            addCriterion("remarks is not null");
            return (Criteria) this;
        }

        public Criteria andRemarksEqualTo(String value) {
            addCriterion("remarks =", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotEqualTo(String value) {
            addCriterion("remarks <>", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksGreaterThan(String value) {
            addCriterion("remarks >", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksGreaterThanOrEqualTo(String value) {
            addCriterion("remarks >=", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLessThan(String value) {
            addCriterion("remarks <", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLessThanOrEqualTo(String value) {
            addCriterion("remarks <=", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksLike(String value) {
            addCriterion("remarks like", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotLike(String value) {
            addCriterion("remarks not like", value, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksIn(List<String> values) {
            addCriterion("remarks in", values, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotIn(List<String> values) {
            addCriterion("remarks not in", values, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksBetween(String value1, String value2) {
            addCriterion("remarks between", value1, value2, "remarks");
            return (Criteria) this;
        }

        public Criteria andRemarksNotBetween(String value1, String value2) {
            addCriterion("remarks not between", value1, value2, "remarks");
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

        public Criteria andAuthorRegexp(String regexp) {
            addCriterion("author regexp", regexp, "author");
            return (Criteria) this;
        }

        public Criteria andTitleRegexp(String regexp) {
            addCriterion("title regexp", regexp, "title");
            return (Criteria) this;
        }

        public Criteria andTagsRegexp(String regexp) {
            addCriterion("tags regexp", regexp, "tags");
            return (Criteria) this;
        }

        public Criteria andContentRegexp(String regexp) {
            addCriterion("content regexp", regexp, "content");
            return (Criteria) this;
        }

        public Criteria andPjRegexp(String regexp) {
            addCriterion("pj regexp", regexp, "pj");
            return (Criteria) this;
        }

        public Criteria andRecordStatusRegexp(String regexp) {
            addCriterion("record_status regexp", regexp, "recordStatus");
            return (Criteria) this;
        }

        public Criteria andRemarksRegexp(String regexp) {
            addCriterion("remarks regexp", regexp, "remarks");
            return (Criteria) this;
        }

        public Criteria andSortPriorityRegexp(String regexp) {
            addCriterion("sort_priority regexp", regexp, "sortPriority");
            return (Criteria) this;
        }

        public Criteria andVersionRegexp(String regexp) {
            addCriterion("version regexp", regexp, "version");
            return (Criteria) this;
        }

        public Criteria andDateCreatedRegexp(String regexp) {
            addCriterion("date_created regexp", regexp, "dateCreated");
            return (Criteria) this;
        }

        public Criteria andLastModifiedRegexp(String regexp) {
            addCriterion("last_modified regexp", regexp, "lastModified");
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