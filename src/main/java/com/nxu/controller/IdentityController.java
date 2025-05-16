package com.nxu.controller;

import com.nxu.entity.Menu;
import com.nxu.service.IdentityService;
import com.nxu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class IdentityController {

    @Autowired
    private IdentityService identityService;

    @Autowired
    private MenuService menuService;

    // 前往身份类型管理页面 (同时获取每个角色拥有的菜单列表)
    @GetMapping("/toRoleManage")
    public String toRoleManage(Model model) {

        // 创建数据列表，每个元素是一个HashMap
        List<HashMap<String, Object>> dataList = new ArrayList<>();

        identityService.selectAllIdentity().forEach(identity -> {
            List<String> menus = menuService.getRoleHaveMenuName(identity.getId());

            HashMap<String, Object> map = new HashMap<>();
            map.put("identity", identity);
            map.put("menus", menus);
            dataList.add(map);
        });

        model.addAttribute("dataList", dataList);
        return "role/roleManage";
    }

    // 前往角色的菜单编辑页面
    @GetMapping("/toRoleMenuEdit/{identity}")
    public String toRoleMenuEdit(Model model, @PathVariable int identity) {
        model.addAttribute("identityId", identity);
        List<Menu> menus = menuService.getSimpleMenus();
        model.addAttribute("menus", menus);   // 全部可选的简单菜单
        ArrayList<Integer> roleHaveMenuId = menuService.getRoleHaveMenuId(identity);
        model.addAttribute("haveId", roleHaveMenuId);   // 当前角色拥有的菜单编号
        return "role/roleMenuEdit";
    }

    // 进行角色权限更新(接收前端传来的菜单ID列表→递归补全缺失的父级菜单→清除旧权限，添加新权限)
    @PostMapping("/doSetRoleMenu/{roleId}")
    @ResponseBody
    public Integer doSetRoleMenu(@PathVariable Integer roleId, @RequestBody List<Integer> values) {
        return menuService.updateRoleMenus(roleId, values);     // 复杂的逻辑在服务层
    }

}