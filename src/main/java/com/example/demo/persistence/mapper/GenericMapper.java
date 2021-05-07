package com.example.demo.persistence.mapper;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface GenericMapper {
    @Select("<script><select>不需要修改这一行！paramsMapWithSql 中放入sql 语句以及需要的占位符参数</select></script>")
    @Lang(MatchAnyLanguageDriver.class)
    LinkedHashMap<String, Object> queryForMap(Map<String, Object> paramsMapWithSql);

    @Select("<script><select>不需要修改这一行！paramsMapWithSql 中放入sql 语句以及需要的占位符参数</select></script>")
    @Lang(MatchAnyLanguageDriver.class)
    List<LinkedHashMap<String, Object>> queryForList(Map<String, Object> paramsMapWithSql);

    @Select("<script><select>不需要修改这一行！paramsMapWithSql 中放入sql 语句以及需要的占位符参数</select></script>")
    @Lang(MatchAnyLanguageDriver.class)
    Object queryForObject(Map<String, Object> paramsMapWithSql);
}