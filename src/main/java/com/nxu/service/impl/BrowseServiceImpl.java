package com.nxu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nxu.entity.Browse;
import com.nxu.mapper.BrowseMapper;
import com.nxu.service.BrowseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
public class BrowseServiceImpl implements BrowseService {

    @Autowired
    private BrowseMapper browseMapper;

    @Override
    public int addBrowse(Browse browse) {
        return browseMapper.insertBrowse(browse);
    }

    @Override
    public PageInfo<Browse> selectBrowse(HashMap<String, Object> map) {
        if (map.get("page") != null && map.get("limit") != null) {
            PageHelper.startPage(Integer.parseInt(map.get("page").toString()), Integer.parseInt(map.get("limit").toString()));
            return new PageInfo<>(browseMapper.selectBrowse(map));
        }
        return new PageInfo<>(browseMapper.selectBrowse(map));
    }
}