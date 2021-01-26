/**
 * generate by mybatisgen in http://git.dianpingoa.com/v1/sh/projects/opplatform/repos/mybatisgen/browse
 */
package com.leshiguang.arch.kunkka.db.mapper.gen;

import com.leshiguang.arch.kunkka.db.entity.gen.Connect;
import com.leshiguang.arch.kunkka.db.entity.gen.ConnectCondition;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface ConnectMapper {
    long countByCondition(ConnectCondition example);

    int deleteByCondition(ConnectCondition example);

    int deleteById(Integer id);

    int insert(Connect record);

    int insertSelective(Connect record);

    List<Connect> selectByCondition(ConnectCondition example);

    Connect selectById(Integer id);

    int updateByConditionSelective(@Param("record") Connect record, @Param("example") ConnectCondition example);

    int updateByCondition(@Param("record") Connect record, @Param("example") ConnectCondition example);

    int updateByIdSelective(Connect record);

    int updateById(Connect record);

    void batchInsert(@Param("list") List<Connect> records);

    Connect selectOneByCondition(ConnectCondition example);
}