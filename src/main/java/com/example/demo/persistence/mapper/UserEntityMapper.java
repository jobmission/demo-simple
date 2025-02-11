package com.example.demo.persistence.mapper;

import com.example.demo.persistence.entity.UserEntity;
import com.example.demo.persistence.entity.UserEntityExample;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserEntityMapper {
    long countByExample(UserEntityExample example);

    int deleteByExample(UserEntityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UserEntity row);

    int insertSelective(UserEntity row);

    List<UserEntity> selectByExample(UserEntityExample example);

    UserEntity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") UserEntity row, @Param("example") UserEntityExample example);

    int updateByExample(@Param("row") UserEntity row, @Param("example") UserEntityExample example);

    int updateByPrimaryKeySelective(UserEntity row);

    int updateByPrimaryKey(UserEntity row);

    int batchInsert(List<UserEntity> list);

    List<UserEntity> selectColumnsByExample(UserEntityExample example);

    List<Long> selectIdsByExample(UserEntityExample example);

    /**
     * 聚合查询
     */
    List<Map<String, Object>> aggregateQueryByExample(UserEntityExample example);

    /**
     * 聚合统计,sum、count、max、min 等
     */
    Map<String, Object> aggregateStatisticsByExample(UserEntityExample example);

    UserEntity selectUniqueByExample(UserEntityExample example);

    int deleteByIds(@Param("ids") long[] ids);
}