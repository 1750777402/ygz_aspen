package com.ygz.aspen.controller;

import com.ygz.aspen.common.base.PageQueryParam;
import com.ygz.aspen.common.base.PageQueryResult;
import com.ygz.aspen.common.base.ResponseModel;
import com.ygz.aspen.model.sys.Role;
import com.ygz.aspen.model.sys.User;
import com.ygz.aspen.param.sys.RoleDTO;
import com.ygz.aspen.param.sys.UserDTO;
import com.ygz.aspen.service.sys.RoleService;
import com.ygz.aspen.vo.system.res.RoleInfoVO;
import com.ygz.aspen.vo.system.res.UserInfoVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @GetMapping("/list")
    @RequiresRoles("admin")
    public ResponseModel<PageQueryResult<RoleInfoVO>> list(@RequestParam(value = "roleName",required = false) String roleName,
                                                           @RequestParam(value = "isDeleted",required = false) Integer isDeleted,
                                                           @RequestParam("pageIndex") Integer pageIndex,
                                                           @RequestParam("pageSize") Integer pageSize){
        List<RoleInfoVO> userList = new ArrayList<>();
        RoleDTO roleDTO = new RoleDTO();
        if(StringUtils.isNotEmpty(roleName)){
            roleDTO.setRoleName(roleName);
        }
        if(isDeleted != null){
            roleDTO.setIsDeleted(isDeleted);
        }
        PageQueryResult<Role> rolePageQueryResult = roleService.selectRole(roleDTO, new PageQueryParam(pageIndex, pageSize));
        if(rolePageQueryResult.isNotEmpty()){
            rolePageQueryResult.getDataList().forEach(role -> userList.add(toRoleInfoVO(role)));
        }
        return new ResponseModel<>(new PageQueryResult<>(userList, rolePageQueryResult.getTotal(), pageIndex, pageSize));
    }

    private RoleInfoVO toRoleInfoVO(Role role) {
        RoleInfoVO roleInfoVO = new RoleInfoVO();
        BeanUtils.copyProperties(role, roleInfoVO);
        return roleInfoVO;
    }


}
