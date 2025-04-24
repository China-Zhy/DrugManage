package com.nxu.controller;

import com.nxu.service.VendorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class VendorController {

    @Autowired
    private VendorService vendorService;


}