package com.example.demo.persistence.mapper;

import com.example.demo.persistence.entity.ArticleEntity;
import com.example.demo.persistence.entity.ArticleEntityExample;
import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface ArticleEntityMapper {
    long countByExample(ArticleEntityExample example);

    int deleteByExample(ArticleEntityExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(ArticleEntity row);

    int insertSelective(ArticleEntity row);

    List<ArticleEntity> selectByExample(ArticleEntityExample example);

    ArticleEntity selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("row") ArticleEntity row, @Param("example") ArticleEntityExample example);

    int updateByExample(@Param("row") ArticleEntity row, @Param("example") ArticleEntityExample example);

    int updateByPrimaryKeySelective(ArticleEntity row);

    int updateByPrimaryKey(ArticleEntity row);

    int batchInsert(List<ArticleEntity> list);

    List<ArticleEntity> selectColumnsByExample(ArticleEntityExample example);

    List<Long> selectIdsByExample(ArticleEntityExample example);

    /**
     * 聚合查询
     */
    List<Map<String, Object>> aggregateQueryByExample(ArticleEntityExample example);

    /**
     * 聚合统计,sum、count、max、min 等
     */
    Map<String, Object> aggregateStatisticsByExample(ArticleEntityExample example);

    ArticleEntity selectUniqueByExample(ArticleEntityExample example);

    int logicalDeleteById(@Param("id") long id);

    int logicalDeleteByIds(@Param("ids") long[] ids);

    int deleteByIds(@Param("ids") long[] ids);
}