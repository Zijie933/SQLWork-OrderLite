package com.restaurant.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "system_users")
public class SystemUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100, nullable = false)
    private String username;

    @Column(length = 100)
    private String password;

    @Column(name = "role_id")
    private Integer roleId;

    private Integer status;

    private String avatar;
}