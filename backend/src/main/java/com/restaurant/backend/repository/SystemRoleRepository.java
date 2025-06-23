package com.restaurant.backend.repository;

import com.restaurant.backend.entity.SystemRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SystemRoleRepository extends JpaRepository<SystemRole, Integer> {
    SystemRole findByName(String name);
    
    boolean existsByName(String name);
}