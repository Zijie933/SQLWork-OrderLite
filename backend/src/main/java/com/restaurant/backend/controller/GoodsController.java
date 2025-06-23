package com.restaurant.backend.controller;

import com.restaurant.backend.entity.Goods;
import com.restaurant.backend.entity.GoodsProperty;
import com.restaurant.backend.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

@RestController
@RequestMapping("/goods")
public class GoodsController {

    @Autowired
    private GoodsService goodsService;

    @GetMapping
    public ResponseEntity<Page<Goods>> getAllGoods(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size);
        return ResponseEntity.ok(goodsService.getAllGoods(pageRequest));
    }

    @PostMapping
    public ResponseEntity<Goods> createGoods(@RequestBody Goods goods) {
        return ResponseEntity.ok(goodsService.createGoods(goods));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGoods(@PathVariable Integer id) {
        goodsService.deleteGoods(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Goods> updateGoods(@PathVariable Integer id, @RequestBody Goods goods) {
        goods.setId(id);
        return ResponseEntity.ok(goodsService.updateGoods(goods));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Goods> getGoods(@PathVariable Integer id) {
        return ResponseEntity.ok(goodsService.getGoodsById(id));
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<List<Goods>> getGoodsByCategory(@PathVariable String categoryName) {
        return ResponseEntity.ok(goodsService.getGoodsByCategory(categoryName));
    }

    @PutMapping("/{id}/sell-status")
    public ResponseEntity<Void> toggleSellStatus(@PathVariable Integer id) {
        goodsService.toggleSellStatus(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/price")
    public ResponseEntity<Void> updatePrice(@PathVariable Integer id, @RequestParam Integer price) {
        goodsService.updateGoodsPrice(id, price);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/image")
    public ResponseEntity<Void> updateImage(@PathVariable Integer id, @RequestParam String imageUrl) {
        goodsService.updateGoodsImage(id, imageUrl);
        return ResponseEntity.ok().build();
    }

    // 商品属性相关接口
    @PostMapping("/{goodsId}/properties")
    public ResponseEntity<GoodsProperty> addProperty(@PathVariable Integer goodsId, 
                                                   @RequestBody GoodsProperty property) {
        property.setGoodsId(goodsId);
        return ResponseEntity.ok(goodsService.addGoodsProperty(property));
    }

    @DeleteMapping("/properties/{propertyId}")
    public ResponseEntity<Void> deleteProperty(@PathVariable Integer propertyId) {
        goodsService.deleteGoodsProperty(propertyId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{goodsId}/properties")
    public ResponseEntity<List<GoodsProperty>> getProperties(@PathVariable Integer goodsId) {
        return ResponseEntity.ok(goodsService.getGoodsProperties(goodsId));
    }

    @PutMapping("/properties/{propertyId}/default")
    public ResponseEntity<Void> setDefaultProperty(@PathVariable Integer propertyId,
                                                 @RequestParam GoodsProperty.PropertyCategory category) {
        goodsService.setDefaultProperty(propertyId, category);
        return ResponseEntity.ok().build();
    }
}