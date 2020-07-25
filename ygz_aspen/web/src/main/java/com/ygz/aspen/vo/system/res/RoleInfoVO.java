package com.ygz.aspen.vo.system.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class RoleInfoVO {

    @ApiModelProperty("角色id")
    private Long roleId;

    @ApiModelProperty("角色名称")
    private String roleName;

    @ApiModelProperty("是否被删除")
    private Integer isDeleted;

    @ApiModelProperty("创建时间")
    private Long created;

    @ApiModelProperty("修改时间")
    private Long updated;


}
