package com.ygz.aspen.service.sys.impl;

import com.ygz.aspen.common.base.PageQueryParam;
import com.ygz.aspen.common.base.PageQueryResult;
import com.ygz.aspen.common.utils.TimeUtil;
import com.ygz.aspen.dao.UserMapper;
import com.ygz.aspen.model.sys.User;
import com.ygz.aspen.param.sys.UserDTO;
import com.ygz.aspen.service.sys.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    public User getUserByUname(String uname) {
        if(StringUtils.isEmpty(uname)){
            return null;
        }
        return userMapper.getUserByUname(uname);
    }

    @Override
    public int addUser(User user) {
        if(user == null){
            return 0;
        }
        user.setCreated(TimeUtil.getInstance().secondNow());
        user.setIsDeleted(0);
        user.setUpdated(user.getCreated());
        return userMapper.addUser(user);
    }

    @Override
    public PageQueryResult<User> selectUserList(UserDTO userDTO, PageQueryParam page) {
        if(userDTO == null){
            return new PageQueryResult<>();
        }
        userDTO.setPageIndex(page.getStart());
        List<User> users = null;
        int count = userMapper.countUser(userDTO);
        if(count > 0){
            users = userMapper.selectUserList(userDTO);
        }
        return new PageQueryResult<>(users, count, page.getPageIndex(), page.getPageSize());
    }

    @Override
    public boolean delUser(Long userId) {
        if(userId == null){
            return false;
        }
        int delRes = userMapper.delUser(userId);
        if(delRes > 0){
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUser(User user) {
        if(user == null || user.getUserId() == null){
            return false;
        }
        int i = userMapper.updateUser(user);
        if(i > 0){
            return true;
        }
        return false;
    }

}
