package com.nxu.service;

import com.github.pagehelper.PageInfo;

public interface RecordService {

    int addRecord(com.nxu.entity.Record record);

    PageInfo<Record> queryRecord(com.nxu.entity.Record record, Integer page, Integer limit);
}