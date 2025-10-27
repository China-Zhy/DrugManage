package com.nxu.service;

import com.nxu.entity.Identity;

import java.util.List;

/**
 * @author ZhangHongYe
 */
public interface IdentityService {

    int addIdentity(Identity identity);

    int delIdentity(int id);

    int setIdentity(Identity identity);

    List<Identity> getAllIdentity();

}