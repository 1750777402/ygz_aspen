package com.ygz.aspen.controller;

import com.ygz.aspen.annotation.AccessLimit;
import com.ygz.aspen.annotation.Pass;
import com.ygz.aspen.context.AspenContextHolder;
import com.ygz.aspen.model.sys.User;
import com.ygz.aspen.service.RedisService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class HellowController {

    @Autowired
    private RedisService redisService;

    @Pass
    @GetMapping("/")
    public String getCache(){
        return redisService.get("ygz_aspen");
    }

    @Pass
    @GetMapping("/test1")
    public String test1(){
        User user = AspenContextHolder.get().getUser();
        AspenContextHolder.get().getUser();
        return user.getUsername();
    }

    @RequiresRoles("admin")
    @GetMapping("/test2")
    public String test2(){
        User user = AspenContextHolder.get().getUser();
        AspenContextHolder.get().getUser();
        return user.getUsername();
    }

    @AccessLimit(perSecond=0.3)
    @GetMapping("/test3")
    public String test3(){
        return "test3";
    }

}
