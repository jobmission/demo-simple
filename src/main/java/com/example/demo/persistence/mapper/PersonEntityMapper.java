package com.example.demo.persistence.mapper;

import com.example.demo.persistence.entity.PersonEntity;
import com.example.demo.persistence.entity.PersonEntityExample;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface PersonEntityMapper {
    long countByExample(PersonEntityExample example);

    int deleteByExample(PersonEntityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(PersonEntity row);

    int insertSelective(PersonEntity row);

    List<PersonEntity> selectByExample(PersonEntityExample example);

    PersonEntity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") PersonEntity row, @Param("example") PersonEntityExample example);

    int updateByExample(@Param("row") PersonEntity row, @Param("example") PersonEntityExample example);

    int updateByPrimaryKeySelective(PersonEntity row);

    int updateByPrimaryKey(PersonEntity row);

    int batchInsert(List<PersonEntity> list);

    List<PersonEntity> selectColumnsByExample(PersonEntityExample example);

    List<Long> selectIdsByExample(PersonEntityExample example);

    /**
     * 聚合查询
     */
    List<Map<String, Object>> aggregateQueryByExample(PersonEntityExample example);

    /**
     * 聚合统计,sum、count、max、min 等
     */
    Map<String, Object> aggregateStatisticsByExample(PersonEntityExample example);

    PersonEntity selectUniqueByExample(PersonEntityExample example);

    int logicalDeleteById(@Param("id") long id);

    int logicalDeleteByIds(@Param("ids") long[] ids);

    int deleteByIds(@Param("ids") long[] ids);
}