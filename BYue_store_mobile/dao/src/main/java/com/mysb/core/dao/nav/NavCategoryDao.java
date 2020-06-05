package com.mysb.core.dao.nav;

import com.mysb.core.pojo.nav.NavCategory;
import com.mysb.core.pojo.nav.NavCategoryQuery;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface NavCategoryDao {
    int countByExample(NavCategoryQuery example);

    int deleteByExample(NavCategoryQuery example);

    int deleteByPrimaryKey(Long id);

    int insert(NavCategory record);

    int insertSelective(NavCategory record);

    List<NavCategory> selectByExample(NavCategoryQuery example);

    NavCategory selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") NavCategory record, @Param("example") NavCategoryQuery example);

    int updateByExample(@Param("record") NavCategory record, @Param("example") NavCategoryQuery example);

    int updateByPrimaryKeySelective(NavCategory record);

    int updateByPrimaryKey(NavCategory record);
}