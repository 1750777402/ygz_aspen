package com.ygz.aspen.vo.system.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@ApiModel
@Data
public class UserMenuVO {

    @ApiModelProperty("菜单id")
    private Long id;

    @ApiModelProperty("菜单排序")
    private Integer sort;

    @ApiModelProperty("父组件id")
    private Long parentId;

    @ApiModelProperty("重定向路径，当设置 noRedirect 的时候该路由在面包屑导航中不可被点击")
    private String redirect;

    @ApiModelProperty("组件地址 /system")
    private String path;

    @ApiModelProperty("菜单名称")
    private String name;

    @ApiModelProperty("组件路径 组件路径 例:(@/views/system/system/center)或(Layout)")
    private String component;

    @ApiModelProperty("是否显示")
    private Boolean hidden;

    @ApiModelProperty("组件meat信息")
    private MenuMeatVO meta;

    @ApiModelProperty("当你一个路由下面的 children 声明的路由大于1个时，自动会变成嵌套的模式--如组件页面,只有一个时，会将那个子路由当做根路由显示在侧边栏")
    private Boolean alwaysShow;

    @ApiModelProperty("子组件列表")
    private List<UserMenuVO> children;

}