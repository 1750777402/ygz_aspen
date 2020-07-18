package com.ygz.aspen.controller;

import com.ygz.aspen.context.AspenContextHolder;
import com.ygz.aspen.model.sys.Menu;
import com.ygz.aspen.model.sys.Role;
import com.ygz.aspen.model.sys.User;
import com.ygz.aspen.param.sys.UserDTO;
import com.ygz.aspen.service.sys.MenuService;
import com.ygz.aspen.service.sys.RoleService;
import com.ygz.aspen.service.sys.UserService;
import com.ygz.aspen.vo.ResponseModel;
import com.ygz.aspen.vo.user.res.MenuMeatVO;
import com.ygz.aspen.vo.user.res.UserInfoVO;
import com.ygz.aspen.vo.user.res.UserMenuVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @GetMapping("/info")
    public ResponseModel<UserInfoVO> info(){
        User user = AspenContextHolder.get().getUser();

        UserInfoVO vo = new UserInfoVO();
        vo.setAvatar(user.getAvatar());
        vo.setId(user.getUserId());
        vo.setUsername(user.getUsername());
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
        List<Role> roles = roleService.selectUserRoleByUserId(user.getUserId());
        if(CollectionUtils.isEmpty(roles)){
            return new ResponseModel<>(null);
        }
        List<Long> roleIds = roles.stream().map(Role::getRoleId).collect(Collectors.toList());
        List<Menu> menus = menuService.selectMenuByRoleIds(roleIds);
        return new ResponseModel<>(assembleMenuVO(menus));
    }

    @GetMapping("/list")
    @RequiresRoles("admin")
    public ResponseModel<List<UserInfoVO>> list(@RequestParam(value = "username",required = false) String username,
                                                @RequestParam(value = "isDeleted",required = false) Integer isDeleted,
                                                @RequestParam("pageIndex") Integer pageIndex,
                                                @RequestParam("pageSize") Integer pageSize){
        List<UserInfoVO> userList = new ArrayList<>();
        UserDTO userDTO = new UserDTO();
        if(StringUtils.isNotEmpty(username)){
            userDTO.setUsername(username);
        }
        if(isDeleted != null){
            userDTO.setIsDeleted(isDeleted);
        }
        userDTO.setPageIndex(pageIndex);
        userDTO.setPageSize(pageSize);
        List<User> users = userService.selectUserList(userDTO);
        if(CollectionUtils.isNotEmpty(users)){
//            List<Long> userIds = users.stream().map(User::getUserId).collect(Collectors.toList());
//            roleService.
            users.forEach(user -> userList.add(toUserInfoVO(user)));
        }
        return new ResponseModel<>(userList);
    }

    private UserInfoVO toUserInfoVO(User user){
        if(user == null){
            return null;
        }
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setUsername(user.getUsername());
        userInfoVO.setId(user.getUserId());
        userInfoVO.setAvatar(user.getAvatar());
        userInfoVO.setPhone(user.getPhone());
        userInfoVO.setUsernick(user.getUsernick());
        userInfoVO.setRoleName("系统管理员");
        userInfoVO.setRoleId(1L);
        userInfoVO.setIsDeleted(user.getIsDeleted() == 0);
        return userInfoVO;
    }

    private List<UserMenuVO> assembleMenuVO(List<Menu> menus){
        if(CollectionUtils.isEmpty(menus)){
            return null;
        }
        List<UserMenuVO> menuVOList = new ArrayList<>();
        Map<Long, UserMenuVO> menuMap = new HashMap<>();
        menus.forEach(menu -> {
            UserMenuVO userMenuVO = getUserMenuVO(menu);
            if(userMenuVO != null){
                menuMap.put(userMenuVO.getId(), userMenuVO);
            }
        });
        menuMap.forEach((menuId, userMenuVO) -> {
            if(userMenuVO.getParentId() == null || userMenuVO.getParentId() == 0){
                menuVOList.add(userMenuVO);
            }else{
                UserMenuVO userMenuVO1 = menuMap.get(userMenuVO.getParentId());
                if(userMenuVO1 != null){
                    List<UserMenuVO> children = userMenuVO1.getChildren();
                    if(CollectionUtils.isNotEmpty(children)){
                        children.add(userMenuVO);
                    }else{
                        List<UserMenuVO> children1 = new ArrayList<>();
                        children1.add(userMenuVO);
                        userMenuVO1.setChildren(children1);
                    }
                }else{
                    menuVOList.add(userMenuVO);
                }
            }
        });
        return menuVOList;
    }

    private UserMenuVO getUserMenuVO(Menu menu){
        if(menu == null){
            return null;
        }
        UserMenuVO menuVO = new UserMenuVO();
        menuVO.setId(menu.getMenuId());
        menuVO.setSort(menu.getSort());
        menuVO.setName(menu.getName());
        menuVO.setPath(menu.getPath());
        menuVO.setComponent(menu.getComponent());
        menuVO.setHidden(menu.getHidden() == 0);
        menuVO.setParentId(menu.getParentId());
        menuVO.setMeta(new MenuMeatVO(menu.getName(), menu.getIcon()));
        if("Layout".equals(menu.getComponent())){
            menuVO.setRedirect("noredirect");
        }
        menuVO.setAlwaysShow(true);
        return menuVO;
    }

//    private List<UserMenuVO> getUserMenuVO(){
//        List<UserMenuVO> userMenuVOList = new ArrayList<>();
//
//        List<UserMenuVO> menuChildrenList = new ArrayList<>();
//
//        UserMenuVO sysMenu = new UserMenuVO();
//        sysMenu.setPath("/system");
//        sysMenu.setSort(1);
//        sysMenu.setRedirect("noredirect");
//        sysMenu.setName("系统管理");
//        sysMenu.setMeta(new MenuMeatVO("系统管理","system"));
//        sysMenu.setComponent("Layout");
//
//        UserMenuVO userMenu = new UserMenuVO();
//        userMenu.setPath("users");
//        userMenu.setSort(2);
//        userMenu.setName("用户管理");
//        userMenu.setMeta(new MenuMeatVO("用户管理","user"));
//        userMenu.setComponent("system/user/index");
//        menuChildrenList.add(userMenu);
//
//        UserMenuVO menuMenu = new UserMenuVO();
//        menuMenu.setPath("menus");
//        menuMenu.setSort(3);
//        menuMenu.setName("菜单管理");
//        menuMenu.setMeta(new MenuMeatVO("菜单管理","caidan"));
//        menuMenu.setComponent("system/menu/index");
//        menuChildrenList.add(menuMenu);
//
//        UserMenuVO roleMenu = new UserMenuVO();
//        roleMenu.setPath("roles");
//        roleMenu.setSort(4);
//        roleMenu.setName("角色管理");
//        roleMenu.setMeta(new MenuMeatVO("角色管理","role"));
//        roleMenu.setComponent("system/role/index");
//        menuChildrenList.add(roleMenu);
//
//        sysMenu.setChildren(menuChildrenList);
//        userMenuVOList.add(sysMenu);
//        return userMenuVOList;
//    }

}
