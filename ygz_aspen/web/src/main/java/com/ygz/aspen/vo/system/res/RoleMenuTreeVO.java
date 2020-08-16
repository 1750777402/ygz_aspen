package com.ygz.aspen.vo.system.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class RoleMenuTreeVO {

    @ApiModelProperty("所有的菜单id树")
    private List<MenuTreeVO> treeVOS;

    @ApiModelProperty("角色已拥有的菜单id")
    private List<Long> roleMenuIds;

}
