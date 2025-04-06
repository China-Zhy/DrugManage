package com.nxu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Stock {
    private int id;                 // 库存编号
    private int medicineId;         // 药品编号
    private Date birthday;          // 生产日期
    private double price;           // 药品单价
    private int count;              // 库存数量
}