package com.ygz.aspen.vo.system.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class UserInfoVO {

    @ApiModelProperty("头像")
    private String avatar;

    @ApiModelProperty("用户id")
    private Long userId;

    @ApiModelProperty("用户名")
    private String username;

    @ApiModelProperty("用户昵称")
    private String usernick;

    @ApiModelProperty("角色code列表")
    private List<String> roles;

    @ApiModelProperty("用户手机")
    private String phone;

    @ApiModelProperty("用户角色id")
    private Long roleId;

    @ApiModelProperty("用户角色名称")
    private String roleName;

    @ApiModelProperty("用户状态")
    private Integer isDeleted;

}
