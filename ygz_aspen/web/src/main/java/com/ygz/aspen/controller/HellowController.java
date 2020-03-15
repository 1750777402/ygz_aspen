package com.ygz.aspen.controller;

import com.ygz.aspen.model.User;
import com.ygz.aspen.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
public class HellowController {

    @Autowired
    private UserService userService;

    @GetMapping("/getUserById")
    public String getUserById(@RequestParam("id") Long id){
        User userById = userService.getUserById(id);
        return userById == null ? "" : userById.getUsername();
    }

}
