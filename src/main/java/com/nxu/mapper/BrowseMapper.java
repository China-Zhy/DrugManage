package com.nxu.mapper;

import com.nxu.entity.Browse;
import org.apache.ibatis.annotations.Mapper;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface BrowseMapper {

    int insertBrowse(Browse browse);

    /**
     * 查询浏览记录
     *
     * @param map who:用户编号 what:通知编号
     * @return 浏览记录集合
     */
    List<Browse> selectBrowse(HashMap<String, Object> map);
}