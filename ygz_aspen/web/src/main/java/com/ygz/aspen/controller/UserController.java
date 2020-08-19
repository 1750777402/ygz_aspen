package com.ygz.aspen.controller;

import com.google.common.collect.Lists;
import com.ygz.aspen.common.base.PageQueryParam;
import com.ygz.aspen.common.base.PageQueryResult;
import com.ygz.aspen.common.base.ResultMsgEnum;
import com.ygz.aspen.common.utils.AspenCollectionUtil;
import com.ygz.aspen.context.AspenContextHolder;
import com.ygz.aspen.model.sys.Menu;
import com.ygz.aspen.model.sys.Role;
import com.ygz.aspen.model.sys.User;
import com.ygz.aspen.model.sys.UserRole;
import com.ygz.aspen.param.sys.UserDTO;
import com.ygz.aspen.service.sys.MenuService;
import com.ygz.aspen.service.sys.RoleService;
import com.ygz.aspen.service.sys.UserService;
import com.ygz.aspen.common.base.ResponseModel;
import com.ygz.aspen.vo.system.req.UserAddVO;
import com.ygz.aspen.vo.system.res.MenuMeatVO;
import com.ygz.aspen.vo.system.res.UserInfoVO;
import com.ygz.aspen.vo.system.res.UserMenuVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/user")
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
        vo.setUserId(user.getUserId());
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
    public ResponseModel<PageQueryResult<UserInfoVO>> list(@RequestParam(value = "username",required = false) String username,
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
        PageQueryResult<User> userPageQueryResult = userService.selectUserList(userDTO, new PageQueryParam(pageIndex, pageSize));
        if(userPageQueryResult.isNotEmpty()){
            List<User> users = userPageQueryResult.getDataList();
            //本次查询的userId集合
            List<Long> userIds = users.stream().map(User::getUserId).collect(Collectors.toList());
            //本次查询的用户所拥有的角色map 这里不想做连表查询  所以复杂点
            Map<Long, List<UserRole>> userRoleMap = roleService.getUserRoleMapByUserIds(userIds);
            //本次查询的用户所有的角色id set
            Set<Long> roleIds = new HashSet<>();
            for (Map.Entry<Long, List<UserRole>> entry : userRoleMap.entrySet()) {
                List<UserRole> userRoles = entry.getValue();
                roleIds.addAll(userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toSet()));
            }
            //本次查询的用户所有的角色id set 对应的角色map
            Map<Long, Role> roleMap = roleService.getRoleListByRoleIds(new ArrayList<>(roleIds));
            users.forEach(user -> userList.add(toUserInfoVO(user, userRoleMap, roleMap)));
        }
        return new ResponseModel<>(new PageQueryResult<>(userList, userPageQueryResult.getTotal(), pageIndex, pageSize));
    }

    private UserInfoVO toUserInfoVO(User user, Map<Long, List<UserRole>> userRoleMap, Map<Long, Role> roleMap){
        if(user == null){
            return null;
        }
        UserInfoVO userInfoVO = new UserInfoVO();
        userInfoVO.setUsername(user.getUsername());
        userInfoVO.setUserId(user.getUserId());
        userInfoVO.setAvatar(user.getAvatar());
        userInfoVO.setPhone(user.getPhone());
        userInfoVO.setUsernick(user.getUsernick());
        userInfoVO.setIsDeleted(user.getIsDeleted());
        List<UserRole> userRoles = userRoleMap.get(user.getUserId());
        if(CollectionUtils.isNotEmpty(userRoles)){
            List<String> roleNames = new ArrayList<>();
            List<Long> roleIds = new ArrayList<>();
            userRoles.forEach(userRole -> {
                Role role = roleMap.get(userRole.getRoleId());
                if(role != null){
                    roleNames.add(role.getRoleName());
                }
                roleIds.add(userRole.getRoleId());
            });
            userInfoVO.setRoleIds(roleIds);
            userInfoVO.setRoleName(AspenCollectionUtil.jointList(roleNames, ","));
        }
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
        menuVO.setName(menu.getMenuName());
        menuVO.setPath(menu.getPath());
        menuVO.setComponent(menu.getComponent());
        menuVO.setHidden(menu.getHidden() == 0);
        menuVO.setParentId(menu.getParentId());
        menuVO.setMeta(new MenuMeatVO(menu.getMenuName(), menu.getIcon()));
        if("Layout".equals(menu.getComponent())){
            menuVO.setRedirect("noredirect");
        }
        menuVO.setAlwaysShow(true);
        return menuVO;
    }

    @PostMapping("/save")
    @RequiresRoles("admin")
    public ResponseModel<Boolean> add(@RequestBody UserAddVO userAddVO){
        if(userAddVO == null || StringUtils.isBlank(userAddVO.getUsernick())
                || StringUtils.isBlank(userAddVO.getPhone()) || StringUtils.isBlank(userAddVO.getUsername())){
            return new ResponseModel<>(ResultMsgEnum.PARAM_ERROR);
        }
        if(userAddVO.getUserId() == null){
            User user = new User();
            user.setAvatar("1");
            user.setPhone(userAddVO.getPhone());
            user.setUsernick(userAddVO.getUsernick());
            user.setUsername(userAddVO.getUsername());
            user.setPassword("123456");
            int i = userService.addUser(user);
            if(i > 0){
                List<Long> roleIds = userAddVO.getRoleIds();
                if(CollectionUtils.isNotEmpty(roleIds)){
                    roleService.addUserRole(user.getUserId(), roleIds);
                }
                return new ResponseModel<>(true);
            }
        }else{
            User user = new User();
            user.setUserId(userAddVO.getUserId());
            user.setUsername(userAddVO.getUsername());
            user.setPhone(userAddVO.getPhone());
            boolean updateRes = userService.updateUser(user);
            if(updateRes){
                List<Long> roleIds = userAddVO.getRoleIds();
                boolean updateUserRoleRes = roleService.updateUserRole(userAddVO.getUserId(), roleIds);
                return new ResponseModel<>(updateUserRoleRes);
            }

        }
        return new ResponseModel<>(ResultMsgEnum.ERROR);
    }

    @GetMapping("del")
    @RequiresRoles("admin")
    public ResponseModel<Boolean> del(@RequestParam("userId") Long userId){
        if(userService.delUser(userId)){
            return new ResponseModel<>(true);
        }
        return new ResponseModel<>(ResultMsgEnum.ERROR);
    }

}
