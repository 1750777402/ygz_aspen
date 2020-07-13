package com.ygz.aspen.model.sys;

import com.ygz.aspen.model.BaseModel;
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
    private String username;

    /**
     * 用户昵称
     */
    private String usernick;

    /**
     * 用户密码
     */
    private String password;

    /**
     * 用户头像
     */
    private String avatar;

    /**
     * 手机号
     */
    private String phone;

}
