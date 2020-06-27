package com.ygz.aspen.controller;

import com.ygz.aspen.annotation.Pass;
import com.ygz.aspen.config.shiro.JWTUtil;
import com.ygz.aspen.context.AspenContextHolder;
import com.ygz.aspen.model.sys.User;
import com.ygz.aspen.service.sys.UserService;
import com.ygz.aspen.vo.ResponseModel;
import com.ygz.aspen.vo.ResultMsgEnum;
import com.ygz.aspen.vo.user.req.DoLoginVO;
import com.ygz.aspen.vo.user.res.LoginResultVO;
import com.ygz.aspen.vo.user.res.MenuMeatVO;
import com.ygz.aspen.vo.user.res.UserMenuVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/")
public class LoginController {

    @Autowired
    private UserService userService;

    @Pass
    @PostMapping("/login")
    public ResponseModel<LoginResultVO> login(@RequestBody DoLoginVO loginVO){
        if(loginVO == null || StringUtils.isEmpty(loginVO.getUname())
                || StringUtils.isEmpty(loginVO.getPassword())){
            return new ResponseModel<>(ResultMsgEnum.PARAM_ERROR);
        }
        User user = userService.getUserByUname(loginVO.getUname());
        if(user != null){
            if(user.getPassword().equals(loginVO.getPassword())){
                String token = JWTUtil.sign(user.getUname(), user.getPassword());
                LoginResultVO loginResultVO = new LoginResultVO();
                loginResultVO.setToken(token);
                loginResultVO.setUname(user.getUname());
                loginResultVO.setUnick(user.getUnick());
                loginResultVO.setUserId(user.getUserId());
                loginResultVO.setUserMenuVO(getUserMenuVO());
                return new ResponseModel<>(loginResultVO);
            }
        }
        return new ResponseModel<>(ResultMsgEnum.UNAUTHORIZED);
    }


    @PostMapping("/logout")
    public ResponseModel<LoginResultVO> logout(){
        User user = AspenContextHolder.get().getUser();
        return new ResponseModel<>();
    }

    private List<UserMenuVO> getUserMenuVO(){
        List<UserMenuVO> userMenuVOList = new ArrayList<>();
        UserMenuVO userMenuVO = new UserMenuVO();
        userMenuVO.setMenuLevel(1);
        userMenuVO.setName("商品中心");
        userMenuVO.setPath("/item");
        userMenuVO.setRedirect("/item/list");
        userMenuVO.setMeatInfo(new MenuMeatVO("商品中心", "el-icon-s-goods"));
        List<UserMenuVO> menuChildrenList = new ArrayList<>();
        UserMenuVO menuChildren = new UserMenuVO();
        menuChildren.setPath("/list");
        menuChildren.setMenuLevel(2);
        menuChildren.setName("商品列表");
        menuChildren.setMeatInfo(new MenuMeatVO("商品列表","el-icon-s-goods"));
        menuChildren.setComponent("@/views/item/list/index");
        menuChildrenList.add(menuChildren);
        userMenuVO.setChildrenList(menuChildrenList);
        userMenuVOList.add(userMenuVO);
        return userMenuVOList;
    }
}
