package com.restaurant.backend.repository;

import com.restaurant.backend.entity.SystemRolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SystemRolePermissionRepository extends JpaRepository<SystemRolePermission, Integer> {
    List<SystemRolePermission> findByRoleId(Integer roleId);
    
    void deleteByRoleId(Integer roleId);
    
    boolean existsByRoleIdAndPermission(Integer roleId, String permission);
}