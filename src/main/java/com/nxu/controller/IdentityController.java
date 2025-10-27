package com.nxu.controller;

import com.nxu.entity.Identity;
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

/**
 * @author ZhangHongYe
 */
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

        identityService.getAllIdentity().forEach(identity -> {
            List<String> menus = menuService.getRoleHaveMenuName(identity.getId());

            HashMap<String, Object> map = new HashMap<>();
            map.put("identity", identity);
            map.put("menus", menus);
            dataList.add(map);
        });

        model.addAttribute("dataList", dataList);
        return "role/role-manage";
    }

    // 前往角色的菜单编辑页面(穿梭框)
    @GetMapping("/toRoleMenuEdit1/{identity}")
    public String toRoleMenuEdit1(Model model, @PathVariable int identity) {
        model.addAttribute("identityId", identity);
        List<Menu> menus = menuService.getSimpleMenus();    // 全部可选的简单菜单
        model.addAttribute("menus", menus);
        ArrayList<Integer> roleHaveMenuId = menuService.getRoleHaveMenuId(identity);
        model.addAttribute("haveId", roleHaveMenuId);   // 当前角色拥有的菜单编号(适应穿梭框)
        return "role/role-menu-edit1";
    }

    // 前往角色的菜单编辑页面(树组件)
    @GetMapping("/toRoleMenuEdit2/{identity}")
    public String toRoleMenuEdit2(Model model, @PathVariable int identity) {
        model.addAttribute("identityId", identity);
        List<Menu> menus = menuService.getAllMenuForManage();   // 全部可选的复杂菜单
        model.addAttribute("menus", menus);
        ArrayList<Integer> roleHaveMenuId = menuService.getRoleHaveMenuIdTree(identity);
        model.addAttribute("haveId", roleHaveMenuId);   // 当前角色拥有的菜单编号(适应树组件)
        return "role/role-menu-edit2";
    }

    // 进行角色权限更新(接收前端传来的菜单ID列表→递归补全缺失的父级菜单→清除旧权限，添加新权限)
    @PostMapping("/doSetRoleMenu/{roleId}")
    @ResponseBody
    public Integer doSetRoleMenu(@PathVariable Integer roleId, @RequestBody List<Integer> values) {
        return menuService.updateRoleMenus(roleId, values);     // 复杂的逻辑在服务层
    }

    // 添加新角色
    @GetMapping("/doAddRole")
    @ResponseBody
    public Integer doAddRole(String name) {
        return identityService.addIdentity(new Identity(0, name));
    }

    // 修改角色名称
    @PostMapping("/doSetRole")
    @ResponseBody
    public Integer doSetRole(@RequestParam Integer id, @RequestParam String name) {
        return identityService.setIdentity(new Identity(id, name));
    }

    // 删除某个角色
    @GetMapping("/doDelRole/{id}")
    @ResponseBody
    public Integer doDelRole(@PathVariable Integer id) {
        return identityService.delIdentity(id);
    }

}