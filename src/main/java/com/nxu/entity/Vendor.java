package com.nxu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vendor {               // 供应商信息
    private int id;                 // 供应商编号
    private String name;            // 供应商名称
    private String people;          // 法定代表人
    private String address;         // 详细地址
    private String code;            // 营业执照
    private String phone;           // 联系电话
    private String email;           // 电子邮箱
    private int status;             // 状态信息 (1-可用 2-停用)
}