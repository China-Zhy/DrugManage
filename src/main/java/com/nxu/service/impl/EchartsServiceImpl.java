package com.nxu.service.impl;

import com.nxu.entity.PieCharts;
import com.nxu.mapper.EchartsMapper;
import com.nxu.service.EchartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EchartsServiceImpl implements EchartsService {

    @Autowired
    private EchartsMapper echartsMapper;

    /**
     * 获取不同角色用户的数量
     *
     * @return 饼图数据集
     */
    @Override
    public List<PieCharts> getTheNumberOfUsersInDifferentRoles() {
        return echartsMapper.getTheNumberOfUsersInDifferentRoles();
    }

    /**
     * 获取系统近一周的用户访问量
     *
     * @return 条形统计图
     */
    @Override
    public List<PieCharts> getTheNumberOfUserVisitsInThePastWeek() {
        return echartsMapper.getTheNumberOfUserVisitsInThePastWeek();
    }
}