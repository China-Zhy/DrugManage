package com.nxu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nxu.entity.Log;
import com.nxu.mapper.LogMapper;
import com.nxu.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    @Override
    public int addLog(Log log) {
        return logMapper.insertLog(log);
    }

    @Override
    public int setLog(int id, int type) {
        return logMapper.updateLog(id, type);
    }

    @Override
    public PageInfo<Log> getAllLog(Integer page, Integer limit) {
        if (page != null && limit != null) {
            PageHelper.startPage(page, limit);
        }
        return new PageInfo<>(logMapper.selectAllLog());
    }
}