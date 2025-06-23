package com.restaurant.backend.service.impl;

import com.restaurant.backend.entity.SystemUser;
import com.restaurant.backend.repository.SystemUserRepository;
import com.restaurant.backend.service.SystemUserService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;

@Service
public class SystemUserServiceImpl implements SystemUserService {

    @Autowired
    private SystemUserRepository userRepository;

    @Override
    public SystemUser createUser(SystemUser user) {
        if (user.getPassword() != null) {
            user.setPassword(encodePassword(user.getPassword()));
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Integer id) {
        userRepository.deleteById(id);
    }

    @Override
    public SystemUser updateUser(SystemUser user) {
        if (user.getPassword() != null) {
            user.setPassword(encodePassword(user.getPassword()));
        }
        return userRepository.save(user);
    }

    @Override
    public SystemUser getUserById(Integer id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public SystemUser getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<SystemUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    @Transactional
    public void updateUserStatus(Integer id, Integer status) {
        userRepository.updateStatus(id, status);
    }

    @Override
    @Transactional
    public void updateUserPassword(Integer id, String oldPassword, String newPassword) {
        SystemUser user = getUserById(id);
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        
        if (!validatePassword(oldPassword, user.getPassword())) {
            throw new RuntimeException("Old password is incorrect");
        }
        
        userRepository.updatePassword(id, encodePassword(newPassword));
    }

    @Override
    @Transactional
    public void updateUserRole(Integer id, Integer roleId) {
        userRepository.updateRole(id, roleId);
    }

    @Override
    public boolean validatePassword(String rawPassword, String encodedPassword) {
        return encodePassword(rawPassword).equals(encodedPassword);
    }

    @Override
    public String encodePassword(String rawPassword) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(rawPassword.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Failed to encode password", e);
        }
    }
}