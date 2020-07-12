package com.ygz.aspen.controller;

import com.ygz.aspen.context.AspenContextHolder;
import com.ygz.aspen.model.sys.Role;
import com.ygz.aspen.model.sys.User;
import com.ygz.aspen.service.sys.RoleService;
import com.ygz.aspen.vo.ResponseModel;
import com.ygz.aspen.vo.user.res.MenuMeatVO;
import com.ygz.aspen.vo.user.res.UserInfoVO;
import com.ygz.aspen.vo.user.res.UserMenuVO;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/info")
    public ResponseModel<UserInfoVO> info(){
        User user = AspenContextHolder.get().getUser();

        UserInfoVO vo = new UserInfoVO();
        vo.setAvatar(user.getAvatar());
        vo.setId(user.getUserId());
        vo.setUsername(user.getUname());
        List<String> roleArr = new ArrayList<>();
        List<Role> roles = roleService.selectUserRoleByUserId(user.getUserId());
        if(CollectionUtils.isNotEmpty(roles)){
            roles.forEach(role -> roleArr.add(role.getRoleCode()));
        }
        vo.setRoles(roleArr);
        return new ResponseModel<>(vo);
    }

    @GetMapping("/menu")
    public ResponseModel<List<UserMenuVO>> menu(){
        User user = AspenContextHolder.get().getUser();
        return new ResponseModel<>(getUserMenuVO());
    }

    private List<UserMenuVO> getUserMenuVO(){
        List<UserMenuVO> userMenuVOList = new ArrayList<>();
        UserMenuVO userMenuVO = new UserMenuVO();
        userMenuVO.setSort(0);
        userMenuVO.setName("系统管理");
        userMenuVO.setPath("/system");
        userMenuVO.setRedirect("noredirect");
        userMenuVO.setComponent("Layout");
        userMenuVO.setAlwaysShow(true);
        userMenuVO.setMeta(new MenuMeatVO("系统管理", "system"));
        List<UserMenuVO> menuChildrenList = new ArrayList<>();

        UserMenuVO userMenu = new UserMenuVO();
        userMenu.setPath("users");
        userMenu.setSort(2);
        userMenu.setName("用户管理");
        userMenu.setMeta(new MenuMeatVO("用户管理","user"));
        userMenu.setComponent("system/user/index");
        menuChildrenList.add(userMenu);

        UserMenuVO menuMenu = new UserMenuVO();
        menuMenu.setPath("menus");
        menuMenu.setSort(3);
        menuMenu.setName("菜单管理");
        menuMenu.setMeta(new MenuMeatVO("菜单管理","caidan"));
        menuMenu.setComponent("system/menu/index");
        menuChildrenList.add(menuMenu);

        UserMenuVO roleMenu = new UserMenuVO();
        roleMenu.setPath("roles");
        roleMenu.setSort(4);
        roleMenu.setName("角色管理");
        roleMenu.setMeta(new MenuMeatVO("角色管理","role"));
        roleMenu.setComponent("system/role/index");
        menuChildrenList.add(roleMenu);

        userMenuVO.setChildren(menuChildrenList);
        userMenuVOList.add(userMenuVO);
        return userMenuVOList;
    }

}
