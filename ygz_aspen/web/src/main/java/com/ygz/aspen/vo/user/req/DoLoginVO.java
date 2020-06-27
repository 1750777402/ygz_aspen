package com.ygz.aspen.vo.user.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class DoLoginVO {

    @ApiModelProperty("用户名")
    private String uname;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("验证码")
    private String verificationCode;

    @ApiModelProperty("是否需要返回菜单信息 0:不需要， 1:需要")
    private Integer menuFlag;

}
