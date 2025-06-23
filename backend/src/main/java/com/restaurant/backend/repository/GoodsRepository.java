package com.restaurant.backend.repository;

import com.restaurant.backend.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoodsRepository extends JpaRepository<Goods, Integer> {
    List<Goods> findByGoodsCategoryNameOrderByDisplayOrderAsc(String categoryName);
    
    @Modifying
    @Query("UPDATE Goods g SET g.isSell = ?2 WHERE g.id = ?1")
    void updateSellStatus(Integer id, Boolean isSell);
    
    @Modifying
    @Query("UPDATE Goods g SET g.defaultPrice = ?2 WHERE g.id = ?1")
    void updateDefaultPrice(Integer id, Integer defaultPrice);
    
    @Modifying
    @Query("UPDATE Goods g SET g.image = ?2 WHERE g.id = ?1")
    void updateImage(Integer id, String image);
    
    @Modifying
    @Query("UPDATE Goods g SET g.goodsCategoryName = ?2 WHERE g.goodsCategoryName = ?1")
    void updateCategory(String oldName, String newName);
}