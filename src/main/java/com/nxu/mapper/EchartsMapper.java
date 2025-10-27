package com.nxu.mapper;

import com.nxu.entity.PieCharts;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ZhangHongYe
 */
@Mapper
public interface EchartsMapper {

    /**
     * 获取不同角色用户的数量
     *
     * @return 饼图数据集
     */
    List<PieCharts> getTheNumberOfUsersInDifferentRoles();

    /**
     * 获取系统近一周的用户访问量
     *
     * @return 条形统计图
     */
    List<PieCharts> getTheNumberOfUserVisitsInThePastWeek();

}