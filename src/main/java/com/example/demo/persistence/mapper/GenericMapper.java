package com.example.demo.persistence.mapper;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Lang;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface GenericMapper {
    @Select("<match_any>不需要修改这一行！paramsMapWithSql 中放入sql 语句以及需要的占位符参数</match_any>")
    @Lang(MatchAnyLangDriver.class)
    Map<String, Object> queryForMap(Map<String, Object> paramsMapWithSql);

    @Select("<match_any>不需要修改这一行！paramsMapWithSql 中放入sql 语句以及需要的占位符参数</match_any>")
    @Lang(MatchAnyLangDriver.class)
    List<Map<String, Object>> queryForList(Map<String, Object> paramsMapWithSql);

    @Select("<match_any>不需要修改这一行！paramsMapWithSql 中放入sql 语句以及需要的占位符参数</match_any>")
    @Lang(MatchAnyLangDriver.class)
    Object queryForObject(Map<String, Object> paramsMapWithSql);

    @Update("<match_any>不需要修改这一行！paramsMapWithSql 中放入sql 语句以及需要的占位符参数</match_any>")
    @Lang(MatchAnyLangDriver.class)
    int update(Map<String, Object> paramsMapWithSql);
}