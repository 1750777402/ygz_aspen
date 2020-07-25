package com.ygz.aspen.controller;

import com.ygz.aspen.common.base.PageQueryParam;
import com.ygz.aspen.common.base.PageQueryResult;
import com.ygz.aspen.common.base.ResponseModel;
import com.ygz.aspen.model.sys.Menu;
import com.ygz.aspen.param.sys.MenuDTO;
import com.ygz.aspen.service.sys.MenuService;
import com.ygz.aspen.vo.system.res.MenuInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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

}
