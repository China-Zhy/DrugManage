package com.nxu.mapper;

import com.nxu.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface UserMapper {

    int insertUser(User user);

    int updateUser(User user);

    int deleteUser(int id);

    User selectUserById(int id);

    User selectUserByPhone(String phone);

    List<User> selectSomeUser(HashMap<String, Object> map);

    // 某角色用户数量
    int queryUserCount(int type);

    // 改变某类用户角色类型
    int updateUserType(@Param("oldType") int oldType, @Param("newType") int newType);
}