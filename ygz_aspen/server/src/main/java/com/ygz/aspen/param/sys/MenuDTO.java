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
     * 菜单url
     */
    private String menuUrl;

    /**
     * 菜单权限
     */
    private String menuPermission;

    /**
     * 菜单等级
     */
    private Integer menuLevel;

    /**
     * 父级菜单id
     */
    private Long menuParentId;

    private List<Long> menuIds;

}
