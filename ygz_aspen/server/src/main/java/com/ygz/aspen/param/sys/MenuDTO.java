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
    private String name;

    /**
     * 父级菜单id
     */
    private Long parentId;

    private List<Long> menuIds;

    private List<Long> parentIds;

}
