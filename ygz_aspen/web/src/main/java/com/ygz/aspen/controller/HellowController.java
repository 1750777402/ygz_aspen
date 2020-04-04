package com.ygz.aspen.controller;

import com.ygz.aspen.service.RedisService;
import com.ygz.aspen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class HellowController {

    @Autowired
    private UserService userService;

    @Autowired
    private RedisService redisService;

    @GetMapping("/")
    public String getCache(@RequestParam("key") String key){
        return redisService.get("ygz_aspen");
    }

}
