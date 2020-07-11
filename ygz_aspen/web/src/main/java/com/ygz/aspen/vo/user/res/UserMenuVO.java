package com.ygz.aspen.vo.user.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel
public class UserMenuVO {

    @ApiModelProperty("菜单排序")
    private Integer sort;

    @ApiModelProperty("组件")
    private String redirect;

    @ApiModelProperty("组件地址 /user")
    private String path;

    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("组件路径 @/views/system/user/center")
    private String component;

    @ApiModelProperty("是否显示")
    private Boolean alwaysShow;

    @ApiModelProperty("组件meat信息")
    private MenuMeatVO meta;

    @ApiModelProperty("子组件列表")
    private List<UserMenuVO> childrenList;

}
