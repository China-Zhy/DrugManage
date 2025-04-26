package com.nxu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nxu.entity.Record;
import com.nxu.mapper.RecordMapper;
import com.nxu.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecordServiceImpl implements RecordService {

    @Autowired
    private RecordMapper recordMapper;

    @Override
    public int addRecord(Record record) {
        return recordMapper.insertRecord(record);
    }

    @Override
    public PageInfo<com.nxu.entity.Record> queryRecord(Record record, Integer page, Integer limit) {
        if (page != null && limit != null) {
            PageHelper.startPage(page, limit);
        }
        List<com.nxu.entity.Record> records = recordMapper.selectAllRecord(record);
        return new PageInfo<>(records);
    }
}