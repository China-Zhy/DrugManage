package com.nxu.mapper;

import com.nxu.entity.Menu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Mapper
public interface MenuMapper {

    /**
     * 用户展示-根据身份类型获取菜单(复杂类型)
     *
     * @param identity 用户身份类型
     * @return 菜单集合
     */
    List<Menu> selectMenuByIdentity(Integer identity);

    /**
     * 后台管理-获取全部菜单(复杂类型)
     *
     * @return 菜单集合
     */
    List<Menu> selectAllMenuForManage();

    /**
     * 权限编辑-获取角色拥有的菜单编号
     *
     * @param identity 用户身份类型
     * @return 菜单编号集合
     */
    ArrayList<Integer> selectRoleHaveMenuId(Integer identity);

    /**
     * 权限编辑-获取角色拥有的菜单菜单名称
     *
     * @param identity 用户身份类型
     * @return 菜单编号集合
     */
    ArrayList<String> selectRoleHaveMenuName(Integer identity);

    /**
     * 权限管理-获取全部菜单(简单类型)
     *
     * @return 菜单集合
     */
    List<Menu> selectSimpleMenus();

    int insertMenu(Menu menu);

    int updateMenu(Menu menu);

    int deleteMenu(Integer id);

    Menu selectMenuById(Integer id);

    /**
     * 根据角色类型删除全部菜单权限
     *
     * @param identityId 角色ID
     */
    void deleteRoleMenuByIdentity(Integer identityId);

    /**
     * 添加角色的菜单权限
     *
     * @param roleId  角色ID
     * @param menuIds 菜单ID
     * @return 添加的数量
     */
    int insertManyRoleMenu(@Param("roleId") Integer roleId, @Param("menuIds") Set<Integer> menuIds);
}