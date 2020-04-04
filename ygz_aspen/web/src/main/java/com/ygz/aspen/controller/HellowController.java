package com.ygz.aspen.controller;

import com.ygz.aspen.service.RedisService;
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

}
