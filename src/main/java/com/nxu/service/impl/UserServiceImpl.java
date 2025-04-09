package com.nxu.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.nxu.entity.User;
import com.nxu.mapper.UserMapper;
import com.nxu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public int addUser(User user) {
        return userMapper.insertUser(user);
    }

    @Override
    public int setUser(User user) {
        return userMapper.updateUser(user);
    }

    @Override
    public int delUser(int id) {
        return userMapper.deleteUser(id);
    }

    @Override
    public User getUserById(int id) {
        return userMapper.selectUserById(id);
    }

    @Override
    public User getUserByPhone(String phone) {
        return userMapper.selectUserByPhone(phone);
    }

    @Override
    public PageInfo<User> getSomeUser(HashMap<String, Object> map) {
        PageHelper.startPage(Integer.parseInt(map.get("page").toString()), Integer.parseInt(map.get("rows").toString()));
        List<User> users = userMapper.selectSomeUser(map);
        return new PageInfo<>(users);
    }
}