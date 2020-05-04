package com.ygz.aspen.vo.user.req;

import lombok.Data;

@Data
public class DoLoginVO {

    private String uname;

    private String password;

    private String verificationCode;

}
