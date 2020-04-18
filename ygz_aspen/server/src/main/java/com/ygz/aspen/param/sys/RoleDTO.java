package com.ygz.aspen.param.sys;

import com.ygz.aspen.param.BaseQueryParam;
import lombok.Data;

import java.util.List;

@Data
public class RoleDTO extends BaseQueryParam {

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

    private List<Long> roleIds;

}
