package com.nxu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Area {                 // 地区数据
    private String code;            // 地址编码
    private String name;            // 地址名称
    private String fullName;        // 地区全称
    private String parentCode;      // 上级编码
    private String spell;           // 字母缩写
    private int level;              // 地区级别：1-省 2-市 3-县
}