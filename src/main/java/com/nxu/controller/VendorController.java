package com.nxu.controller;

import com.github.pagehelper.PageInfo;
import com.nxu.entity.Vendor;
import com.nxu.service.AreaService;
import com.nxu.service.UserService;
import com.nxu.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
public class VendorController {

    @Autowired
    private VendorService vendorService;

    @Autowired
    private UserService userService;

    @Autowired
    private AreaService areaService;

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

    // 前往添加供应商页面
    @GetMapping("/toVendorAdd")
    public String vendorAddHtml(Model model) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("type", 6);
        model.addAttribute("vendorUserList", userService.getSomeUser(map).getList());   // 查找供应商用户
        model.addAttribute("areaList", areaService.selectArea(1, null)); // 默认显示省
        return "vendorAdd";
    }

    // 进行添加供应商操作
    @PostMapping("/doAddVendor")
    @ResponseBody
    public HashMap<String, Object> doAddVendor(@RequestBody Vendor vendor) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", vendorService.addVendor(vendor));
        return map;
    }

    // 进行删除供应商操作
    @GetMapping("/doDelVendor/{id}")
    @ResponseBody
    public HashMap<String, Object> doDelVendor(@PathVariable int id) {
        HashMap<String, Object> map = new HashMap<>();
        Vendor vendor = new Vendor();
        vendor.setId(id);
        vendor.setStatus(2);
        map.put("code", vendorService.setVendor(vendor));   // 假删除，将供应商状态设置为'禁用'
        return map;
    }

    // 打开编辑供应商页面
    @GetMapping("/toSetVendor/{id}")
    public String toSetVendor(Model model, @PathVariable Integer id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("type", 6);
        model.addAttribute("vendorUserList", userService.getSomeUser(map).getList());   // 查找供应商用户
        model.addAttribute("vendor", vendorService.getOneVendor(id));
        return "vendorSet";
    }

    // 进行更新供应商信息操作
    @PostMapping("/doSetVendor")
    @ResponseBody
    public HashMap<String, Object> doSetVendor(@RequestBody Vendor vendor) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", vendorService.setVendor(vendor));
        map.put("info", vendor);
        return map;
    }
}