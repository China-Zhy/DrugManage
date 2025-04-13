package com.nxu.controller;

import com.github.pagehelper.PageInfo;
import com.nxu.entity.Record;
import com.nxu.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class RecordController {

    @Autowired
    private RecordService recordService;

    // 前往库存记录页面
    @GetMapping("/toRecord")
    public String toRecord() {
        return "record";
    }

    // 根据条件查询库存记录数据
    @GetMapping("/getRecordData")
    @ResponseBody
    public Map<String, Object> getRecordData(Integer page, Integer limit, Integer what, Integer who, Integer type) {

        Record record = new Record();
        if (what != null) {
            record.setWhat(what);
        }
        if (who != null) {
            record.setWho(who);
        }
        if (type != null) {
            record.setType(type);
        }
        PageInfo<java.lang.Record> pageInfo = recordService.queryRecord(record, page, limit);

        HashMap<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());
        return map;
    }

}