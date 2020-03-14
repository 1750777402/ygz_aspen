package com.ygz.aspen.web.controller;

import com.ygz.aspen.common.utils.DateUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class HellowController {

    @GetMapping("/")
    public String hellow(){
        return DateUtil.getToday();
    }

}
