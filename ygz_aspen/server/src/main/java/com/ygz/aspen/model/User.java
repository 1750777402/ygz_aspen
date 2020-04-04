package com.ygz.aspen.model;

import lombok.Data;

@Data
public class User extends BaseModel {

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户名
     */
    private String uname;

    /**
     * 用户昵称
     */
    private String unick;

    /**
     * 用户密码
     */
    private String password;

}
