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
        userMenuVO.setSort(1);
        userMenuVO.setName("商品中心");
        userMenuVO.setPath("/item");
        userMenuVO.setRedirect("/item/list");
        userMenuVO.setComponent("Layout");
        userMenuVO.setMeta(new MenuMeatVO("商品中心", "el-icon-s-goods"));
        List<UserMenuVO> menuChildrenList = new ArrayList<>();
        UserMenuVO menuChildren = new UserMenuVO();
        menuChildren.setPath("/list");
        menuChildren.setSort(2);
        menuChildren.setName("商品列表");
        menuChildren.setMeta(new MenuMeatVO("商品列表","el-icon-s-goods"));
        menuChildren.setComponent("item/list/index");
        menuChildrenList.add(menuChildren);
        userMenuVO.setChildrenList(menuChildrenList);
        userMenuVOList.add(userMenuVO);
        return userMenuVOList;
    }

}
