package com.nxu.controller;

import com.nxu.entity.Identity;
import com.nxu.service.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class IdentityController {

    @Autowired
    private IdentityService identityService;

    @GetMapping("/getAllIdentity")
    @ResponseBody
    public List<Identity> getAllIdentity() {
        return identityService.selectAllIdentity();
    }

}