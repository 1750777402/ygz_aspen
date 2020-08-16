package com.ygz.aspen.service.sys.impl;

import com.google.common.collect.Lists;
import com.ygz.aspen.common.base.PageQueryParam;
import com.ygz.aspen.common.base.PageQueryResult;
import com.ygz.aspen.common.utils.TimeUtil;
import com.ygz.aspen.dao.sys.MenuMapper;
import com.ygz.aspen.dao.sys.RoleMenuMapper;
import com.ygz.aspen.model.sys.Menu;
import com.ygz.aspen.model.sys.RoleMenu;
import com.ygz.aspen.param.sys.MenuDTO;
import com.ygz.aspen.service.sys.MenuService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private RoleMenuMapper roleMenuMapper;

    @Override
    public PageQueryResult<Menu> selectMenuList(MenuDTO dto, PageQueryParam page) {
        if(page == null){
            return new PageQueryResult<>();
        }
        int count = menuMapper.countMenu(dto);
        List<Menu> menus = null;
        if(count > 0){
            dto.setPageIndex(page.getStart());
            dto.setPageSize(page.getPageSize());
            dto.setOrderByClause(" sort ");
            menus = menuMapper.selectMenuList(dto);
        }
        return new PageQueryResult(menus, count, page.getPageIndex(), page.getPageSize());
    }

    @Override
    public Boolean addMenu(Menu menu) {
        if(menu == null){
            return false;
        }
        menu.setCreated(TimeUtil.getInstance().secondNow());
        menu.setUpdated(menu.getCreated());
        menu.setIsDeleted(0);
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
            dto.setHidden(1);
            return menuMapper.selectMenuList(dto);
        }
        return null;
    }

    @Override
    public List<Menu> getAllMenu() {
        MenuDTO dto = new MenuDTO();
        dto.setIsDeleted(0);
        dto.setOrderByClause(" sort ");
        return menuMapper.selectMenuList(dto);
    }

    @Override
    public boolean delRoleMenu(Long roleId) {
        if(roleId == null){
            return false;
        }
        int res = roleMenuMapper.delRoleMenu(roleId);
        if(res > 0){
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean updateRoleMenu(Long roleId, List<Long> menuIds) {
        if(roleId == null || CollectionUtils.isEmpty(menuIds)){
            return false;
        }
        try {
            List<RoleMenu> roleMenus = roleMenuMapper.selectRoleMenuByRoleIds(Lists.newArrayList(roleId));
            if(CollectionUtils.isNotEmpty(roleMenus)){
                //需要删除的彩带记录的id集合  方便根据id删除角色对应菜单
                List<Long> delIds = new ArrayList<>();
                roleMenus.forEach(roleMenu -> {
                    if(menuIds.contains(roleMenu.getMenuId())){
                        menuIds.remove(roleMenu.getMenuId());
                    }else{
                        delIds.add(roleMenu.getId());
                    }
                });
                boolean delBoolean = true;
                //把老的删除掉
                if(CollectionUtils.isNotEmpty(delIds)){
                    int delRes = roleMenuMapper.batchDelRoleMenuByRoleMenuIds(delIds);
                    log.info("批量删除角色:{}菜单结果:{}", roleId, delRes);
                    if(delRes < 1){
                        delBoolean = false;
                    }
                }
                boolean batchAddBoolean = true;
                //把新的加进去
                if(CollectionUtils.isNotEmpty(menuIds)){
                    int addRes = batchAddRoleMenu(roleId, menuIds);
                    log.info("批量新增用户:{}角色结果:{}", roleId, addRes);
                    if(addRes < 1){
                        batchAddBoolean = false;
                    }
                }
                return delBoolean && batchAddBoolean;
            }else{
                int res = batchAddRoleMenu(roleId, menuIds);
                log.info("批量新增用户:{}角色结果:{}", roleId, res);
                if(res > 0){
                    return true;
                }
            }
        } catch (Exception e) {
            log.error("更新角色菜单出现异常:{}", e);
            throw e;
        }
        return false;
    }

    private int batchAddRoleMenu(Long roleId, List<Long> menuIds){
        List<RoleMenu> roleMenuList = new ArrayList<>();
        menuIds.forEach(menuId -> {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenu.setCreated(TimeUtil.getInstance().secondNow());
            roleMenu.setUpdated(roleMenu.getCreated());
            roleMenu.setIsDeleted(0);
            roleMenuList.add(roleMenu);
        });
        return roleMenuMapper.batchAddRoleMenu(roleMenuList);
    }

}
