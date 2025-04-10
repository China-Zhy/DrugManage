package com.nxu.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Notice {               // 通知公告
    private int id;                 // 通知编号
    private String title;           // 通知标题
    private String content;         // 通知内容
    private int sender;             // 发布用户
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime when;     // 发布时间
    private String receiver;        // 接收用户类型
    private int status;             // 通知状态 (1-未发布 2-已发布 3-已销毁)
}