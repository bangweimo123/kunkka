/**
 * generate by mybatisgen in http://git.dianpingoa.com/v1/sh/projects/opplatform/repos/mybatisgen/browse
 */
package com.leshiguang.arch.kunkka.db.mapper.gen;

import com.leshiguang.arch.kunkka.db.entity.gen.Category;
import com.leshiguang.arch.kunkka.db.entity.gen.CategoryCondition;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface CategoryMapper {
    long countByCondition(CategoryCondition example);

    int deleteByCondition(CategoryCondition example);

    int deleteById(Integer id);

    int insert(Category record);

    int insertSelective(Category record);

    List<Category> selectByCondition(CategoryCondition example);

    Category selectById(Integer id);

    int updateByConditionSelective(@Param("record") Category record, @Param("example") CategoryCondition example);

    int updateByCondition(@Param("record") Category record, @Param("example") CategoryCondition example);

    int updateByIdSelective(Category record);

    int updateById(Category record);

    void batchInsert(@Param("list") List<Category> records);

    Category selectOneByCondition(CategoryCondition example);
}