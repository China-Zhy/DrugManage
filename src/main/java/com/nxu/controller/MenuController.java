package com.nxu.controller;

import com.nxu.entity.Menu;
import com.nxu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
public class MenuController {

    @Autowired
    private MenuService menuService;

    // 打开菜单管理页面
    @GetMapping("/toMenuManage")
    public String toMenuManage() {
        return "menu/menu-manage";
    }

    // 获取全部菜单数据
    @GetMapping("/getAllMenuData")
    @ResponseBody
    public HashMap<String, Object> getMenuData() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("data", menuService.getAllMenuForManage());
        return map;
    }

    // 打开图标选择页面
    @GetMapping("/toChooseIcon")
    public String toChooseIcon() {
        return "menu/choose-icon";
    }

    // 打开菜单编辑页面
    @GetMapping("/toMenuInfoEdit/{id}")
    public String toMenuInfoEdit(Model model, @PathVariable Integer id) {
        model.addAttribute("menu", menuService.getMenuById(id));
        return "menu/menu-info-edit";
    }

    // 进行更新菜单操作
    @PostMapping("/doSetMenuInfo")
    @ResponseBody
    public Integer doSetMedicine(@RequestBody Menu menu) {
        return menuService.setMenu(menu);
    }
}