package com.ygz.aspen.controller;

import com.ygz.aspen.annotation.Pass;
import com.ygz.aspen.config.shiro.JWTUtil;
import com.ygz.aspen.model.sys.User;
import com.ygz.aspen.service.sys.UserService;
import com.ygz.aspen.vo.ResponseModel;
import com.ygz.aspen.vo.ResultMsgEnum;
import com.ygz.aspen.vo.user.req.DoLoginVO;
import com.ygz.aspen.vo.user.res.LoginResultVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class LoginController {

    @Autowired
    private UserService userService;

    @Pass
    @PostMapping("/login")
    public ResponseModel<LoginResultVO> login(@RequestBody DoLoginVO loginVO){
        if(loginVO == null || StringUtils.isEmpty(loginVO.getUname())
                || StringUtils.isEmpty(loginVO.getPassword()) || StringUtils.isEmpty(loginVO.getVerificationCode())){
            return new ResponseModel<>(ResultMsgEnum.PARAM_ERROR);
        }
        User user = userService.getUserByUname(loginVO.getUname());
        if(user != null){
            if(user.getPassword().equals(loginVO.getPassword())){
                String token = JWTUtil.sign(user.getUname(), user.getPassword());
                LoginResultVO loginResultVO = new LoginResultVO();
                loginResultVO.setAuthorization(token);
                loginResultVO.setUname(user.getUname());
                loginResultVO.setUnick(user.getUnick());
                loginResultVO.setUserId(user.getUserId());
                return new ResponseModel<>(loginResultVO);
            }
        }
        return new ResponseModel<>(ResultMsgEnum.UNAUTHORIZED);
    }


}