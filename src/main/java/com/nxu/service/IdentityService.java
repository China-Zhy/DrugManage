package com.nxu.service;

import com.nxu.entity.Identity;

import java.util.List;

public interface IdentityService {

    int insertIdentity(String name);

    int deleteIdentity(int id);

    int updateIdentity(String name);

    List<Identity> selectAllIdentity();
}