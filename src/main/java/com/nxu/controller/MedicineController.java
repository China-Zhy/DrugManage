package com.nxu.controller;

import com.github.pagehelper.PageInfo;
import com.nxu.entity.Medicine;
import com.nxu.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @GetMapping("/toMedicine")
    public String medicineHtml(Model model, String name, String code) {
        model.addAttribute("name", name);
        model.addAttribute("code", code);
        return "medicine";
    }

    @GetMapping("/getAllMedicineData")
    @ResponseBody
    public HashMap<String, Object> getMedicines(String name, String code, Integer page, Integer limit) {
        PageInfo<Medicine> pageInfo = medicineService.getSomeMedicine(name, code, page, limit);

        HashMap<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());

        return map;
    }

    @GetMapping("/toMedicineAdd")
    public String medicineAddHtml() {
        return "medicineAdd";
    }

    @PostMapping("/doAddMedicine")
    @ResponseBody
    public HashMap<String, Object> doAddMedicine(@RequestBody Medicine medicine) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", medicineService.addMedicine(medicine));
        return map;
    }

    @GetMapping("/doDelMedicine/{id}")
    @ResponseBody
    public HashMap<String, Object> doDelMedicine(@PathVariable int id) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", medicineService.delMedicine(id));
        return map;
    }

    @PostMapping("/doSetMedicine")
    @ResponseBody
    public HashMap<String, Object> doSetMedicine(@RequestBody Medicine medicine) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", medicineService.setMedicine(medicine));
        return map;
    }

}