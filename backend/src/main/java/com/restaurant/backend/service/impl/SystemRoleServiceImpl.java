package com.restaurant.backend.service.impl;

import com.restaurant.backend.entity.SystemRole;
import com.restaurant.backend.entity.SystemRolePermission;
import com.restaurant.backend.repository.SystemRolePermissionRepository;
import com.restaurant.backend.repository.SystemRoleRepository;
import com.restaurant.backend.service.SystemRoleService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemRoleServiceImpl implements SystemRoleService {

    @Autowired
    private SystemRoleRepository roleRepository;

    @Autowired
    private SystemRolePermissionRepository permissionRepository;

    @Override
    public SystemRole createRole(SystemRole role) {
        return roleRepository.save(role);
    }

    @Override
    @Transactional
    public void deleteRole(Integer id) {
        permissionRepository.deleteByRoleId(id);
        roleRepository.deleteById(id);
    }

    @Override
    public SystemRole updateRole(SystemRole role) {
        return roleRepository.save(role);
    }

    @Override
    public SystemRole getRoleById(Integer id) {
        return roleRepository.findById(id).orElse(null);
    }

    @Override
    public SystemRole getRoleByName(String name) {
        return roleRepository.findByName(name);
    }

    @Override
    public List<SystemRole> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    @Transactional
    public void addPermission(Integer roleId, String permission) {
        if (!hasPermission(roleId, permission)) {
            SystemRolePermission rolePermission = new SystemRolePermission();
            rolePermission.setRoleId(roleId);
            rolePermission.setPermission(permission);
            permissionRepository.save(rolePermission);
        }
    }

    @Override
    @Transactional
    public void removePermission(Integer roleId, String permission) {
        List<SystemRolePermission> permissions = permissionRepository.findByRoleId(roleId);
        permissions.stream()
                .filter(p -> p.getPermission().equals(permission))
                .forEach(p -> permissionRepository.deleteById(p.getId()));
    }

    @Override
    public List<SystemRolePermission> getRolePermissions(Integer roleId) {
        return permissionRepository.findByRoleId(roleId);
    }

    @Override
    @Transactional
    public void updateRolePermissions(Integer roleId, List<String> permissions) {
        permissionRepository.deleteByRoleId(roleId);
        permissions.forEach(permission -> addPermission(roleId, permission));
    }

    @Override
    public boolean hasPermission(Integer roleId, String permission) {
        return permissionRepository.existsByRoleIdAndPermission(roleId, permission);
    }
}