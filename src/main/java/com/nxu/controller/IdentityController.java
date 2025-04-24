package com.nxu.controller;

import com.nxu.service.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class IdentityController {

    @Autowired
    private IdentityService identityService;

}