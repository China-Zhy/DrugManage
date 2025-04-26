package com.nxu.controller;

import com.github.pagehelper.PageInfo;
import com.nxu.entity.Browse;
import com.nxu.entity.Notice;
import com.nxu.entity.User;
import com.nxu.service.BrowseService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
public class BrowseController {

    @Autowired
    private BrowseService browseService;

    // 前往通知浏览记录页面
    @GetMapping("/toBrowse")
    public String toBrowse() {
        return "browse";
    }

    // 添加通知浏览记录
    @GetMapping("/doBrowseAdd/{noticeId}")
    @ResponseBody
    public HashMap<String, Object> doBrowseAdd(HttpSession session, @PathVariable int noticeId) {
        HashMap<String, Object> map = new HashMap<>();
        User user = (User) session.getAttribute("loginUser");
        Browse browse = new Browse();
        browse.setUser(user);
        Notice notice = new Notice();
        notice.setId(noticeId);
        browse.setNotice(notice);
        map.put("code", browseService.addBrowse(browse));
        return map;
    }

    // 获取通知浏览记录数据
    @GetMapping("/getAllBrowseData")
    @ResponseBody
    public HashMap<String, Object> getMedicines(Integer what, Integer who, Integer page, Integer limit) {
        HashMap<String, Object> params = new HashMap<>();
        if (what != null) {
            params.put("what", what);
        }
        if (who != null) {
            params.put("who", who);
        }
        if (page != null) {
            params.put("page", page);
        }
        if (limit != null) {
            params.put("limit", limit);
        }

        PageInfo<Browse> pageInfo = browseService.selectBrowse(params);

        HashMap<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());

        return map;
    }

}