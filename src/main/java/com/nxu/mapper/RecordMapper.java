package com.nxu.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecordMapper {

    int insertRecord(com.nxu.entity.Record record);

    List<com.nxu.entity.Record> selectAllRecord(com.nxu.entity.Record record);
}