package com.nxu.controller;

import com.github.pagehelper.PageInfo;
import com.nxu.entity.Record;
import com.nxu.entity.Stock;
import com.nxu.entity.User;
import com.nxu.service.RecordService;
import com.nxu.service.StockService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.HashMap;

@Controller
public class StockController {

    @Autowired
    private StockService stockService;

    @Autowired
    private RecordService recordService;

    // 前往库存管理页面
    @GetMapping("/toStock")
    public String toStock(Model model, String name, String code) {
        model.addAttribute("name", name);
        model.addAttribute("code", code);
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

    // 进行出库操作
    @PostMapping("/doOutputStock")
    @ResponseBody
    public HashMap<String, Object> doOutputStock(@RequestBody Record record, HttpSession session) {

        Stock stock = stockService.getStockById(record.getId());

        record.setWhat(stock.getMedicineId());
        record.setType(2);                      // 入库-1 出库-2
        record.setBirthday(stock.getBirthday());

        User user = (User) session.getAttribute("loginUser");   // 获取当前登录的用户
        record.setWho(user.getId());

        int i = recordService.addRecord(record);

        int j = stockService.changeStock(stock.getId(), (stock.getCount() - record.getCount()));   // 出库 减法操作

        HashMap<String, Object> map = new HashMap<>();

        map.put("code", i == 1 && j == 1 ? 1 : 0);

        return map;
    }

    // 前往入库页面
    @GetMapping("/toStockInput/{id}")
    public String toStockInput(Model model, @PathVariable("id") Integer id) {
        model.addAttribute("id", id);
        return "stockInput";
    }

    // 进行入库操作
    @PostMapping("/doInputStock")
    @ResponseBody
    public HashMap<String, Object> doInputStock(@RequestBody Record record, HttpSession session) {

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        String recordBirthday = sdf.format(record.getBirthday());   // 格式化页面提交过来的日期

        // 先查询是否存在相同的库存
        PageInfo<Stock> pageInfo = stockService.getSomeStock(record.getId(), 1, 1000);
        int isHave = 0;     // 标识是否有同样的库存
        for (Stock stock : pageInfo.getList()) {
            if (stock.getPrice() == record.getPrice() && recordBirthday.equals(sdf.format(stock.getBirthday()))) {
                isHave = stock.getId();
                break;
            }
        }

        User user = (User) session.getAttribute("loginUser");   // 获取当前登录的用户
        record.setWho(user.getId());
        record.setType(1);                      // 入库-1 出库-2
        record.setWhat(record.getId());     // 此 ID 是药品编号

        int i, j;   // 库存操作结果, 记录操作结果

        if (isHave == 0) {  // 没有相同的库存
            Stock stock = new Stock();      // 创建新的库存
            stock.setMedicineId(record.getId());
            stock.setBirthday(record.getBirthday());
            stock.setPrice(record.getPrice());
            stock.setCount(record.getCount());

            i = stockService.addStock(stock);   // 插入新的库存

        } else {    // 存在相同的库存

            Stock stock = stockService.getStockById(isHave);
            i = stockService.changeStock(stock.getId(), (stock.getCount() + record.getCount()));   // 入库 加法操作
        }

        j = recordService.addRecord(record);

        HashMap<String, Object> map = new HashMap<>();

        map.put("code", i == 1 && j == 1 ? 1 : 0);
        return map;
    }

}