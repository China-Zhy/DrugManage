package com.nxu.controller;

import com.nxu.entity.Area;
import com.nxu.entity.User;
import com.nxu.service.AreaService;
import com.nxu.service.MenuService;
import jakarta.servlet.http.HttpSession;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Controller
public class SystemController {

    @Autowired
    private AreaService areaService;

    @Autowired
    private MenuService menuService;

    // 后台框架页面(根据用户类型加载菜单)
    @GetMapping("/")
    public String indexHtml(HttpSession session, Model model) {
        User user = (User) session.getAttribute("loginUser");
        model.addAttribute("menuList", menuService.getMenuByIdentity(user.getType()));
        return "index";
    }

    // 后台默认主页
    @GetMapping("/toSystemHome")
    public String toWelcome() {
        return "system/systemHome";
    }

    // 前往系统设置页面
    @GetMapping("/toSystemSetting")
    public String toSystemSetting() {
        return "system/systemSetting";
    }

    // 图片上传后转为二进制和base64格式存入数据库
    @PostMapping("/uploadImage")
    @ResponseBody
    public Integer uploadImage(@RequestParam("file") MultipartFile file, HttpSession session) throws IOException {
        if (!file.isEmpty()) {
            byte[] binaryData = file.getBytes();
            session.setAttribute("binaryImage", binaryData);
            session.setAttribute("base64Image", STR."data:image/jpeg;base64,\{Base64.encodeBase64String(binaryData)}");
            return 1;
        } else {
            System.out.println("警告：上传的图片为空！");
            return 0;
        }
    }

    // 选择地区(级联选择框)
    @GetMapping("/getArea/{level}/{code}")
    @ResponseBody
    public List<Area> getArea(@PathVariable("level") Integer level, @PathVariable("code") String code) {
        return areaService.selectArea(level, code);
    }
}