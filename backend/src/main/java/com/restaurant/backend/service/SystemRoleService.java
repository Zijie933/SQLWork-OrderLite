package com.restaurant.backend.service;

import com.restaurant.backend.entity.SystemRole;
import com.restaurant.backend.entity.SystemRolePermission;

import java.util.List;

public interface SystemRoleService {
    SystemRole createRole(SystemRole role);
    
    void deleteRole(Integer id);
    
    SystemRole updateRole(SystemRole role);
    
    SystemRole getRoleById(Integer id);
    
    SystemRole getRoleByName(String name);
    
    List<SystemRole> getAllRoles();
    
    // 权限相关
    void addPermission(Integer roleId, String permission);
    
    void removePermission(Integer roleId, String permission);
    
    List<SystemRolePermission> getRolePermissions(Integer roleId);
    
    void updateRolePermissions(Integer roleId, List<String> permissions);
    
    boolean hasPermission(Integer roleId, String permission);
}