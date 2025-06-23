package com.restaurant.backend.controller;

import com.restaurant.backend.entity.GoodsCategory;
import com.restaurant.backend.service.GoodsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/goods-categories")
public class GoodsCategoryController {

    @Autowired
    private GoodsCategoryService categoryService;

    @PostMapping
    public ResponseEntity<GoodsCategory> createCategory(@RequestBody GoodsCategory category) {
        return ResponseEntity.ok(categoryService.createCategory(category));
    }

    @DeleteMapping("/{name}")
    public ResponseEntity<Void> deleteCategory(@PathVariable String name) {
        categoryService.deleteCategory(name);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{oldName}")
    public ResponseEntity<GoodsCategory> updateCategory(
            @PathVariable String oldName,
            @RequestBody GoodsCategory category) {
        return ResponseEntity.ok(categoryService.updateCategory(oldName, category));
    }

    @GetMapping
    public ResponseEntity<List<GoodsCategory>> getAllCategories() {
        return ResponseEntity.ok(categoryService.getAllCategories());
    }

    @GetMapping("/visible")
    public ResponseEntity<List<GoodsCategory>> getVisibleCategories() {
        return ResponseEntity.ok(categoryService.getVisibleCategories());
    }

    @PutMapping("/{name}/show-status")
    public ResponseEntity<Void> toggleShowStatus(@PathVariable String name) {
        categoryService.toggleCategoryStatus(name);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<GoodsCategory> getCategoryByName(@PathVariable String name) {
        return ResponseEntity.ok(categoryService.getCategoryByName(name));
    }
}