package com.ygz.aspen.vo.system.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class UserAddVO {

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户昵称")
    private String usernick;

    @ApiModelProperty("手机号")
    private String phone;

    @ApiModelProperty("用户所属角色id")
    private List<Long> roleIds;

}
