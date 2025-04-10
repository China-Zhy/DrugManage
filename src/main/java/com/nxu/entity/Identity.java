package com.nxu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Identity {     // 用户身份
    private int id;         // 身份编号
    private String name;    // 身份名称
}