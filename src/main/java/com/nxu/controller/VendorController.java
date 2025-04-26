package com.nxu.controller;

import com.github.pagehelper.PageInfo;
import com.nxu.entity.Vendor;
import com.nxu.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
public class VendorController {

    @Autowired
    private VendorService vendorService;

    // 获取某个供应商信息
    @GetMapping("/getVendorData/{id}")
    @ResponseBody
    public Vendor getVendorInfo(@PathVariable int id) {
        return vendorService.getOneVendor(id);
    }

    // 前往供应商管理页面
    @GetMapping("/toVendor")
    public String toVendor() {
        return "vendor";
    }

    // 获取全部供应商信息
    @GetMapping("/getAllVendor")
    @ResponseBody
    public HashMap<String, Object> getAllVendor(Integer page, Integer limit) {
        PageInfo<Vendor> allVendor = vendorService.getAllVendor(page, limit);
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("count", allVendor.getTotal());
        map.put("data", allVendor.getList());
        return map;
    }
}