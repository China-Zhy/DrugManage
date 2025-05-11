package com.nxu.utils;

import com.nxu.entity.User;
import com.nxu.service.LogService;
import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.servlet.http.HttpSessionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 监听器
 */
@WebListener
@Component
public class SessionListener implements HttpSessionListener {

    @Autowired
    private LogService logService;

    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSessionListener.super.sessionCreated(se);
    }

    @Override
    public void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        User user = (User) session.getAttribute("loginUser");
        Integer logId = (Integer) session.getAttribute("logId");
        if (user != null && logId != null) {
            logService.setLog(logId, 2);    // 操作类型(0-用户在线 1-主动退出 2-会话过期)
            session.invalidate();
        }
    }
}