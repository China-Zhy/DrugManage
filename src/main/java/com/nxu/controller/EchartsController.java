package com.nxu.controller;

import com.nxu.entity.PieCharts;
import com.nxu.service.EchartsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class EchartsController {

    @Autowired
    private EchartsService echartsService;

    // 获取各角色用户的数量
    @GetMapping("/getRoleUserCount")
    public List<PieCharts> getRoleUserCount() {
        return echartsService.getTheNumberOfUsersInDifferentRoles();
    }

    // 获取系统近一周的用户访问量
    @GetMapping("/getUserLoginLog")
    public List<PieCharts> getUserLoginLog() {
        return echartsService.getTheNumberOfUserVisitsInThePastWeek();
    }

}