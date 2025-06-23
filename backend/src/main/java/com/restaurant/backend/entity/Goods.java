package com.restaurant.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "goods")
public class Goods {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "goods_category_name", nullable = false)
    private String goodsCategoryName;

    @Column(nullable = false)
    private String name;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(name = "default_price")
    private Integer defaultPrice;

    @Column(name = "is_sell")
    private Boolean isSell;

    private String image;

    private String description;

    @Column(name = "price", nullable = false)
    private Integer price;
}