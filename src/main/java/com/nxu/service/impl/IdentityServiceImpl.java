package com.nxu.service.impl;

import com.nxu.entity.Identity;
import com.nxu.mapper.IdentityMapper;
import com.nxu.mapper.MenuMapper;
import com.nxu.mapper.UserMapper;
import com.nxu.service.IdentityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author ZhangHongYe
 */
@Service
public class IdentityServiceImpl implements IdentityService {

    @Autowired
    private IdentityMapper identityMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MenuMapper menuMapper;

    @Override
    public int addIdentity(Identity identity) {
        identityMapper.insertIdentity(identity);
        return identity.getId();
    }

    @Transactional
    @Override
    public int delIdentity(int id) {
        int i = userMapper.queryUserCount(id);
        if (i > 0) {
            return 0;   // 当前角色存在用户，不能删除
        }
        menuMapper.deleteRoleMenuByIdentity(id);    // 删除这个角色的所有菜单权限
        return identityMapper.deleteIdentity(id);
    }

    @Override
    public int setIdentity(Identity identity) {
        return identityMapper.updateIdentity(identity);
    }

    @Override
    public List<Identity> getAllIdentity() {
        return identityMapper.selectAllIdentity();
    }

}