package com.nxu.mapper;

import com.nxu.entity.Identity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IdentityMapper {

    int insertIdentity(@Param("name") String name);

    int deleteIdentity(@Param("id") int id);

    int updateIdentity(@Param("name") String name);

    List<Identity> selectAllIdentity();
}