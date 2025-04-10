package com.nxu.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Browse {               // 浏览记录
    private int id;                 // 浏览记录编号
    private int who;                // 浏览用户编号
    private int what;               // 通知编号
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime when;     // 浏览时间
}