package com.nxu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Medicine {         // 药品信息
    private int id;             // 药品编号
    private String name;        // 药品名称
    private String code;        // 国药准字
    private String compose;     // 药品成分
    private String specs;       // 药品规格
    private String usage;       // 用法用量
    private String image;       // 药品照片
    private String origin;      // 生产厂家
    private int status;         // 状态信息 (1-可用 2-禁用)
}