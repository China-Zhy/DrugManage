package com.nxu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SystemController {

    @GetMapping("/toWelcome")
    public String toWelcome() {
        return "welcome";
    }

}