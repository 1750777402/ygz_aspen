package com.ygz.aspen.service.sys;

import com.ygz.aspen.common.base.PageQueryParam;
import com.ygz.aspen.common.base.PageQueryResult;
import com.ygz.aspen.common.base.ResultMsgEnum;
import com.ygz.aspen.model.sys.Menu;
import com.ygz.aspen.param.sys.MenuDTO;

import java.util.List;
import java.util.Map;

public interface MenuService {

    PageQueryResult<Menu> selectMenuList(MenuDTO dto, PageQueryParam page);

    Boolean addMenu(Menu menu);

    Menu getMenuById(Long menuId);

    List<Menu> selectMenuByRoleIds(List<Long> roleIds);

    List<Menu> getAllMenu();

    boolean delRoleMenu(Long roleId);

    boolean updateRoleMenu(Long roleId, List<Long> menuIds);

    List<Menu> getMenuNext(Long parentMenuId);

    /**
     * 批量获取是否有下一级菜单
     * @param menuIds
     * @return
     */
    Map<Long, Boolean> getMenuIsExistLowerLevel(List<Long> menuIds);

    Boolean updateMenuById(Menu menu);

    Boolean delMenuById(Long menuId);
}
