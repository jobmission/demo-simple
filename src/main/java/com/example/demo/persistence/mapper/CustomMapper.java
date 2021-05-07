package com.example.demo.persistence.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface CustomMapper {
    List<Map<String, Object>> selectAll();
}
