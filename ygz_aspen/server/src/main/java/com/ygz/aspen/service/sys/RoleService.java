package com.ygz.aspen.service.sys;

import com.ygz.aspen.model.sys.Role;

import java.util.List;

public interface RoleService {

    List<Role> selectRole(Role role);

    Boolean addRole(Role role);

}
