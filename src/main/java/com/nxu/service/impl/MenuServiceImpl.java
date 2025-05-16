package com.nxu.service.impl;

import com.nxu.entity.Menu;
import com.nxu.mapper.MenuMapper;
import com.nxu.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    /**
     * 用户展示-根据身份类型获取菜单(复杂类型)
     *
     * @param identity 用户身份类型
     * @return 菜单集合
     */
    @Override
    public List<Menu> getMenuByIdentity(Integer identity) {
        return menuMapper.selectMenuByIdentity(identity);
    }

    /**
     * 后台管理-获取全部菜单(复杂类型)
     *
     * @return 菜单集合
     */
    @Override
    public List<Menu> getAllMenuForManage() {
        return menuMapper.selectAllMenuForManage();
    }

    /**
     * 权限编辑-获取角色拥有的菜单编号
     *
     * @param identity 用户身份类型
     * @return 菜单编号集合
     */
    @Override
    public ArrayList<Integer> getRoleHaveMenuId(Integer identity) {
        return menuMapper.selectRoleHaveMenuId(identity);
    }

    /**
     * 权限编辑-获取角色拥有的菜单菜单名称
     *
     * @param identity 用户身份类型
     * @return 菜单编号集合
     */
    @Override
    public ArrayList<String> getRoleHaveMenuName(Integer identity) {
        return menuMapper.selectRoleHaveMenuName(identity);
    }

    /**
     * 权限管理-获取全部菜单(简单类型)
     *
     * @return 菜单集合
     */
    @Override
    public List<Menu> getSimpleMenus() {
        return menuMapper.selectSimpleMenus();
    }

    @Override
    public int addMenu(Menu menu) {
        return menuMapper.insertMenu(menu);
    }

    @Override
    public int setMenu(Menu menu) {
        return menuMapper.updateMenu(menu);
    }

    @Override
    public int delMenu(Integer id) {
        return menuMapper.deleteMenu(id);
    }

    /**
     * 更新角色的菜单权限
     *
     * @param roleId  角色ID
     * @param menuIds 菜单ID集合
     * @return 更新结果
     */
    @Override
    @Transactional
    public int updateRoleMenus(Integer roleId, List<Integer> menuIds) {

        // 1. 补全所有父级菜单（确保一级菜单被选中）
        Set<Integer> result = new CopyOnWriteArraySet<>(menuIds);   // 使用Set避免重复

        Set<Integer> father = new CopyOnWriteArraySet<>();  // 一级菜单编号

        menuIds.parallelStream().forEach(menuId -> {
            Menu menu = menuMapper.selectMenuById(menuId);
            if (menu != null && menu.getLevel() == 2) { // 二级菜单需要补全父级
                father.add(menu.getParent());
                result.add(menu.getParent());   // 添加父级菜单
            }
        });

        result.parallelStream().forEach(menuId -> {
            if (!father.contains(menuId)) {
                Menu menu = menuMapper.selectMenuById(menuId);
                if (menu != null && menu.getLevel() == 1 && !menu.getUrl().startsWith("/")) {
                    result.remove(menuId);  // 一级菜单中如果没有添加二级菜单就移除
                }
            }
        });

        // 2. 删除该角色的所有旧权限
        menuMapper.deleteRoleMenuByIdentity(roleId);

        // 3. 为该角色添加新权限
        int i = 0;
        if (!result.isEmpty()) {
            i = menuMapper.insertManyRoleMenu(roleId, result);
        }

        if (i == result.size()) {
            return 1;
        } else {
            throw new RuntimeException("Tips：角色菜单更新失败！事务回滚！");
        }
    }
}