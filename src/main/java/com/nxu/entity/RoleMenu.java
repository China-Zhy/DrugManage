package com.nxu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleMenu {             // 角色菜单表
    private int identityId;         // 角色编号
    private int menuId;             // 菜单编号
}