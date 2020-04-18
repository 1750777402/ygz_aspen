package com.ygz.aspen.dao.sys;

import com.ygz.aspen.model.sys.Role;
import com.ygz.aspen.param.sys.RoleDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMapper {

    Role getRoleById(@Param("roleId") Long roleId);

    int addRole(Role role);

    List<Role> selectRoleList(RoleDTO roleDTO);

}
