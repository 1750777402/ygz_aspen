package com.ygz.aspen.service.sys;

import com.ygz.aspen.model.sys.Menu;
import com.ygz.aspen.param.sys.MenuDTO;

import java.util.List;

public interface MenuService {

    List<Menu> selectMenuList(MenuDTO dto);

    Boolean addMenu(Menu menu);

    Menu getMenuById(Long menuId);

    List<Menu> selectMenuByRoleIds(List<Long> roleIds);
}
