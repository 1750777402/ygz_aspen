package com.ygz.aspen.vo.system.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel
@Data
public class MenuMeatVO {

    @ApiModelProperty("菜单名称")
    private String title;

    @ApiModelProperty("菜单图标")
    private String icon;

    @ApiModelProperty("是否被 <keep-alive> 缓存")
    private Boolean noCache = false;

    @ApiModelProperty(" 若果设置为true，它则会固定在tags-view中(默认 false)")
    private Boolean affix = false;

    public MenuMeatVO() {
    }

    public MenuMeatVO(String title, String icon) {
        this.title = title;
        this.icon = icon;
    }
}
