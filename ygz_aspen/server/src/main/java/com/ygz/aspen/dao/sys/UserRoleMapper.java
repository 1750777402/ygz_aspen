package com.ygz.aspen.dao.sys;

import com.ygz.aspen.model.sys.UserRole;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserRoleMapper {

    List<UserRole> selectUserRoleByUserId(@Param("userId")Long userId);

    int addUserRole(UserRole roleMenu);

}
