package com.ygz.aspen.service.sys.impl;

import com.ygz.aspen.dao.sys.MenuMapper;
import com.ygz.aspen.dao.sys.RoleMenuMapper;
import com.ygz.aspen.model.sys.Menu;
import com.ygz.aspen.service.sys.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;


    @Override
    public List<Menu> selectMenuList(Menu menu) {
        return null;
    }

    @Override
    public Boolean addMenu(Menu menu) {
        int i = menuMapper.addMenu(menu);
        if(i == 1){
            return true;
        }
        return false;
    }
}
