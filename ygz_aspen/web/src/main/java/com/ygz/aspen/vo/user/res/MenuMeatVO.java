package com.ygz.aspen.vo.user.res;

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

    public MenuMeatVO() {
    }

    public MenuMeatVO(String title, String icon) {
        this.title = title;
        this.icon = icon;
    }
}
