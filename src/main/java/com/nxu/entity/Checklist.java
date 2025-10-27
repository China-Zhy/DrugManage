package com.nxu.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author ZhangHongYe
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Checklist {                // 操作清单
    private int id;                     // 清单编号
    private int actionUser;             // 操作人员
    private LocalDateTime actionTime;   // 操作时间
    private double totalAmount;         // 药品总价
    private int vendorId;               // 供应商户
    private int verifyUser;             // 审核人员
    private LocalDateTime verifyTime;   // 审核时间
}