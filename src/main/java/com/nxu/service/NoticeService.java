package com.nxu.service;

import com.github.pagehelper.PageInfo;
import com.nxu.entity.Notice;

public interface NoticeService {

    int addNotice(Notice notice);

    int setNotice(Notice notice);

    Notice getNotice(int id);

    PageInfo<Notice> getNotices(Integer status, Integer sender, Integer type, Integer page, Integer limit);

    /**
     * 查询某个用户未浏览的通知
     */
    PageInfo<Notice> getNoticeByUser(Integer userId, Integer userType, Integer page, Integer limit);
}