/**
 * generate by mybatisgen in http://git.dianpingoa.com/v1/sh/projects/opplatform/repos/mybatisgen/browse
 */
package com.leshiguang.arch.redissonx.core.mapper.gen;

import com.leshiguang.arch.redissonx.core.entity.gen.ClusterConnectMapping;
import com.leshiguang.arch.redissonx.core.entity.gen.ClusterConnectMappingCondition;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClusterConnectMappingMapper {
    long countByCondition(ClusterConnectMappingCondition example);

    int deleteByCondition(ClusterConnectMappingCondition example);

    int deleteById(Integer id);

    int insert(ClusterConnectMapping record);

    int insertSelective(ClusterConnectMapping record);

    List<ClusterConnectMapping> selectByCondition(ClusterConnectMappingCondition example);

    ClusterConnectMapping selectById(Integer id);

    int updateByConditionSelective(@Param("record") ClusterConnectMapping record, @Param("example") ClusterConnectMappingCondition example);

    int updateByCondition(@Param("record") ClusterConnectMapping record, @Param("example") ClusterConnectMappingCondition example);

    int updateByIdSelective(ClusterConnectMapping record);

    int updateById(ClusterConnectMapping record);

    void batchInsert(@Param("list") List<ClusterConnectMapping> records);

    ClusterConnectMapping selectOneByCondition(ClusterConnectMappingCondition example);
}