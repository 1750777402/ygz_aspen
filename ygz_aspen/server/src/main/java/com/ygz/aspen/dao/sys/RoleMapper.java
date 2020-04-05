package com.ygz.aspen.dao.sys;

import com.ygz.aspen.model.sys.Role;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RoleMapper {

    Role selectRoleById(@Param("roleId")Long roleId);

    int addRole(Role role);

}
