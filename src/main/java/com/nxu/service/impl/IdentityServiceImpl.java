package com.nxu.service.impl;

import com.nxu.entity.Identity;
import com.nxu.mapper.IdentityMapper;
import com.nxu.service.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IdentityServiceImpl implements IdentityService {

    @Autowired
    private IdentityMapper identityMapper;

    @Override
    public int insertIdentity(String name) {
        return identityMapper.insertIdentity(name);
    }

    @Override
    public int deleteIdentity(int id) {
        return identityMapper.deleteIdentity(id);
    }

    @Override
    public int updateIdentity(String name) {
        return identityMapper.updateIdentity(name);
    }

    @Override
    public List<Identity> selectAllIdentity() {
        return identityMapper.selectAllIdentity();
    }
}