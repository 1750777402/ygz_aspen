package com.ygz.aspen.param.sys;

import com.ygz.aspen.param.BaseQueryParam;
import lombok.Data;

import java.util.List;

@Data
public class UserDTO extends BaseQueryParam {

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
     * 手机号
     */
    private String phone;


    private List<Long> userIds;


}
