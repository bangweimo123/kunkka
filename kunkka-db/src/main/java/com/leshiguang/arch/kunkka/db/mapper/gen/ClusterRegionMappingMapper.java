/**
 * generate by mybatisgen in http://git.dianpingoa.com/v1/sh/projects/opplatform/repos/mybatisgen/browse
 */
package com.leshiguang.arch.kunkka.db.mapper.gen;

import com.leshiguang.arch.kunkka.db.entity.gen.ClusterRegionMapping;
import com.leshiguang.arch.kunkka.db.entity.gen.ClusterRegionMappingCondition;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClusterRegionMappingMapper {
    long countByCondition(ClusterRegionMappingCondition example);

    int deleteByCondition(ClusterRegionMappingCondition example);

    int deleteById(Integer id);

    int insert(ClusterRegionMapping record);

    int insertSelective(ClusterRegionMapping record);

    List<ClusterRegionMapping> selectByCondition(ClusterRegionMappingCondition example);

    ClusterRegionMapping selectById(Integer id);

    int updateByConditionSelective(@Param("record") ClusterRegionMapping record, @Param("example") ClusterRegionMappingCondition example);

    int updateByCondition(@Param("record") ClusterRegionMapping record, @Param("example") ClusterRegionMappingCondition example);

    int updateByIdSelective(ClusterRegionMapping record);

    int updateById(ClusterRegionMapping record);

    void batchInsert(@Param("list") List<ClusterRegionMapping> records);

    ClusterRegionMapping selectOneByCondition(ClusterRegionMappingCondition example);
}