package com.nxu.service;

import com.github.pagehelper.PageInfo;
import com.nxu.entity.Browse;

import java.util.HashMap;

public interface BrowseService {

    /**
     * 添加用户的通知浏览记录
     *
     * @param browse 浏览记录
     * @return 添加结果
     */
    int addBrowse(Browse browse);

    /**
     * 查询浏览记录
     *
     * @param map who:用户编号 what:通知编号 page:当前页码 limit:每页数据量
     * @return 浏览记录集合
     */
    PageInfo<Browse> selectBrowse(HashMap<String, Object> map);
}