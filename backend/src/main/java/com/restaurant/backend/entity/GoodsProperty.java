package com.restaurant.backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "goods_property")
public class GoodsProperty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "goods_id", nullable = false)
    private Integer goodsId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PropertyCategory category;

    @Column(name = "property_option", nullable = false)
    private Integer propertyOption;

    @Column(name = "is_default", nullable = false)
    private Boolean isDefault;

    @Column(name = "rebase_price")
    private Integer rebasePrice;

    @Column(name = "extra_price")
    private Integer extraPrice;

    public enum PropertyCategory {
        SIZE,
        TEMPERATURE,
        SUGAR,
        ICE
    }
}