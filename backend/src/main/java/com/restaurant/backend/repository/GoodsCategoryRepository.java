package com.restaurant.backend.repository;

import com.restaurant.backend.entity.GoodsCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoodsCategoryRepository extends JpaRepository<GoodsCategory, String> {
    List<GoodsCategory> findAllByOrderByDisplayOrderAsc();
    
    List<GoodsCategory> findByShowStatusTrueOrderByDisplayOrderAsc();
    
    @Modifying
    @Query("UPDATE GoodsCategory g SET g.showStatus = ?2 WHERE g.name = ?1")
    void updateShowStatus(String name, Boolean showStatus);
    
    @Modifying
    @Query("UPDATE GoodsCategory g SET g.name = ?2, g.displayOrder = ?3 WHERE g.name = ?1")
    void updateCategory(String oldName, String newName, Integer displayOrder);
}