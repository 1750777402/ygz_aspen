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
    private String uname;

    /**
     * 用户昵称
     */
    private String unick;


    private List<Long> userIds;


}
