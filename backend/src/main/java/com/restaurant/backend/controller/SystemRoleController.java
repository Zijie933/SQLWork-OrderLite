package com.restaurant.backend.controller;

import com.restaurant.backend.entity.SystemRole;
import com.restaurant.backend.entity.SystemRolePermission;
import com.restaurant.backend.service.SystemRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/system/roles")
public class SystemRoleController {

    @Autowired
    private SystemRoleService roleService;

    @PostMapping
    public ResponseEntity<SystemRole> createRole(@RequestBody SystemRole role) {
        return ResponseEntity.ok(roleService.createRole(role));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRole(@PathVariable Integer id) {
        roleService.deleteRole(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<SystemRole> updateRole(@PathVariable Integer id, 
                                               @RequestBody SystemRole role) {
        role.setId(id);
        return ResponseEntity.ok(roleService.updateRole(role));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SystemRole> getRole(@PathVariable Integer id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<SystemRole> getRoleByName(@PathVariable String name) {
        return ResponseEntity.ok(roleService.getRoleByName(name));
    }

    @GetMapping
    public ResponseEntity<List<SystemRole>> getAllRoles() {
        return ResponseEntity.ok(roleService.getAllRoles());
    }

    @PostMapping("/{roleId}/permissions")
    public ResponseEntity<Void> addPermission(@PathVariable Integer roleId, 
                                            @RequestParam String permission) {
        roleService.addPermission(roleId, permission);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{roleId}/permissions")
    public ResponseEntity<Void> removePermission(@PathVariable Integer roleId, 
                                               @RequestParam String permission) {
        roleService.removePermission(roleId, permission);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{roleId}/permissions")
    public ResponseEntity<List<SystemRolePermission>> getRolePermissions(
            @PathVariable Integer roleId) {
        return ResponseEntity.ok(roleService.getRolePermissions(roleId));
    }

    @PutMapping("/{roleId}/permissions")
    public ResponseEntity<Void> updateRolePermissions(@PathVariable Integer roleId, 
                                                    @RequestBody List<String> permissions) {
        roleService.updateRolePermissions(roleId, permissions);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{roleId}/has-permission")
    public ResponseEntity<Boolean> hasPermission(@PathVariable Integer roleId, 
                                               @RequestParam String permission) {
        return ResponseEntity.ok(roleService.hasPermission(roleId, permission));
    }
}