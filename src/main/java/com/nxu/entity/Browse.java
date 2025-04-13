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
    private User user;              // 浏览用户     // private int who;
    private Notice notice;          // 通知信息     // private int what;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime when;     // 浏览时间
}