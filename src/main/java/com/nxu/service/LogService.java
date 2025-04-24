package com.nxu.service;

import com.github.pagehelper.PageInfo;
import com.nxu.entity.Log;

public interface LogService {

    int addLog(Log log);

    int setLog(int id, int type);

    PageInfo<Log> getAllLog(Integer page, Integer limit);
}