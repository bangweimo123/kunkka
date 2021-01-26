/**
 * generate by mybatisgen in http://git.dianpingoa.com/v1/sh/projects/opplatform/repos/mybatisgen/browse
 */
package com.leshiguang.arch.kunkka.db.mapper.gen;

import com.leshiguang.arch.kunkka.db.entity.gen.ClusterAuthMapping;
import com.leshiguang.arch.kunkka.db.entity.gen.ClusterAuthMappingCondition;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClusterAuthMappingMapper {
    long countByCondition(ClusterAuthMappingCondition example);

    int deleteByCondition(ClusterAuthMappingCondition example);

    int deleteById(Integer id);

    int insert(ClusterAuthMapping record);

    int insertSelective(ClusterAuthMapping record);

    List<ClusterAuthMapping> selectByCondition(ClusterAuthMappingCondition example);

    ClusterAuthMapping selectById(Integer id);

    int updateByConditionSelective(@Param("record") ClusterAuthMapping record, @Param("example") ClusterAuthMappingCondition example);

    int updateByCondition(@Param("record") ClusterAuthMapping record, @Param("example") ClusterAuthMappingCondition example);

    int updateByIdSelective(ClusterAuthMapping record);

    int updateById(ClusterAuthMapping record);

    void batchInsert(@Param("list") List<ClusterAuthMapping> records);

    ClusterAuthMapping selectOneByCondition(ClusterAuthMappingCondition example);
}