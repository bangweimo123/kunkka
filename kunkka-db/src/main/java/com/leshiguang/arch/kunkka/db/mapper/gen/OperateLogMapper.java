/**
 * generate by mybatisgen in http://git.dianpingoa.com/v1/sh/projects/opplatform/repos/mybatisgen/browse
 */
package com.leshiguang.arch.kunkka.db.mapper.gen;

import com.leshiguang.arch.kunkka.db.entity.gen.OperateLog;
import com.leshiguang.arch.kunkka.db.entity.gen.OperateLogCondition;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface OperateLogMapper {
    long countByCondition(OperateLogCondition example);

    int deleteByCondition(OperateLogCondition example);

    int deleteById(Integer id);

    int insert(OperateLog record);

    int insertSelective(OperateLog record);

    List<OperateLog> selectByCondition(OperateLogCondition example);

    OperateLog selectById(Integer id);

    int updateByConditionSelective(@Param("record") OperateLog record, @Param("example") OperateLogCondition example);

    int updateByCondition(@Param("record") OperateLog record, @Param("example") OperateLogCondition example);

    int updateByIdSelective(OperateLog record);

    int updateById(OperateLog record);

    void batchInsert(@Param("list") List<OperateLog> records);

    OperateLog selectOneByCondition(OperateLogCondition example);
}