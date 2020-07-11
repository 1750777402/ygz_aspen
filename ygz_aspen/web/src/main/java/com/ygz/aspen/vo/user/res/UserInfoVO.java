package com.ygz.aspen.vo.user.res;

import lombok.Data;

import java.util.List;

@Data
public class UserInfoVO {

    private String avatar;

    private Long id;

    private String username;

    private List<String> roles;

}
