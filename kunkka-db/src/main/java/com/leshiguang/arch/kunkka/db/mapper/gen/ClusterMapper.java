/**
 * generate by mybatisgen in http://git.dianpingoa.com/v1/sh/projects/opplatform/repos/mybatisgen/browse
 */
package com.leshiguang.arch.kunkka.db.mapper.gen;

import com.leshiguang.arch.kunkka.db.entity.gen.Cluster;
import com.leshiguang.arch.kunkka.db.entity.gen.ClusterCondition;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ClusterMapper {
    long countByCondition(ClusterCondition example);

    int deleteByCondition(ClusterCondition example);

    int deleteById(Integer id);

    int insert(Cluster record);

    int insertSelective(Cluster record);

    List<Cluster> selectByCondition(ClusterCondition example);

    Cluster selectById(Integer id);

    int updateByConditionSelective(@Param("record") Cluster record, @Param("example") ClusterCondition example);

    int updateByCondition(@Param("record") Cluster record, @Param("example") ClusterCondition example);

    int updateByIdSelective(Cluster record);

    int updateById(Cluster record);

    void batchInsert(@Param("list") List<Cluster> records);

    Cluster selectOneByCondition(ClusterCondition example);
}