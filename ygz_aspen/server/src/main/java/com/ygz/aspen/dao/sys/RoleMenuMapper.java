package com.ygz.aspen.dao.sys;

import com.ygz.aspen.model.sys.RoleMenu;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RoleMenuMapper {

    RoleMenu selectRoleMenuById(@Param("id")Long id);

    int addRoleMenu(RoleMenu roleMenu);

}
