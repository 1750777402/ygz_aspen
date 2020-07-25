package com.ygz.aspen.service.sys.impl;

import com.ygz.aspen.common.base.PageQueryParam;
import com.ygz.aspen.common.base.PageQueryResult;
import com.ygz.aspen.dao.sys.RoleMapper;
import com.ygz.aspen.dao.sys.UserRoleMapper;
import com.ygz.aspen.model.sys.Role;
import com.ygz.aspen.model.sys.UserRole;
import com.ygz.aspen.param.sys.RoleDTO;
import com.ygz.aspen.service.sys.RoleService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;


    @Override
    public PageQueryResult<Role> selectRole(RoleDTO dto, PageQueryParam page) {
        if(dto == null){
            return new PageQueryResult<>();
        }
        dto.setPageIndex(page.getStart());
        int count = roleMapper.countRole(dto);
        List<Role> roles = null;
        if(count > 0){
            roles = roleMapper.selectRoleList(dto);
        }
        return new PageQueryResult<>(roles, count, page.getPageIndex(), page.getPageSize());
    }

    @Override
    public Boolean addRole(Role role) {
        if(role == null){
            return false;
        }
        int i = roleMapper.addRole(role);
        if(i == 1){
            return true;
        }
        return false;
    }

    @Override
    public Role getRoleById(Long roleId) {
        if(roleId == null){
            return null;
        }
        return roleMapper.getRoleById(roleId);
    }

    @Override
    public List<Role> selectUserRoleByUserId(Long userId) {
        if(userId == null){
            return null;
        }
        List<UserRole> userRoles = userRoleMapper.selectUserRoleByUserId(userId);
        if(CollectionUtils.isNotEmpty(userRoles)){
            List<Long> roleIds = userRoles.stream().map(userRole -> userRole.getRoleId()).collect(Collectors.toList());
            RoleDTO dto = new RoleDTO();
            dto.setRoleIds(roleIds);
            dto.setIsDeleted(0);
            return roleMapper.selectRoleList(dto);
        }
        return null;
    }
}
