package com.ygz.aspen.model.sys;

import com.ygz.aspen.model.BaseModel;
import lombok.Data;

/**
 * 用户角色关联表
 * @author zx
 */
@Data
public class UserRole extends BaseModel {

    private Long id;

    /**
     * 角色id
     */
    private Long roleId;

    /**
     * 用户id
     */
    private Long userId;

}
