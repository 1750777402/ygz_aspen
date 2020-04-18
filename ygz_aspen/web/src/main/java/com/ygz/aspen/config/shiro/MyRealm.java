package com.ygz.aspen.config.shiro;


import com.ygz.aspen.common.constant.Constant;
import com.ygz.aspen.model.sys.Menu;
import com.ygz.aspen.model.sys.Role;
import com.ygz.aspen.model.sys.User;
import com.ygz.aspen.param.sys.MenuDTO;
import com.ygz.aspen.service.sys.MenuService;
import com.ygz.aspen.service.sys.RoleService;
import com.ygz.aspen.service.sys.UserService;
import com.ygz.aspen.utils.SpringContextBeanUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 自定义Realm
 * @author ygz
 */
public class MyRealm extends AuthorizingRealm {

    private UserService userService;

    private MenuService menuService;

    private RoleService roleService;

    /**
     * 重写此方法，不然Shiro会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (menuService == null) {
            this.menuService = SpringContextBeanUtil.getBean(MenuService.class);
        }
        if (roleService == null) {
            this.roleService = SpringContextBeanUtil.getBean(RoleService.class);
        }

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        List<String> permissions = new ArrayList<>();

        String userNo = JWTUtil.getUserNo(principals.toString());
        User user = userService.getUserByUname(userNo);
        List<Role> roles = roleService.selectUserRoleByUserId(user.getUserId());
        if(CollectionUtils.isNotEmpty(roles)){
          //添加控制角色级别的权限
//        Set<String> roleNameSet = new HashSet<>();
//        roleNameSet.add(role.getRoleName());
//        simpleAuthorizationInfo.addRoles(roleNameSet);

            //控制菜单级别按钮  类中用@RequiresPermissions("user:list") 对应数据库中code字段来控制controller
            List<Long> roleIds = roles.stream().map(role -> role.getRoleId()).collect(Collectors.toList());
            List<Menu> menuList = menuService.selectMenuByRoleIds(roleIds);
            if(CollectionUtils.isNotEmpty(menuList)){
                menuList.forEach(menu -> {
                    if (StringUtils.isNoneEmpty(menu.getMenuPermission())) {
                        permissions.add(menu.getMenuPermission());
                    }
                });
            }
        }
        Set<String> permission = new HashSet<>(permissions);
        simpleAuthorizationInfo.addStringPermissions(permission);
        return simpleAuthorizationInfo;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken auth) throws UnauthorizedException {
        if (userService == null) {
            this.userService = SpringContextBeanUtil.getBean(UserService.class);
        }
        String token = (String) auth.getCredentials();
        if(Constant.isPass){
            return new SimpleAuthenticationInfo(token, token, this.getName());
        }
        // 解密获得username，用于和数据库进行对比
        String userNo = JWTUtil.getUserNo(token);
        if (StringUtils.isEmpty(userNo)) {
            throw new UnauthorizedException("token invalid");
        }
        User user = userService.getUserByUname(userNo);
        if (user == null) {
            throw new UnauthorizedException("User didn't existed!");
        }
        if (! JWTUtil.verify(token, userNo, user.getPassword())) {
            throw new UnauthorizedException("Username or password error");
        }
        return new SimpleAuthenticationInfo(token, token, this.getName());
    }
}