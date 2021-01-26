/**
 * generate by mybatisgen in http://git.dianpingoa.com/v1/sh/projects/opplatform/repos/mybatisgen/browse
 */
package com.leshiguang.arch.kunkka.db.mapper.gen;

import com.leshiguang.arch.kunkka.db.entity.gen.UserRoleMapping;
import com.leshiguang.arch.kunkka.db.entity.gen.UserRoleMappingCondition;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UserRoleMappingMapper {
    long countByCondition(UserRoleMappingCondition example);

    int deleteByCondition(UserRoleMappingCondition example);

    int deleteById(Integer id);

    int insert(UserRoleMapping record);

    int insertSelective(UserRoleMapping record);

    List<UserRoleMapping> selectByCondition(UserRoleMappingCondition example);

    UserRoleMapping selectById(Integer id);

    int updateByConditionSelective(@Param("record") UserRoleMapping record, @Param("example") UserRoleMappingCondition example);

    int updateByCondition(@Param("record") UserRoleMapping record, @Param("example") UserRoleMappingCondition example);

    int updateByIdSelective(UserRoleMapping record);

    int updateById(UserRoleMapping record);

    void batchInsert(@Param("list") List<UserRoleMapping> records);

    UserRoleMapping selectOneByCondition(UserRoleMappingCondition example);
}