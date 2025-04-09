package com.nxu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class User {
    private int id;             // 用户编号
    private String name;        // 用户姓名
    private String phone;       // 手机号码
    private String password;    // 账户密码
    private String address;     // 居住地址
    private String avatar;      // 用户头像
    private String card;        // 身份证号
    private String other;       // 其他信息
    private int type;           // 身份类型
    private int status;         // 状态信息 (1-可用 0-禁用)
}