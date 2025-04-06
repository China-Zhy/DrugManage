package com.nxu.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RecordMapper {

    int insertRecord(Record record);

    List<Record> selectAllRecord(Record record);
}