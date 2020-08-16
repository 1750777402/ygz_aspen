package com.ygz.aspen.vo.system.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class MenuCascaderVO {

    @ApiModelProperty("菜单名称")
    private String label;

    @ApiModelProperty("菜单Id")
    private Long value;

    @ApiModelProperty("是否叶子节点 是:true 不是:false")
    private Boolean leaf;

}
