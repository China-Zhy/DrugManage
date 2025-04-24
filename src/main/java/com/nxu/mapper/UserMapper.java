package com.nxu.mapper;

import com.nxu.entity.User;
import org.apache.ibatis.annotations.Mapper;

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
}