package com.nxu.service;

import com.nxu.entity.Menu;

import java.util.ArrayList;
import java.util.List;

public interface MenuService {

    /**
     * 用户展示-根据身份类型获取菜单(复杂类型)
     *
     * @param identity 用户身份类型
     * @return 菜单集合
     */
    List<Menu> getMenuByIdentity(Integer identity);

    /**
     * 后台管理-获取全部菜单(复杂类型)
     *
     * @return 菜单集合
     */
    List<Menu> getAllMenuForManage();

    /**
     * 权限编辑-获取角色拥有的菜单编号
     *
     * @param identity 用户身份类型
     * @return 菜单编号集合
     */
    ArrayList<Integer> getRoleHaveMenuId(Integer identity);

    /**
     * 权限编辑-获取角色拥有的菜单菜单名称
     *
     * @param identity 用户身份类型
     * @return 菜单编号集合
     */
    ArrayList<String> getRoleHaveMenuName(Integer identity);

    /**
     * 权限管理-获取全部菜单(简单类型)
     *
     * @return 菜单集合
     */
    List<Menu> getSimpleMenus();

    int addMenu(Menu menu);

    int setMenu(Menu menu);

    int delMenu(Integer id);

    /**
     * 更新角色的菜单权限
     *
     * @param roleId  角色ID
     * @param menuIds 菜单ID集合
     * @return 更新结果
     */
    int updateRoleMenus(Integer roleId, List<Integer> menuIds);
}