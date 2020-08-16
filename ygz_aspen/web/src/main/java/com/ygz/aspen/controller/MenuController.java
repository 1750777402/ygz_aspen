package com.ygz.aspen.controller;

import com.google.common.collect.Lists;
import com.ygz.aspen.common.base.PageQueryParam;
import com.ygz.aspen.common.base.PageQueryResult;
import com.ygz.aspen.common.base.ResponseModel;
import com.ygz.aspen.common.base.ResultMsgEnum;
import com.ygz.aspen.model.sys.Menu;
import com.ygz.aspen.param.sys.MenuDTO;
import com.ygz.aspen.service.sys.MenuService;
import com.ygz.aspen.vo.system.res.MenuInfoVO;
import com.ygz.aspen.vo.system.res.MenuTreeVO;
import com.ygz.aspen.vo.system.res.RoleMenuTreeVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @GetMapping("/tree")
    @RequiresRoles("admin")
    public ResponseModel<PageQueryResult<MenuInfoVO>> getMenuList(@RequestParam(value = "menuName", required = false) String menuName,
                                                                  @RequestParam(value = "parentMenuId", required = false) Long parentMenuId,
                                                                  @RequestParam("pageIndex") Integer pageIndex,
                                                                  @RequestParam("pageSize") Integer pageSize){
        if(parentMenuId == null){
            parentMenuId = 0L;
        }
        MenuDTO dto = new MenuDTO();
        if(StringUtils.isNotEmpty(menuName)){
            dto.setName(menuName);
        }
        dto.setParentId(parentMenuId);
        List<MenuInfoVO> menuInfoVOS = new ArrayList<>();
        PageQueryResult<Menu> menuList = menuService.selectMenuList(dto, new PageQueryParam(pageIndex, pageSize));
        if(menuList.isNotEmpty()){
            menuList.getDataList().forEach(menu -> menuInfoVOS.add(toMenuInfoVO(menu)));
        }
        return new ResponseModel<>(new PageQueryResult<>(menuInfoVOS, menuList.getTotal(), pageIndex, pageSize));
    }

    private MenuInfoVO toMenuInfoVO(Menu menu){
        if(menu == null){
            return null;
        }
        MenuInfoVO menuInfoVO = new MenuInfoVO();
        menuInfoVO.setComponent(menu.getComponent());
        menuInfoVO.setHidden(menu.getHidden());
        menuInfoVO.setIcon(menu.getIcon());
        menuInfoVO.setMenuId(menu.getMenuId());
        menuInfoVO.setMenuName(menu.getName());
        menuInfoVO.setParentId(menu.getParentId());
        menuInfoVO.setPath(menu.getPath());
        menuInfoVO.setSort(menu.getSort());
        return menuInfoVO;
    }

    @GetMapping("roleMenuTree")
    @RequiresRoles("admin")
    public ResponseModel<RoleMenuTreeVO> roleMenuTree(@RequestParam("roleId") Long roleId){
        RoleMenuTreeVO vo = new RoleMenuTreeVO();
        List<Menu> allMenu = menuService.getAllMenu();
        List<Menu> menuList = menuService.selectMenuByRoleIds(Lists.newArrayList(roleId));
        //这里获取的是用户拥有的所有菜单 直接返回给前端会导致非叶子节点被选中 需要处理只返回给前端叶子节点(最小一级的菜单)
        List<Long> roleMenuIds = new ArrayList<>();
        if(CollectionUtils.isNotEmpty(menuList)){
            roleMenuIds = menuList.stream().map(Menu::getMenuId).collect(Collectors.toList());
            vo.setRoleMenuIds(roleMenuIds);
        }
        vo.setTreeVOS(getMenuTree(allMenu, 0L, roleMenuIds));
        return new ResponseModel(vo);
    }

    /**
     * 递归获取菜单树
     */
    private List<MenuTreeVO> getMenuTree(List<Menu> menus, Long parentId, List<Long> roleMenuIds){
        if(CollectionUtils.isEmpty(menus)){
            return null;
        }
        List<MenuTreeVO> treeVOS = new ArrayList<>();
        menus.forEach(menu -> {
            if(menu.getParentId().equals(parentId)){
                MenuTreeVO treeVO = toTreeVO(menu);
                List<MenuTreeVO> menuTree = getMenuTree(menus, menu.getMenuId(), roleMenuIds);
                if(CollectionUtils.isNotEmpty(menuTree)){
                    treeVO.setChildren(menuTree);
                    //删除返回给前端的非叶子节点菜单
                    roleMenuIds.remove(menu.getMenuId());
                }else{
                    treeVO.setChildren(null);
                }
                treeVOS.add(treeVO);
            }
        });
        return treeVOS;
    }

    private MenuTreeVO toTreeVO(Menu menu){
        MenuTreeVO treeVO = new MenuTreeVO();
        treeVO.setDisabled(menu.getHidden() == 0);
        treeVO.setId(menu.getMenuId());
        treeVO.setLabel(menu.getName());
        return treeVO;
    }

}
