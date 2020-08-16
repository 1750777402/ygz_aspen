package com.ygz.aspen.param.sys;

import com.ygz.aspen.param.BaseQueryParam;
import lombok.Data;

import java.util.List;

@Data
public class MenuDTO extends BaseQueryParam {

    /**
     * 菜单id
     */
    private Long menuId;

    /**
     * 菜单名称
     */
    private String menuName;

    /**
     * 父级菜单id
     */
    private Long parentId;

    /**
     * 是否显示 0-不显示  1-显示
     */
    private Integer hidden;

    private List<Long> menuIds;

    private List<Long> parentIds;

}
