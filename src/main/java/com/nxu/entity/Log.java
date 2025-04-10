package com.nxu.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log {                  // 用户日志
    private int id;                 // 日志编号
    private int who;                // 用户编号
    private String name;            // 用户昵称
    private String ip;              // 机器IP地址
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime login;    // 登录时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime logout;   // 退出时间
    private int type;               // 操作类型(1-主动退出 2-会话过期)
}