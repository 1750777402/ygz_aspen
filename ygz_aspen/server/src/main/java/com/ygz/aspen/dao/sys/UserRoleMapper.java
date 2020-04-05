package com.ygz.aspen.dao.sys;

import com.ygz.aspen.model.sys.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface UserRoleMapper {

    UserRole selectUserRoleById(@Param("id")Long id);

    int addUserRole(UserRole roleMenu);

}
