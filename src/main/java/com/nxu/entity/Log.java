package com.nxu.entity;

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
    private LocalDateTime login;    // 登录时间
    private LocalDateTime logout;   // 退出时间
    private int type;               // 操作类型(0-用户在线 1-主动退出 2-会话过期)
}