package com.ygz.aspen.vo.system.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel
public class MenuInfoVO {

    @ApiModelProperty("菜单id")
    private Long menuId;

    @ApiModelProperty("菜单名称")
    private String menuName;

    @ApiModelProperty("菜单图标")
    private String icon;

    @ApiModelProperty("菜单排序")
    private Integer sort;

    @ApiModelProperty("菜单链接地址")
    private String path;

    @ApiModelProperty("菜单组件路径")
    private String component;

    @ApiModelProperty("是否显示  0-不显示  1-显示")
    private Integer hidden;

    @ApiModelProperty("菜单父级id")
    private Long parentId;

    @ApiModelProperty("是否有下级菜单")
    private Boolean hasChildren = true;

}
