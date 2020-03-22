package com.ygz.aspen.dao;

import com.ygz.aspen.model.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> selectUserById(@Param("userId") Long userId);

}
