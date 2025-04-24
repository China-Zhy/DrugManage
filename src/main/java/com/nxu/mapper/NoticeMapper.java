package com.nxu.mapper;

import com.nxu.entity.Notice;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface NoticeMapper {

    int insertNotice(Notice notice);

    int updateNotice(Notice notice);

    Notice selectNoticeById(int id);

    List<Notice> selectNotices(@Param("status") Integer status, @Param("sender") Integer sender, @Param("type") Integer type);

    /**
     * 查询某个用户未浏览的通知
     */
    List<Notice> selectNoticeByUser(@Param("userId") Integer userId, @Param("userType") Integer userType);
}