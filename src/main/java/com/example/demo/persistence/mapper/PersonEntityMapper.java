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

    int deleteByPrimaryKey(Long id);

    int insert(PersonEntity record);

    int insertSelective(PersonEntity record);

    List<PersonEntity> selectByExample(PersonEntityExample example);

    PersonEntity selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") PersonEntity record, @Param("example") PersonEntityExample example);

    int updateByExample(@Param("record") PersonEntity record, @Param("example") PersonEntityExample example);

    int updateByPrimaryKeySelective(PersonEntity record);

    int updateByPrimaryKey(PersonEntity record);

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
}