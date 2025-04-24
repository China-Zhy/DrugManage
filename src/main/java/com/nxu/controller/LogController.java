package com.nxu.controller;

import com.github.pagehelper.PageInfo;
import com.nxu.entity.Log;
import com.nxu.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
public class LogController {

    @Autowired
    private LogService logService;

    // 前往登录日志管理页面
    @GetMapping("/toLogManage")
    public String medicineHtml() {
        return "logManage";
    }

    // 获取登录日志数据
    @GetMapping("/getLogData")
    @ResponseBody
    public HashMap<String, Object> getMedicines(Integer page, Integer limit) {
        PageInfo<Log> pageInfo = logService.getAllLog(page, limit);

        HashMap<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());

        return map;
    }

}