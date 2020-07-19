package com.ygz.aspen.service.sys;

import com.ygz.aspen.common.base.PageQueryResult;
import com.ygz.aspen.model.sys.User;
import com.ygz.aspen.param.sys.UserDTO;

import java.util.List;

public interface UserService {

    User getUserById(Long userId);

    User getUserByUname(String uname);

    int addUser(User user);

    PageQueryResult<User> selectUserList(UserDTO user);

}
