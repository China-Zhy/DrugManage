package com.nxu;

import com.nxu.entity.Browse;
import com.nxu.entity.Notice;
import com.nxu.mapper.BrowseMapper;
import com.nxu.mapper.NoticeMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;

@SpringBootTest
@Slf4j
public class MyTest {

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private BrowseMapper browseMapper;

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

}