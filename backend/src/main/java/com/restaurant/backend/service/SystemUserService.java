package com.restaurant.backend.service;

import com.restaurant.backend.entity.SystemUser;

import java.util.List;

public interface SystemUserService {
    SystemUser createUser(SystemUser user);
    
    void deleteUser(Integer id);
    
    SystemUser updateUser(SystemUser user);
    
    SystemUser getUserById(Integer id);
    
    SystemUser getUserByUsername(String username);
    
    List<SystemUser> getAllUsers();
    
    void updateUserStatus(Integer id, Integer status);
    
    void updateUserPassword(Integer id, String oldPassword, String newPassword);
    
    void updateUserRole(Integer id, Integer roleId);
    
    boolean validatePassword(String rawPassword, String encodedPassword);
    
    String encodePassword(String rawPassword);
}