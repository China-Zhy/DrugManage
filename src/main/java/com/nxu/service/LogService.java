package com.nxu.service;

import com.github.pagehelper.PageInfo;
import com.nxu.entity.Log;

import java.time.LocalDateTime;

public interface LogService {

    int insertLog(Log log);

    int updateLog(int id, LocalDateTime logout, int type);

    PageInfo<Log> selectAllLog(Integer page, Integer limit);
}