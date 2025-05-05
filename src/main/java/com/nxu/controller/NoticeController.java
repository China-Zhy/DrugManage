package com.nxu.controller;

import com.github.pagehelper.PageInfo;
import com.nxu.entity.Notice;
import com.nxu.entity.User;
import com.nxu.service.IdentityService;
import com.nxu.service.NoticeService;
import com.nxu.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private IdentityService identityService;

    @Autowired
    private UserService userService;

    // 前往通知管理页面(管理员)
    @GetMapping("/toNoticeMange")
    public String toNotice() {
        return "noticeMange";
    }

    // 获取全部通知数据(管理员)
    @GetMapping("/getAllNoticeData")
    @ResponseBody
    public HashMap<String, Object> getNotices(Integer status, Integer sender, Integer type, Integer page, Integer limit) {

        PageInfo<Notice> pageInfo = noticeService.getNotices(status, sender, type, page, limit);

        HashMap<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());

        return map;
    }

    // 前往用户个人通知页面
    @GetMapping("/toNoticePerson")
    public String toNoticePerson(Model model, HttpSession session, Integer page, Integer limit) {
        User user = (User) session.getAttribute("loginUser");
        PageInfo<Notice> noticeByUser = noticeService.getNoticeByUser(user.getId(), user.getType(), page, limit);
        model.addAttribute("noticeList", noticeByUser.getList());
        return "noticePerson";
    }

    // 前往通知发布页面
    @GetMapping("/toNoticeAdd")
    public String toNoticeAdd(Model model) {
        model.addAttribute("identityList", identityService.selectAllIdentity());
        return "noticeAdd";
    }

    // 添加新的通知
    @PostMapping("/doNoticeAdd")
    @ResponseBody
    public HashMap<String, Object> doNoticeAdd(HttpSession session, @RequestBody Notice notice) {

        User user = (User) session.getAttribute("loginUser");   // 获取当前登录的用户
        notice.setSender(user.getId());     // 设置发布用户

        int i = noticeService.addNotice(notice);

        HashMap<String, Object> map = new HashMap<>();

        if (i > 0) {
            map.put("code", notice.getStatus());
        } else {
            map.put("code", i);
        }
        return map;
    }

    // 更改通知状态
    @GetMapping("/setNoticeStatus/{id}/{status}")
    @ResponseBody
    public HashMap<String, Object> doNoticeAdd(@PathVariable Integer id, @PathVariable Integer status) {
        HashMap<String, Object> map = new HashMap<>();
        Notice notice = new Notice();
        notice.setId(id);
        notice.setStatus(status);
        map.put("code", noticeService.setNotice(notice));
        return map;
    }

    // 获取某个通知的详情信息
    @GetMapping("/toNoticeDetail/{id}")
    public String toNoticeDetail(Model model, @PathVariable Integer id) {
        Notice notice = noticeService.getNotice(id);
        model.addAttribute("notice", notice);
        User sender = userService.getUserById(notice.getSender());
        model.addAttribute("sender", sender);
        return "noticeDetail";
    }

    // 前往系统维护通知页面
    @GetMapping("/toNoticeDisabled")
    public String toNoticeDisabled(Model model) {
        PageInfo<Notice> notices = noticeService.getNotices(2, null, 2, null, null);
        if (!notices.getList().isEmpty()) {
            model.addAttribute("noticeList", notices.getList());
            return "noticeDisabled";
        } else {
            return "index";
        }
    }
}