package com.ygz.aspen.model.sys;

import com.ygz.aspen.model.BaseModel;
import lombok.Data;

/**
 * 菜单
 * @author zx
 */
@Data
public class Menu extends BaseModel {

    /**
     * 菜单id
     */
    private Long munId;

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

    /**
     * 菜单排序
     */
    private Integer menuSort;

    /**
     * 菜单图标
     */
    private String menuIcon;

}
