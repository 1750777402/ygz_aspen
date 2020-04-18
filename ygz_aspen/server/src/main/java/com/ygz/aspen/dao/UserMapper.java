package com.ygz.aspen.dao;

import com.ygz.aspen.model.sys.User;
import com.ygz.aspen.param.sys.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    User getUserById(@Param("userId") Long userId);

    int addUser(User user);

    List<User> selectUserList(UserDTO user);
}
