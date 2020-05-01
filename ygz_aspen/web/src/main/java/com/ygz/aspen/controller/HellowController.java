package com.ygz.aspen.controller;

import com.ygz.aspen.annotation.AccessLimit;
import com.ygz.aspen.annotation.Pass;
import com.ygz.aspen.service.RedisService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class HellowController {

    @Autowired
    private RedisService redisService;

    @GetMapping("/")
    public String getCache(){
        return redisService.get("ygz_aspen");
    }

    @Pass
    @GetMapping("/test1")
    public String test1(){
        return "test1";
    }

    @RequiresPermissions("user:test")
    @GetMapping("/test2")
    public String test2(){
        return "test2";
    }

    @AccessLimit(perSecond=0.3,timeOut = 5000)
    @GetMapping("/test3")
    public String test3(){
        return "test3";
    }

}
