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
    private Long menuId;

    /**
     * 菜单名称
     */
    private String name;

    /**
     * 父级菜单id
     */
    private Long parentId;

    /**
     * 菜单排序
     */
    private Integer sort;

    /**
     * 菜单图标
     */
    private String icon;

    /**
     * 是否显示  0-不显示  1-显示
     */
    private Integer hidden;

    /**
     * 组件地址 /system
     */
    private String path;

    /**
     * 组件路径 @/views/system/system/center
     */
    private String component;


}
