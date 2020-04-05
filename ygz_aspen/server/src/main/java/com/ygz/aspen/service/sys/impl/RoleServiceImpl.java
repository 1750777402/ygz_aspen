package com.ygz.aspen.service.sys.impl;

import com.ygz.aspen.dao.sys.RoleMapper;
import com.ygz.aspen.dao.sys.UserRoleMapper;
import com.ygz.aspen.model.sys.Role;
import com.ygz.aspen.service.sys.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;


    @Override
    public List<Role> selectRole(Role role) {


        return null;
    }

    @Override
    public Boolean addRole(Role role) {
        int i = roleMapper.addRole(role);
        if(i == 1){
            return true;
        }
        return false;
    }
}
