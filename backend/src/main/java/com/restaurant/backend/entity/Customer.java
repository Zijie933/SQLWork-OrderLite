package com.restaurant.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @Column(name = "wx_openid")
    private String wxOpenid;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(length = 100)
    private String phone;

    private String address;

    private Integer sex;

    @Column(name = "wx_avatar")
    private String wxAvatar;

    private Integer status;
}