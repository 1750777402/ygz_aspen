package com.ygz.aspen.service.sys.impl;

import com.google.common.collect.Lists;
import com.ygz.aspen.common.base.PageQueryParam;
import com.ygz.aspen.common.base.PageQueryResult;
import com.ygz.aspen.common.utils.TimeUtil;
import com.ygz.aspen.dao.sys.RoleMapper;
import com.ygz.aspen.dao.sys.UserRoleMapper;
import com.ygz.aspen.model.sys.Role;
import com.ygz.aspen.model.sys.UserRole;
import com.ygz.aspen.param.sys.RoleDTO;
import com.ygz.aspen.service.sys.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;


    @Override
    public PageQueryResult<Role> selectRole(RoleDTO dto, PageQueryParam page) {
        if(dto == null){
            return new PageQueryResult<>();
        }
        dto.setPageIndex(page.getStart());
        int count = roleMapper.countRole(dto);
        List<Role> roles = null;
        if(count > 0){
            roles = roleMapper.selectRoleList(dto);
        }
        return new PageQueryResult<>(roles, count, page.getPageIndex(), page.getPageSize());
    }

    @Override
    public Boolean addRole(Role role) {
        if(role == null){
            return false;
        }
        role.setIsDeleted(0);
        role.setCreated(TimeUtil.getInstance().secondNow());
        role.setUpdated(role.getCreated());
        int i = roleMapper.addRole(role);
        if(i == 1){
            return true;
        }
        return false;
    }

    @Override
    public Role getRoleById(Long roleId) {
        if(roleId == null){
            return null;
        }
        return roleMapper.getRoleById(roleId);
    }

    @Override
    public List<Role> selectUserRoleByUserId(Long userId) {
        if(userId == null){
            return null;
        }
        List<UserRole> userRoles = userRoleMapper.selectUserRoleByUserId(userId);
        if(CollectionUtils.isNotEmpty(userRoles)){
            List<Long> roleIds = userRoles.stream().map(userRole -> userRole.getRoleId()).collect(Collectors.toList());
            RoleDTO dto = new RoleDTO();
            dto.setRoleIds(roleIds);
            dto.setIsDeleted(0);
            return roleMapper.selectRoleList(dto);
        }
        return null;
    }

    @Override
    public boolean addUserRole(long userId, List<Long> roleIds) {
        if(userId < 1 || CollectionUtils.isEmpty(roleIds)){
            return false;
        }
        roleIds.forEach(roleId -> {
            UserRole userRole = new UserRole();
            userRole.setRoleId(roleId);
            userRole.setUserId(userId);
            userRole.setIsDeleted(0);
            userRole.setCreated(TimeUtil.getInstance().secondNow());
            userRole.setUpdated(userRole.getCreated());
            userRoleMapper.addUserRole(userRole);
        });
        return true;
    }

    @Override
    public Map<Long, List<UserRole>> getUserRoleMapByUserIds(List<Long> userIds) {
        Map<Long, List<UserRole>> map = new HashMap<>();
        List<UserRole> userRoles = userRoleMapper.getUserRoleByUserIds(userIds);
        if(CollectionUtils.isNotEmpty(userRoles)){
            userRoles.forEach(userRole -> {
                List<UserRole> roleIds = map.get(userRole.getUserId());
                if(CollectionUtils.isNotEmpty(roleIds)){
                    roleIds.add(userRole);
                    map.put(userRole.getUserId(), roleIds);
                }else{
                    map.put(userRole.getUserId(), Lists.newArrayList(userRole));
                }
            });
        }
        return map;
    }

    @Override
    public Boolean updateRole(Role role) {
        if(role == null || role.getRoleId() == null){
            return false;
        }
        int i = roleMapper.updateRole(role);
        if(i > 0){
            return true;
        }
        return false;
    }

    @Override
    public Boolean delRole(Long roleId) {
        if(roleId == null){
            return false;
        }
        int i = roleMapper.delRole(roleId);
        if(i > 0){
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean updateUserRole(Long userId, List<Long> roleIds) {
        if(userId == null){
            return false;
        }
        try {
            if(CollectionUtils.isNotEmpty(roleIds)){
                List<UserRole> userRoles = userRoleMapper.selectUserRoleByUserId(userId);
                if(CollectionUtils.isNotEmpty(userRoles)){
                    //需要删除的用户角色记录的id集合  方便根据id删除用户角色
                    List<Long> delIds = new ArrayList<>();
                    userRoles.forEach(userRole -> {
                        if(roleIds.contains(userRole.getRoleId())){
                            roleIds.remove(userRole.getRoleId());
                        }else{
                            delIds.add(userRole.getId());
                        }
                    });
                    //把老的删除掉
                    if(CollectionUtils.isNotEmpty(delIds)){
                        int delRes = userRoleMapper.batchDelUserRole(delIds);
                        log.info("批量删除用户:{}角色结果:{}", userId, delRes);
                    }
                    //把新的加进去
                    if(CollectionUtils.isNotEmpty(roleIds)){
                        int addRes = batchAddUserRole(roleIds, userId);
                        log.info("批量新增用户:{}角色结果:{}", userId, addRes);
                    }
                }else{
                    int addRes = batchAddUserRole(roleIds, userId);
                    log.info("批量新增用户:{}角色结果:{}", userId, addRes);
                }
            }else{
                int delRes = userRoleMapper.delUserRole(userId);
                log.info("批量删除用户:{}角色结果:{}", userId, delRes);
            }
            return true;
        } catch (Exception e) {
            log.error("更新用户角色出现异常:{}", e);
            throw e;
        }
    }

    @Override
    public Map<Long, Role> getRoleListByRoleIds(List<Long> roleIds) {
        Map<Long, Role> roleMap = new HashMap<>();
        if(CollectionUtils.isEmpty(roleIds)){
            return roleMap;
        }
        RoleDTO roleDTO = new RoleDTO();
        roleDTO.setRoleIds(roleIds);
        roleDTO.setIsDeleted(0);
        List<Role> roleList = roleMapper.selectRoleList(roleDTO);
        if(CollectionUtils.isNotEmpty(roleList)){
            roleList.forEach(role -> roleMap.put(role.getRoleId(), role));
        }
        return roleMap;
    }

    private int batchAddUserRole(List<Long> roleIds, Long userId){
        List<UserRole> userRoleList = new ArrayList<>();
        roleIds.forEach(roleId -> {
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(roleId);
            userRole.setIsDeleted(0);
            userRole.setCreated(TimeUtil.getInstance().secondNow());
            userRole.setUpdated(userRole.getCreated());
            userRoleList.add(userRole);
        });
        int res = userRoleMapper.batchAddUserRole(userRoleList);
        return res;
    }
}
