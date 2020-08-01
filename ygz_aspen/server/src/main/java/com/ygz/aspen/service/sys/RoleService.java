package com.ygz.aspen.service.sys;

import com.ygz.aspen.common.base.PageQueryParam;
import com.ygz.aspen.common.base.PageQueryResult;
import com.ygz.aspen.model.sys.Role;
import com.ygz.aspen.param.sys.RoleDTO;

import java.util.List;

public interface RoleService {

    PageQueryResult<Role> selectRole(RoleDTO role, PageQueryParam page);

    Boolean addRole(Role role);

    Role getRoleById(Long roleId);

    List<Role> selectUserRoleByUserId(Long userId);

    boolean addUserRole(long userId, List<Long> roleIds);
}
