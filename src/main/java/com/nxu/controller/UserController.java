package com.nxu.controller;

import com.github.pagehelper.PageInfo;
import com.nxu.entity.Log;
import com.nxu.entity.Notice;
import com.nxu.entity.User;
import com.nxu.service.LogService;
import com.nxu.service.NoticeService;
import com.nxu.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
public class UserController {

    @Autowired
    public UserService userService;

    @Autowired
    private LogService logService;

    @Autowired
    private NoticeService noticeService;

    // 前往用户登录页面
    @GetMapping("/toUserLogin")
    public String toUserLogin() {
        return "userLogin";
    }

    // 将用户的登录信息存入session并添加登录日志
    void addSessionAndLog(HttpSession session, HttpServletRequest request, User user) {
        session.setAttribute("loginUser", user);    // 把登录用户信息存入session

        // 添加用户的登录日志
        Log log = new Log();
        log.setWho(user.getId());
        log.setName(user.getName());
        log.setIp(request.getRemoteAddr());     // 获取用户的IP地址
        log.setType(0);     // 操作类型(0-用户在线 1-主动退出 2-会话过期)

        logService.addLog(log);
        session.setAttribute("logId", log.getId()); // 把当前用户登录日志的ID存入session
    }

    // 进行用户登录
    @PostMapping("/doUserLogin")
    @ResponseBody
    public HashMap<String, Object> doUserLogin(String phone, String password, HttpSession session, HttpServletRequest request) {

        HashMap<String, Object> map = new HashMap<>();

        User user = userService.login(phone);

        if (user == null) {
            map.put("code", 0);
            map.put("info", "该手机号尚未注册！");
        } else {
            if (password.equals(user.getPassword())) {

                // 判断是否存在系统维护通知
                PageInfo<Notice> notices = noticeService.getNotices(2, null, 2, null, null);

                if (notices.getList().isEmpty()) {
                    // 当前没有系统维护通知
                    addSessionAndLog(session, request, user);

                    map.put("code", 1);
                    map.put("info", STR."登录成功！欢迎您：\{user.getName()}");
                } else {
                    // 当前存在系统维护通知

                    boolean isSender = false;   // 判断当前登录用户是否发布系统维护通知

                    for (Notice notice : notices.getList()) {
                        if (notice.getSender() == user.getId()) {
                            isSender = true;
                            break;
                        }
                    }

                    if (isSender) {
                        // 虽然存在系统维护通知，但是当前登录用户是通知发布人之一
                        addSessionAndLog(session, request, user);

                        map.put("code", 1);
                        map.put("info", STR."系统维护中，欢迎您：\{user.getName()}");
                    } else {
                        map.put("code", 3);     // 存在系统维护通知
                        map.put("info", "系统维护中，即将跳转通知页面~");
                    }
                }
            } else {
                map.put("code", 2);
                map.put("info", "登录失败！密码错误，请重试~");
            }
        }
        return map;
    }

    // 用户退出登录
    @GetMapping("/doUserLogout")
    @ResponseBody
    public HashMap<String, Object> doUserLogout(HttpSession session) {
        HashMap<String, Object> map = new HashMap<>();
        User user = (User) session.getAttribute("loginUser");
        Integer logId = (Integer) session.getAttribute("logId");

        if (user != null && logId != null) {
            logService.setLog(logId, 1);    // 操作类型(0-用户在线 1-主动退出 2-会话过期)
            map.put("code", 1);
        } else {
            map.put("code", 0);
        }
        return map;
    }

    // 前往用户注册页面
    @GetMapping("/toUserEnroll")
    public String toUserEnroll() {
        return "userEnroll";
    }

    // 进行用户注册
    @PostMapping("/doUserEnroll")
    @ResponseBody
    public HashMap<String, Object> doUserEnroll(@ModelAttribute("user") User user) {
        HashMap<String, Object> map = new HashMap<>();
        User login = userService.login(user.getPhone());
        if (login == null) {
            int i = userService.addUser(user);
            map.put("code", i);
        } else {
            map.put("code", 2);     // 手机号已被注册
        }
        return map;
    }

    // 验证登录用户的账户密码
    @PostMapping("/confirmLoginUserPassword")
    @ResponseBody
    public boolean getLoginUserPassword(HttpSession session, @RequestParam("password") String password) {
        User user = (User) session.getAttribute("loginUser");
        return user.getPassword().equals(password);
    }
}