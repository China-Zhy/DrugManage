package com.nxu;

import com.github.pagehelper.PageInfo;
import com.nxu.entity.*;
import com.nxu.entity.Record;
import com.nxu.mapper.BrowseMapper;
import com.nxu.mapper.NoticeMapper;
import com.nxu.service.AreaService;
import com.nxu.service.MedicineService;
import com.nxu.service.StockService;
import com.nxu.service.UserService;
import com.nxu.utils.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
@Slf4j
public class SimpleTest {

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private BrowseMapper browseMapper;

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private RedisService redisService;

    @Autowired
    private UserService userService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private StockService stockService;

    // 测试-某用户的未浏览通知
    @Test
    void test1() {
        List<Notice> notices = noticeMapper.selectNoticeByUser(3, 3);
        for (Notice notice : notices) {
            System.out.println(notice);
        }
    }

    // 测试-浏览记录(Browse集合中包含User和Notice)
    @Test
    void test2() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("id", 2);
        map.put("what", 2);
        List<Browse> browses = browseMapper.selectBrowse(map);
        for (Browse browse : browses) {
            System.out.println(browse);
        }
    }

    // 测试Redis
    @Test
    void test3() {
        PageInfo<Medicine> pageInfo = medicineService.getSomeMedicine(null, null, null, null);
        redisService.setList("medicines", Arrays.asList(pageInfo.getList().toArray()));
        List<Object> medicines = redisService.getList("medicines");
        for (Object object : medicines) {
            System.out.println(object);
        }
    }

    // 查询供应商用户
    @Test
    void test4() {
        HashMap<String, Object> map = new HashMap<>();
        map.put("type", 6);
        userService.getSomeUser(map).getList().forEach(System.out::println);
    }

    // 测试查找地区(级联菜单)
    @Test
    void test5() {
        List<Area> areas = areaService.selectArea(3, "152500");
        for (Area area : areas) {
            System.out.println(area);
        }
    }

    // 批量插入库存
    @Test
    void test6() {
        User user = new User();
        user.setId(7);
        List<Record> records = new ArrayList<>();

        for (int i = 1; i <= 30; i++) {
            Record record = new Record();
            record.setWhat(i);
            record.setType(1);
            record.setCount(50);
            record.setPrice(20);
            record.setBirthday(new Date());
            record.setFrom(3);
            record.setOther("初始化入库");
            records.add(record);
        }
        int i = stockService.inputStock(records, user);
        System.out.println(i);
    }
}