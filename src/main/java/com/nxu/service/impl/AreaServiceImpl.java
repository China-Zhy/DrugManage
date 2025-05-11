package com.nxu.service.impl;

import com.nxu.entity.Area;
import com.nxu.mapper.AreaMapper;
import com.nxu.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaServiceImpl implements AreaService {

    @Autowired
    private AreaMapper areaMapper;

    /**
     * 通过自身级别和上级编码查找地区
     *
     * @param level      地区级别
     * @param parentCode 上级编码
     * @return 地区结合
     */
    @Override
    public List<Area> selectArea(Integer level, String parentCode) {
        return areaMapper.selectArea(level, parentCode);
    }
}