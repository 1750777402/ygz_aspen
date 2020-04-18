package com.ygz.aspen.service.sys.impl;

import com.ygz.aspen.dao.UserMapper;
import com.ygz.aspen.model.sys.User;
import com.ygz.aspen.param.sys.UserDTO;
import com.ygz.aspen.service.sys.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(Long userId) {
        return userMapper.getUserById(userId);
    }

    @Override
    public int addUser(User user) {
        if(user == null){
            return 0;
        }
        return userMapper.addUser(user);
    }

    @Override
    public List<User> selectUserList(UserDTO userDTO) {
        if(userDTO == null){
            return null;
        }
        return userMapper.selectUserList(userDTO);
    }

}
