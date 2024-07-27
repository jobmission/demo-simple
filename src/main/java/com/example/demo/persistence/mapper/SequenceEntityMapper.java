package com.example.demo.persistence.mapper;

import com.example.demo.persistence.entity.SequenceEntity;
import com.example.demo.persistence.entity.SequenceEntityExample;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface SequenceEntityMapper {
    long countByExample(SequenceEntityExample example);

    int deleteByExample(SequenceEntityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(SequenceEntity row);

    int insertSelective(SequenceEntity row);

    List<SequenceEntity> selectByExample(SequenceEntityExample example);

    SequenceEntity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") SequenceEntity row, @Param("example") SequenceEntityExample example);

    int updateByExample(@Param("row") SequenceEntity row, @Param("example") SequenceEntityExample example);

    int updateByPrimaryKeySelective(SequenceEntity row);

    int updateByPrimaryKey(SequenceEntity row);

    int batchInsert(List<SequenceEntity> list);

    List<SequenceEntity> selectColumnsByExample(SequenceEntityExample example);

    List<Long> selectIdsByExample(SequenceEntityExample example);

    /**
     * 聚合查询
     */
    List<Map<String, Object>> aggregateQueryByExample(SequenceEntityExample example);

    /**
     * 聚合统计,sum、count、max、min 等
     */
    Map<String, Object> aggregateStatisticsByExample(SequenceEntityExample example);

    SequenceEntity selectUniqueByExample(SequenceEntityExample example);

    int logicalDeleteById(@Param("id") long id);

    int logicalDeleteByIds(@Param("ids") long[] ids);

    int deleteByIds(@Param("ids") long[] ids);
}