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

    List<Notice> selectNotices(Notice notice);

    /**
     * 查询某个用户未浏览的通知
     */
    List<Notice> selectNoticeByUser(@Param("userId") int userId, @Param("userType") int userType);
}