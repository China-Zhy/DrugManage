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

    // 前往药品管理页面
    @GetMapping("/toMedicine")
    public String medicineHtml(Model model) {
        SystemController.getMedicineDictionary(model, medicineService);
        return "medicine";
    }

    // 获取药品数据
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

    // 前往添加药品页面
    @GetMapping("/toMedicineAdd")
    public String medicineAddHtml() {
        return "medicineAdd";
    }

    // 进行添加药品操作
    @PostMapping("/doAddMedicine")
    @ResponseBody
    public HashMap<String, Object> doAddMedicine(@RequestBody Medicine medicine) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", medicineService.addMedicine(medicine));
        return map;
    }

    // 进行删除药品操作
    @GetMapping("/doDelMedicine/{id}")
    @ResponseBody
    public HashMap<String, Object> doDelMedicine(@PathVariable int id) {
        HashMap<String, Object> map = new HashMap<>();
        Medicine medicine = new Medicine();
        medicine.setId(id);
        medicine.setStatus(2);
        map.put("code", medicineService.setMedicine(medicine));     // 加删除，将药品状态设置为'禁用'
        return map;
    }

    // 进行更新药品操作
    @PostMapping("/doSetMedicine")
    @ResponseBody
    public HashMap<String, Object> doSetMedicine(@RequestBody Medicine medicine) {
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", medicineService.setMedicine(medicine));
        return map;
    }

}