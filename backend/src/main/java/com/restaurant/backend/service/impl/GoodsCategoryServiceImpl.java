package com.restaurant.backend.service.impl;

import com.restaurant.backend.entity.GoodsCategory;
import com.restaurant.backend.repository.GoodsCategoryRepository;
import com.restaurant.backend.service.GoodsCategoryService;
import com.restaurant.backend.service.GoodsService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsCategoryServiceImpl implements GoodsCategoryService {

    @Autowired
    private GoodsCategoryRepository categoryRepository;

    @Autowired
    private GoodsService goodsService;

    @Override
    public GoodsCategory createCategory(GoodsCategory category) {
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void deleteCategory(String name) {
        categoryRepository.deleteById(name);
    }

    @Override
    @Transactional
    public GoodsCategory updateCategory(String oldName, GoodsCategory category) {
        categoryRepository.updateCategory(oldName, category.getName(), category.getDisplayOrder());
        goodsService.updateGoodsCategory(oldName, category.getName());
        return category;
    }

    @Override
    public List<GoodsCategory> getAllCategories() {
        return categoryRepository.findAllByOrderByDisplayOrderAsc();
    }

    @Override
    public List<GoodsCategory> getVisibleCategories() {
        return categoryRepository.findByShowStatusTrueOrderByDisplayOrderAsc();
    }

    @Override
    @Transactional
    public void toggleCategoryStatus(String name) {
        GoodsCategory category = getCategoryByName(name);
        if (category != null) {
            categoryRepository.updateShowStatus(name, !category.getShowStatus());
        }
    }

    @Override
    public GoodsCategory getCategoryByName(String name) {
        return categoryRepository.findById(name).orElse(null);
    }
}