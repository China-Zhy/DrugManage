package com.nxu.controller;

import com.github.pagehelper.PageInfo;
import com.nxu.entity.*;
import com.nxu.entity.Record;
import com.nxu.service.MedicineService;
import com.nxu.service.StockService;
import com.nxu.service.VendorService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class StockController {

    @Autowired
    private StockService stockService;

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private VendorService vendorService;

    // 前往库存管理页面
    @GetMapping("/toStock")
    public String toStock(Model model) {
        HashMap<String, Object> dictionary = medicineService.getMedicineDictionary();
        model.addAttribute("nameList", dictionary.get("nameList"));
        model.addAttribute("codeList", dictionary.get("codeList"));
        return "stock";
    }

    // 前往某药品库存页面
    @GetMapping("/toStockData/{id}")
    public String toStockData(Model model, @PathVariable("id") Integer medicineId) {
        model.addAttribute("medicineId", medicineId);
        return "stockData";
    }

    // 获取某药品库存信息
    @GetMapping("/getStockData/{id}")
    @ResponseBody
    public HashMap<String, Object> getStockData(@PathVariable("id") Integer medicineId, Integer page, Integer limit) {
        PageInfo<Stock> pageInfo = stockService.getSomeStock(medicineId, page, limit);

        HashMap<String, Object> map = new HashMap<>();
        map.put("code", 0);
        map.put("count", pageInfo.getTotal());
        map.put("data", pageInfo.getList());

        return map;
    }

    // 前往出库页面
    @GetMapping("/toStockOutput/{id}/{count}")
    public String toStockChange(Model model, @PathVariable int id, @PathVariable int count) {
        model.addAttribute("id", id);
        model.addAttribute("count", count);
        return "stockOutput";
    }

    // 进行出库操作 (包含事务，代码都在服务层)
    @PostMapping("/doOutputStock")
    @ResponseBody
    public HashMap<String, Object> doOutputStock(@RequestBody Record record, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");   // 获取当前登录的用户
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", stockService.outputStock(record, user));
        return map;
    }

    // 前往入库页面
    @GetMapping("/toStockInput")
    public String toStockEnter(Model model) {
        PageInfo<Medicine> allMedicine = medicineService.getSomeMedicine(null, null, null, null);
        ArrayList<Identity> medicineList = new ArrayList<>();

        // 对于 CPU 密集型任务，可以使用并行流 parallelStream() 来利用多核 CPU，提高处理速度
        allMedicine.getList().parallelStream().forEach(medicine -> {
            Identity identity = new Identity();
            identity.setId(medicine.getId());
            identity.setName(STR."\{medicine.getCode()} | \{medicine.getName()} | \{medicine.getSpecs()}");
            medicineList.add(identity);
        });
        model.addAttribute("medicineList", medicineList);

        PageInfo<Vendor> allVendor = vendorService.getAllVendor(null, null);
        ArrayList<Identity> vendorList = new ArrayList<>();

        // 对于 CPU 密集型任务，可以使用并行流 parallelStream() 来利用多核 CPU，提高处理速度
        allVendor.getList().parallelStream().forEach(vendor -> {
            Identity identity = new Identity();
            identity.setId(vendor.getId());
            identity.setName(STR."\{vendor.getName()} | \{vendor.getCode()}");  // 使用String模板来格式化字符串
            vendorList.add(identity);
        });
        model.addAttribute("vendorList", vendorList);

        return "stockInput";
    }

    // 进行入库操作 (包含事务，代码都在服务层)
    @PostMapping("/doInputStock")
    @ResponseBody
    public HashMap<String, Object> doInputStock(@RequestBody List<Record> records, HttpSession session) {
        User user = (User) session.getAttribute("loginUser");   // 获取当前登录的用户
        int i = stockService.inputStock(records, user);

        HashMap<String, Object> map = new HashMap<>();
        map.put("code", i);
        return map;
    }

}