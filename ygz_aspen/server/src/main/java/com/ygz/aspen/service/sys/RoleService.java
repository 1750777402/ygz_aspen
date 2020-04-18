package com.ygz.aspen.service.sys;

import com.ygz.aspen.model.sys.Role;
import com.ygz.aspen.param.sys.RoleDTO;

import java.util.List;

public interface RoleService {

    List<Role> selectRole(RoleDTO role);

    Boolean addRole(Role role);

    Role getRoleById(Long roleId);

    List<Role> selectUserRoleByUserId(Long userId);

}
