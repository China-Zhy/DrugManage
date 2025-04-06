package com.nxu.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class StockController {

    // 前往入库页面
    @GetMapping("/toStockInput/{what}/{type}")
    public String toStockInput(Model model, @PathVariable int what, @PathVariable int type) {
        model.addAttribute("what", what);
        model.addAttribute("type", type);
        return "stockInput";
    }

    // 前往出库页面
    @GetMapping("/toStockOutput/{what}/{type}")
    public String toStockOutput(Model model, @PathVariable int what, @PathVariable int type) {
        model.addAttribute("what", what);
        model.addAttribute("type", type);

        // 查询库存信息

        return "stockOutput";
    }

}