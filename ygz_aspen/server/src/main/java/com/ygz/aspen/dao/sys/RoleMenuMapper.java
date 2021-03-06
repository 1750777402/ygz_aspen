package com.ygz.aspen.dao.sys;

import com.ygz.aspen.model.sys.RoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface RoleMenuMapper {

    RoleMenu selectRoleMenuById(@Param("id")Long id);

    List<RoleMenu> selectRoleMenuByRoleIds(@Param("roleIds") List<Long> roleId);

    int addRoleMenu(RoleMenu roleMenu);

    int delRoleMenu(@Param("roleId") Long roleId);

    int batchAddRoleMenu(@Param("roleMenuList") List<RoleMenu> roleMenuList);

    int batchDelRoleMenuByRoleMenuIds(@Param("roleMenuIds") List<Long> delIds);
}
