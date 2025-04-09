package com.nxu.controller;

import com.nxu.entity.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SystemController {

    @GetMapping("/")
    public String indexHtml(HttpSession session) {
        User user = new User();
        user.setId(3);
        session.setAttribute("loginUser", user);
        return "index";
    }

    @GetMapping("/toWelcome")
    public String toWelcome() {
        return "welcome";
    }

}