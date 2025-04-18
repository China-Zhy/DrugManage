package com.nxu.mapper;

import com.nxu.entity.Log;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface LogMapper {

    int insertLog(Log log);

    int updateLog(@Param("id") int id, @Param("logout") LocalDateTime logout, @Param("type") int type);

    List<Log> selectAllLog();
}