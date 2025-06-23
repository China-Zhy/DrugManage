package com.nxu.controller;

import com.github.pagehelper.PageInfo;
import com.nxu.entity.Medicine;
import com.nxu.service.MedicineService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Controller
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    // 将药品信息字典存入Redis中
    @GetMapping("/doRedisMedicine")
    @ResponseBody
    public Boolean doRedisMedicine() {
        medicineService.getMedicineDictionary();    // 在用户登录成功后显示提示的时候请求这个方法
        return true;
    }

    // 前往药品管理页面
    @GetMapping("/toMedicine")
    public String medicineHtml(Model model) {
        HashMap<String, Object> dictionary = medicineService.getMedicineDictionary();
        model.addAttribute("nameList", dictionary.get("nameList"));
        model.addAttribute("codeList", dictionary.get("codeList"));
        return "medicine/medicine";
    }

    // 获取全部药品数据
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

    // 获取某个药品数据
    @GetMapping("/getMedicineData/{id}")
    @ResponseBody
    public Medicine getMedicineById(@PathVariable Integer id) {
        return medicineService.getOneMedicine(id);
    }

    // 前往添加药品页面
    @GetMapping("/toMedicineAdd")
    public String medicineAddHtml() {
        return "medicine/medicine-add";
    }

    // 进行添加药品操作
    @PostMapping("/doAddMedicine")
    @ResponseBody
    public HashMap<String, Object> doAddMedicine(@RequestBody Medicine medicine, HttpSession session) {
        medicine.setImage((String) session.getAttribute("base64Image"));
        session.removeAttribute("binaryImage");
        session.removeAttribute("base64Image");

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
        map.put("code", medicineService.setMedicine(medicine));     // 假删除，将药品状态设置为'禁用'
        return map;
    }

    // 进行更新药品操作
    @PostMapping("/doSetMedicine")
    @ResponseBody
    public HashMap<String, Object> doSetMedicine(@RequestBody Medicine medicine, HttpSession session) {
        // 判断药品是否更换了照片
        if (medicine.getImage().equals("1")) {
            medicine.setImage((String) session.getAttribute("base64Image"));
            session.removeAttribute("binaryImage");
            session.removeAttribute("base64Image");
        } else if (medicine.getImage().equals("0")) {
            medicine.setImage(null);    // 如果药品照片没有更新，则都置为空，避免mapper层进行大文本更新
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", medicineService.setMedicine(medicine));
        map.put("image", medicine.getImage());
        return map;
    }
}