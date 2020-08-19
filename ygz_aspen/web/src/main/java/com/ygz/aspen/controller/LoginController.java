package com.ygz.aspen.controller;

import com.ygz.aspen.annotation.Pass;
import com.ygz.aspen.config.shiro.JWTUtil;
import com.ygz.aspen.context.AspenContextHolder;
import com.ygz.aspen.model.sys.User;
import com.ygz.aspen.service.sys.UserService;
import com.ygz.aspen.common.base.ResponseModel;
import com.ygz.aspen.common.base.ResultMsgEnum;
import com.ygz.aspen.vo.system.req.DoLoginVO;
import com.ygz.aspen.vo.system.res.LoginResultVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private UserService userService;

    @Pass
    @PostMapping("/login")
    public ResponseModel<LoginResultVO> login(@RequestBody DoLoginVO loginVO){
        if(loginVO == null || StringUtils.isEmpty(loginVO.getUsername())
                || StringUtils.isEmpty(loginVO.getPassword())){
            return new ResponseModel<>(ResultMsgEnum.PARAM_ERROR);
        }
        User user = userService.getUserByUname(loginVO.getUsername());
        if(user != null){
            if(user.getPassword().equals(loginVO.getPassword())){
                String token = JWTUtil.sign(user.getUsername(), user.getPassword());
                LoginResultVO loginResultVO = new LoginResultVO();
                loginResultVO.setToken(token);
                loginResultVO.setUname(user.getUsername());
                loginResultVO.setUnick(user.getUsernick());
                loginResultVO.setUserId(user.getUserId());
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


}
