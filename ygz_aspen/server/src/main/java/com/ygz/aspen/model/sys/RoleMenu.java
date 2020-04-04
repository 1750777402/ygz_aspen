package com.ygz.aspen.model.sys;

import com.ygz.aspen.model.BaseModel;
import lombok.Data;

/**
 * 角色菜单关联表
 * @author zx
 */
@Data
public class RoleMenu extends BaseModel {

    private Long id;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 菜单id
     */
    private Long menuId;

}
