package com.nxu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Record {           // 库存记录
    private int id;             // 记录编号
    private Date when;          // 记录时间
    private int what;           // 药品编号
    private int type;           // 操作类型 (1-入库 2-出库)
    private int count;          // 药品数量
    private double price;       // 药品单价
    private Date birthday;      // 生产日期
    private int who;            // 操作用户
    private String other;       // 备注信息
}