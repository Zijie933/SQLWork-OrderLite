package com.restaurant.backend.service;

import com.restaurant.backend.entity.Goods;
import com.restaurant.backend.entity.GoodsProperty;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface GoodsService {
    Page<Goods> getAllGoods(Pageable pageable);
    Goods createGoods(Goods goods);
    
    void deleteGoods(Integer id);
    
    Goods updateGoods(Goods goods);
    
    Goods getGoodsById(Integer id);
    
    List<Goods> getGoodsByCategory(String categoryName);
    
    void toggleSellStatus(Integer id);
    
    void updateGoodsPrice(Integer id, Integer price);
    
    void updateGoodsImage(Integer id, String imageUrl);
    
    void updateGoodsCategory(String oldCategory, String newCategory);
    
    // 商品属性相关
    GoodsProperty addGoodsProperty(GoodsProperty property);
    
    void deleteGoodsProperty(Integer propertyId);
    
    List<GoodsProperty> getGoodsProperties(Integer goodsId);
    
    void setDefaultProperty(Integer propertyId, GoodsProperty.PropertyCategory category);
}