package com.ygz.aspen.service.sys.impl;

import com.ygz.aspen.dao.sys.MenuMapper;
import com.ygz.aspen.dao.sys.RoleMenuMapper;
import com.ygz.aspen.model.sys.Menu;
import com.ygz.aspen.model.sys.RoleMenu;
import com.ygz.aspen.param.sys.MenuDTO;
import com.ygz.aspen.service.sys.MenuService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;


    @Override
    public List<Menu> selectMenuList(MenuDTO dto) {
        return menuMapper.selectMenuList(dto);
    }

    @Override
    public Boolean addMenu(Menu menu) {
        int i = menuMapper.addMenu(menu);
        if(i == 1){
            return true;
        }
        return false;
    }

    @Override
    public Menu getMenuById(Long menuId) {
        if(menuId == null){
            return null;
        }
        return menuMapper.selectMenuById(menuId);
    }

    @Override
    public List<Menu> selectMenuByRoleIds(List<Long> roleIds) {
        if(CollectionUtils.isEmpty(roleIds)){
            return null;
        }
        List<RoleMenu> roleMenus = roleMenuMapper.selectRoleMenuByRoleIds(roleIds);
        if(CollectionUtils.isNotEmpty(roleMenus)){
            List<Long> menuIds = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
            MenuDTO dto = new MenuDTO();
            dto.setMenuIds(menuIds);
            dto.setIsDeleted(0);
            return menuMapper.selectMenuList(dto);
        }
        return null;
    }
}
