package com.ygz.aspen.service.sys;

import com.ygz.aspen.model.sys.Menu;

import java.util.List;

public interface MenuService {

    List<Menu> selectMenuList(Menu menu);

    Boolean addMenu(Menu menu);
}
