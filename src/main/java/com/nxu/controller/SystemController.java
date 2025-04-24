package com.nxu.controller;

import com.github.pagehelper.PageInfo;
import com.nxu.entity.Medicine;
import com.nxu.service.MedicineService;
import com.nxu.utils.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;

@Controller
public class SystemController {

    @Autowired
    private RedisService redisService;

    @GetMapping("/")
    public String indexHtml() {
        return "index";
    }

    @GetMapping("/toWelcome")
    public String toWelcome() {
        return "welcome";
    }

    /**
     * 药品信息字典
     *
     * @param model           页面视图
     * @param medicineService 药品服务
     */
    static void getMedicineDictionary(Model model, MedicineService medicineService) {
        PageInfo<Medicine> pageInfo = medicineService.getSomeMedicine(null, null, null, null);
        ArrayList<String> nameList = new ArrayList<>();     // 药品名称字典
        ArrayList<String> codeList = new ArrayList<>();     // 国药准字字典

        for (Medicine medicine : pageInfo.getList()) {
            nameList.add(medicine.getName());
            codeList.add(medicine.getCode());
        }
        model.addAttribute("nameList", nameList);
        model.addAttribute("codeList", codeList);
    }
}