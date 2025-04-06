package com.nxu.service;

import com.github.pagehelper.PageInfo;
import com.nxu.entity.User;

import java.util.HashMap;

public interface UserService {

    int addUser(User user);

    int setUser(User user);

    int delUser(int id);

    User getUserById(int id);

    User getUserByPhone(String phone);

    PageInfo<User> getSomeUser(HashMap<String, Object> map);
}