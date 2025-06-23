package com.restaurant.backend.repository;

import com.restaurant.backend.entity.GoodsProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoodsPropertyRepository extends JpaRepository<GoodsProperty, Integer> {
    List<GoodsProperty> findByGoodsId(Integer goodsId);
    
    List<GoodsProperty> findByGoodsIdAndCategory(Integer goodsId, GoodsProperty.PropertyCategory category);
    
    @Modifying
    @Query("UPDATE GoodsProperty g SET g.isDefault = false WHERE g.goodsId = ?1 AND g.category = ?2")
    void resetDefaultStatusOfSameGoodsProperty(Integer goodsId, GoodsProperty.PropertyCategory category);
    
    @Modifying
    @Query("UPDATE GoodsProperty g SET g.isDefault = false")
    void resetAllDefaultStatus();
    
    void deleteByGoodsId(Integer goodsId);
}