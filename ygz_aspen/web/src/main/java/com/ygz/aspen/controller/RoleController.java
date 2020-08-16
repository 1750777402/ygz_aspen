package com.ygz.aspen.controller;

import com.ygz.aspen.common.base.PageQueryParam;
import com.ygz.aspen.common.base.PageQueryResult;
import com.ygz.aspen.common.base.ResponseModel;
import com.ygz.aspen.common.base.ResultMsgEnum;
import com.ygz.aspen.model.sys.Role;
import com.ygz.aspen.model.sys.User;
import com.ygz.aspen.param.sys.RoleDTO;
import com.ygz.aspen.param.sys.UserDTO;
import com.ygz.aspen.service.sys.MenuService;
import com.ygz.aspen.service.sys.RoleService;
import com.ygz.aspen.vo.system.res.RoleInfoVO;
import com.ygz.aspen.vo.system.res.UserInfoVO;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

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

    @PostMapping("save")
    @RequiresRoles("admin")
    public ResponseModel<Boolean> saveRole(@RequestBody RoleInfoVO roleInfoVO){
        if(roleInfoVO == null || StringUtils.isBlank(roleInfoVO.getRoleName())){
            return new ResponseModel<>(ResultMsgEnum.PARAM_ERROR);
        }
        if(roleInfoVO.getRoleId() == null){
            if(StringUtils.isBlank(roleInfoVO.getRoleCode())){
                return new ResponseModel<>(ResultMsgEnum.PARAM_ERROR);
            }
            Role role = new Role();
            role.setRoleName(roleInfoVO.getRoleName());
            role.setRoleCode(roleInfoVO.getRoleCode());
            return new ResponseModel<>(roleService.addRole(role));
        }else{
            Role role = new Role();
            role.setRoleName(roleInfoVO.getRoleName());
            role.setRoleCode(roleInfoVO.getRoleCode());
            role.setRoleId(roleInfoVO.getRoleId());
            return new ResponseModel<>(roleService.updateRole(role));
        }
    }

    @GetMapping("/delRole")
    @RequiresRoles("admin")
    public ResponseModel<Boolean> delRole(@RequestParam(value = "roleId") Long roleId){
        return new ResponseModel<>(roleService.delRole(roleId));
    }

    @PostMapping("/saveRoleMenu")
    @RequiresRoles("admin")
    public ResponseModel<Boolean> saveRoleMenu(@RequestBody RoleInfoVO roleInfoVO){
        if(roleInfoVO == null || roleInfoVO.getRoleId() == null){
            return new ResponseModel<>(ResultMsgEnum.PARAM_ERROR);
        }
        Long roleId = roleInfoVO.getRoleId();
        if(CollectionUtils.isNotEmpty(roleInfoVO.getMenuIds())){
            return new ResponseModel(menuService.updateRoleMenu(roleId, roleInfoVO.getMenuIds()));
        }else{
            return new ResponseModel(menuService.delRoleMenu(roleId));
        }
    }

}
