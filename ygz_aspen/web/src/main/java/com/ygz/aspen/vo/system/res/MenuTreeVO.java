package com.ygz.aspen.vo.system.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
public class MenuTreeVO {

    @ApiModelProperty("菜单id")
    private Long id;

    @ApiModelProperty("菜单名称")
    private String label;

    @ApiModelProperty("下级菜单")
    private List<MenuTreeVO> children;

    @ApiModelProperty("如果菜单被隐藏 则无法选择(是否显示  0-不显示:true  1-显示:false)")
    private Boolean disabled;

}
