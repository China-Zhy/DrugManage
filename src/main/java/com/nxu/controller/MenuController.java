package com.nxu.controller;

import com.nxu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/toMenuManage")
    public String toMenuManage() {
        return "menu/menuManage";
    }

    @GetMapping("/getAllMenuData")
    @ResponseBody
    public HashMap<String, Object> getMenuData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("data", menuService.getAllMenuForManage());
        return map;
    }
}