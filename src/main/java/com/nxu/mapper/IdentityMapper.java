package com.nxu.mapper;

import com.nxu.entity.Identity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author ZhangHongYe
 */
@Mapper
public interface IdentityMapper {

    int insertIdentity(Identity identity);

    int deleteIdentity(int id);

    int updateIdentity(Identity identity);

    List<Identity> selectAllIdentity();

}