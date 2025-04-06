package com.nxu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Medicine {
    private int id;             // 药品编号
    private String name;        // 药品名称
    private String code;        // 国药准字
    private String compose;     // 药品成分
    private String specs;       // 药品规格
    private String usage;       // 用法用量
    private String image;       // 药品照片
    private String origin;      // 生产厂家
    private int count;          // 库存数量
}