package com.ygz.aspen.vo.user.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class LoginResultVO {

    @ApiModelProperty("登陆成功返回的token")
    private String token;

    @ApiModelProperty("用户名")
    private String uname;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("用户昵称")
    private String unick;

    @ApiModelProperty("用户的菜单信息")
    private List<UserMenuVO> userMenuVO;

}
