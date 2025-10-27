package com.nxu.controller;

import com.nxu.entity.PieCharts;
import com.nxu.service.EchartsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ZhangHongYe
 */
@RestController
@Tag(name = "Echarts的Controller", description = "调用Echarts的接口")
public class EchartsController {

    @Autowired
    private EchartsService echartsService;

    // 获取各角色用户的数量
    @GetMapping("/getRoleUserCount")
    @Operation(summary = "展示系统中各角色数量", description = "获取各角色用户的数量")
    public List<PieCharts> getRoleUserCount() {
        return echartsService.getTheNumberOfUsersInDifferentRoles();
    }

    // 获取系统近一周的用户访问量
    @GetMapping("/getUserLoginLog")
    @Operation(summary = "展示系统近一周的用户访问量", description = "获取系统近一周的用户访问量")
    public List<PieCharts> getUserLoginLog() {
        return echartsService.getTheNumberOfUserVisitsInThePastWeek();
    }

}