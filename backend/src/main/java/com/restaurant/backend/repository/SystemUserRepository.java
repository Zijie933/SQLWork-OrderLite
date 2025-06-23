package com.restaurant.backend.repository;

import com.restaurant.backend.entity.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SystemUserRepository extends JpaRepository<SystemUser, Integer> {
    SystemUser findByUsername(String username);
    
    boolean existsByUsername(String username);
    
    @Modifying
    @Query("UPDATE SystemUser u SET u.status = ?2 WHERE u.id = ?1")
    void updateStatus(Integer id, Integer status);
    
    @Modifying
    @Query("UPDATE SystemUser u SET u.password = ?2 WHERE u.id = ?1")
    void updatePassword(Integer id, String password);
    
    @Modifying
    @Query("UPDATE SystemUser u SET u.roleId = ?2 WHERE u.id = ?1")
    void updateRole(Integer id, Integer roleId);
}