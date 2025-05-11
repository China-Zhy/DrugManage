package com.nxu.service;

import com.nxu.entity.Area;

import java.util.List;

public interface AreaService {
    /**
     * 通过自身级别和上级编码查找地区
     *
     * @param level      地区级别
     * @param parentCode 上级编码
     * @return 地区结合
     */
    List<Area> selectArea(Integer level, String parentCode);
}