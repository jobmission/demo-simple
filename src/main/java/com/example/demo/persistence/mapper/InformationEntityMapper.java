package com.example.demo.persistence.mapper;

import com.example.demo.persistence.entity.InformationEntity;
import com.example.demo.persistence.entity.InformationEntityExample;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface InformationEntityMapper {
    long countByExample(InformationEntityExample example);

    int deleteByExample(InformationEntityExample example);

    int deleteByPrimaryKey(Long id);

    int insert(InformationEntity record);

    int insertSelective(InformationEntity record);

    List<InformationEntity> selectByExample(InformationEntityExample example);

    InformationEntity selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") InformationEntity record, @Param("example") InformationEntityExample example);

    int updateByExample(@Param("record") InformationEntity record, @Param("example") InformationEntityExample example);

    int updateByPrimaryKeySelective(InformationEntity record);

    int updateByPrimaryKey(InformationEntity record);

    int batchInsert(List<InformationEntity> list);

    List<InformationEntity> selectColumnsByExample(InformationEntityExample example);

    List<Long> selectIdsByExample(InformationEntityExample example);

    /**
     * 聚合查询
     */
    List<Map<String, Object>> aggregateQueryByExample(InformationEntityExample example);

    /**
     * 聚合统计,sum、count、max、min 等
     */
    Map<String, Object> aggregateStatisticsByExample(InformationEntityExample example);

    InformationEntity selectUniqueByExample(InformationEntityExample example);

    int logicalDeleteById(@Param("id") long id);

    int logicalDeleteByIds(@Param("ids") long[] ids);
}