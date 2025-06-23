package com.restaurant.backend.controller;

import com.restaurant.backend.entity.SystemUser;
import com.restaurant.backend.service.SystemUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/users")
public class SystemUserController {

    @Autowired
    private SystemUserService userService;

    @PostMapping
    public ResponseEntity<SystemUser> createUser(@RequestBody SystemUser user) {
        return ResponseEntity.ok(userService.createUser(user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id) {
        userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SystemUser> updateUser(@PathVariable Integer id, 
                                               @RequestBody SystemUser user) {
        user.setId(id);
        return ResponseEntity.ok(userService.updateUser(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SystemUser> getUser(@PathVariable Integer id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<SystemUser> getUserByUsername(@PathVariable String username) {
        return ResponseEntity.ok(userService.getUserByUsername(username));
    }

    @GetMapping
    public ResponseEntity<List<SystemUser>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<Void> updateUserStatus(@PathVariable Integer id, 
                                               @RequestParam Integer status) {
        userService.updateUserStatus(id, status);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/password")
    public ResponseEntity<Void> updateUserPassword(@PathVariable Integer id, 
                                                 @RequestParam String oldPassword,
                                                 @RequestParam String newPassword) {
        userService.updateUserPassword(id, oldPassword, newPassword);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/role")
    public ResponseEntity<Void> updateUserRole(@PathVariable Integer id, 
                                             @RequestParam Integer roleId) {
        userService.updateUserRole(id, roleId);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/validate-password")
    public ResponseEntity<Boolean> validatePassword(@RequestParam String username, 
                                                  @RequestParam String password) {
        SystemUser user = userService.getUserByUsername(username);
        if (user == null) {
            return ResponseEntity.ok(false);
        }
        return ResponseEntity.ok(userService.validatePassword(password, user.getPassword()));
    }
}