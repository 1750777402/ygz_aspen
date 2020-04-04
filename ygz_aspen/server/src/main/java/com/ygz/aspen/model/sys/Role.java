package com.ygz.aspen.model.sys;

import com.ygz.aspen.model.BaseModel;
import lombok.Data;

/**
 * 角色表
 * @author zx
 */
@Data
public class Role extends BaseModel {

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 角色名称
     */
    private String roleName;

    /**
     * 角色类型
     */
    private Integer roleType;

}
