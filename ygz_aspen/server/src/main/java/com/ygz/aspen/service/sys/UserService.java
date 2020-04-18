package com.ygz.aspen.service.sys;

import com.ygz.aspen.model.sys.User;
import com.ygz.aspen.param.sys.UserDTO;

import java.util.List;

public interface UserService {

    User getUserById(Long userId);

    int addUser(User user);

    List<User> selectUserList(UserDTO user);

}
