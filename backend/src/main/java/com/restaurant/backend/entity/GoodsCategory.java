package com.restaurant.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "goods_category")
public class GoodsCategory {
    @Id
    @Column(nullable = false)
    private String name;

    @Column(name = "display_order", nullable = false)
    private Integer displayOrder;

    @Column(name = "show_status", nullable = false)
    private Boolean showStatus;
}