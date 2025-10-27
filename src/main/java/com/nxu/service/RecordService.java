package com.nxu.service;

import com.github.pagehelper.PageInfo;

/**
 * @author ZhangHongYe
 */
public interface RecordService {

    int addRecord(com.nxu.entity.Record record);

    PageInfo<com.nxu.entity.Record> queryRecord(com.nxu.entity.Record record, Integer page, Integer limit);

}