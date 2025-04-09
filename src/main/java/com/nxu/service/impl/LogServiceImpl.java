package com.nxu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nxu.entity.Log;
import com.nxu.mapper.LogMapper;
import com.nxu.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogMapper logMapper;

    @Override
    public int insertLog(Log log) {
        return logMapper.insertLog(log);
    }

    @Override
    public int updateLog(int id, LocalDateTime logout, int type) {
        return logMapper.updateLog(id, logout, type);
    }

    @Override
    public PageInfo<Log> selectAllLog(Integer page, Integer limit) {
        PageHelper.startPage(page, limit);
        List<Log> logs = logMapper.selectAllLog();
        return new PageInfo<>(logs);
    }
}