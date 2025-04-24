package com.nxu;

import com.github.pagehelper.PageInfo;
import com.nxu.entity.Browse;
import com.nxu.entity.Medicine;
import com.nxu.entity.Notice;
import com.nxu.mapper.BrowseMapper;
import com.nxu.mapper.NoticeMapper;
import com.nxu.service.MedicineService;
import com.nxu.utils.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
        redisService.setObjectList("medicines", Arrays.asList(pageInfo.getList().toArray()));
        List<Object> medicines = redisService.getObjectList("medicines");
        for (Object object : medicines) {
            System.out.println(object);
        }
    }
}