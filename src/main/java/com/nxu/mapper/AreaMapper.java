package com.nxu.mapper;

import com.nxu.entity.Area;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AreaMapper {

    /**
     * 通过自身级别和上级编码查找地区
     *
     * @param level      地区级别
     * @param parentCode 上级编码
     * @return 地区结合
     */
    List<Area> selectArea(@Param("level") Integer level,@Param("parentCode") String parentCode);
}