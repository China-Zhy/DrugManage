package com.nxu.service;

import com.github.pagehelper.PageInfo;
import com.nxu.entity.User;

import java.util.HashMap;

/**
 * @author ZhangHongYe
 */
public interface UserService {

    int addUser(User user);

    int setUser(User user);

    int delUser(int id);

    User getUserById(int id);

    User login(String phone);

    PageInfo<User> getSomeUser(HashMap<String, Object> map);

    int getUserCount(int type);

}