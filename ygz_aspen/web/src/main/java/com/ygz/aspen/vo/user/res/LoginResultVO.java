package com.ygz.aspen.vo.user.res;

import lombok.Data;

@Data
public class LoginResultVO {

    private String token;

    private String uname;

    private Long userId;

    private String unick;

}
