package com.ygz.aspen.service.impl;

import com.ygz.aspen.dao.UserMapper;
import com.ygz.aspen.model.User;
import com.ygz.aspen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public User getUserById(Long id) {
        List<User> users = userMapper.selectUserById(id);
        return CollectionUtils.isEmpty(users) ? null : users.get(0);
    }
}
