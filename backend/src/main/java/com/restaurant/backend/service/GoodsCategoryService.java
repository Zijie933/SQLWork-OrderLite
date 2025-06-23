package com.restaurant.backend.service;

import com.restaurant.backend.entity.GoodsCategory;

import java.util.List;

public interface GoodsCategoryService {
    GoodsCategory createCategory(GoodsCategory category);
    
    void deleteCategory(String name);
    
    GoodsCategory updateCategory(String oldName, GoodsCategory category);
    
    List<GoodsCategory> getAllCategories();
    
    List<GoodsCategory> getVisibleCategories();
    
    void toggleCategoryStatus(String name);
    
    GoodsCategory getCategoryByName(String name);
}