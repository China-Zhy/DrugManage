package com.nxu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {             // 用户信息
    private int id;             // 用户编号
    private String name;        // 用户姓名
    private String phone;       // 手机号码
    private String password;    // 账户密码
    private String address;     // 居住地址
    private String card;        // 身份证号
    private String other;       // 其他信息
    private int type;           // 身份类型
    private int status;         // 状态信息 (1-可用 2-停用)
    private byte[] binary;      // 二进制图片
    private String base64;      // base64图片
}