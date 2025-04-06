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

    int deleteUser(@Param("id") int id);

    User selectUserById(@Param("id") int id);

    User selectUserByPhone(@Param("phone") String phone);

    List<User> selectSomeUser(HashMap<String, Object> map);
}