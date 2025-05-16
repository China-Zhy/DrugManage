package com.nxu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu {                 // 菜单信息表
    private int id;                 // 菜单编号
    private String name;            // 菜单名称
    private String url;             // 菜单链接
    private String icon;            // 菜单图标
    private int parent;             // 上级编号
    private int level;              // 菜单级别
    private int status;             // 菜单状态 (1-使用 2-停用)
    private List<Menu> children;    // 子菜单集合
}