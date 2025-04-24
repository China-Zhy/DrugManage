package com.nxu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nxu.entity.Notice;
import com.nxu.mapper.NoticeMapper;
import com.nxu.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoticeServiceImpl implements NoticeService {

    @Autowired
    private NoticeMapper noticeMapper;

    @Override
    public int addNotice(Notice notice) {
        return noticeMapper.insertNotice(notice);
    }

    @Override
    public int setNotice(Notice notice) {
        return noticeMapper.updateNotice(notice);
    }

    @Override
    public Notice getNotice(int id) {
        return noticeMapper.selectNoticeById(id);
    }

    @Override
    public PageInfo<Notice> getNotices(Integer status, Integer sender, Integer type, Integer page, Integer limit) {
        if (page != null && limit != null) {
            PageHelper.startPage(page, limit);
        }
        return new PageInfo<>(noticeMapper.selectNotices(status, sender, type));
    }

    @Override
    public PageInfo<Notice> getNoticeByUser(Integer userId, Integer userType, Integer page, Integer limit) {
        if (page == null || limit == null) {
            return new PageInfo<>(noticeMapper.selectNoticeByUser(userId, userType));
        } else {
            PageHelper.startPage(page, limit);
            List<Notice> notices = noticeMapper.selectNoticeByUser(userId, userType);
            return new PageInfo<>(notices);
        }
    }

}