package com.restaurant.backend.service.impl;

import com.restaurant.backend.entity.Goods;
import com.restaurant.backend.entity.GoodsProperty;
import com.restaurant.backend.repository.GoodsRepository;
import com.restaurant.backend.repository.GoodsPropertyRepository;
import com.restaurant.backend.service.GoodsService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Override
    public Page<Goods> getAllGoods(Pageable pageable) {
        return goodsRepository.findAll(pageable);
    }

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private GoodsPropertyRepository goodsPropertyRepository;

    @Override
    public Goods createGoods(Goods goods) {
        if (goods.getDefaultPrice() != null) {
            goods.setPrice(goods.getDefaultPrice());
        }
        return goodsRepository.save(goods);
    }

    @Override
    public void deleteGoods(Integer id) {
        goodsPropertyRepository.deleteByGoodsId(id);
        goodsRepository.deleteById(id);
    }

    @Override
    public Goods updateGoods(Goods goods) {
        return goodsRepository.save(goods);
    }

    @Override
    public Goods getGoodsById(Integer id) {
        return goodsRepository.findById(id).orElse(null);
    }

    @Override
    public List<Goods> getGoodsByCategory(String categoryName) {
        return goodsRepository.findByGoodsCategoryNameOrderByDisplayOrderAsc(categoryName);
    }

    @Override
    @Transactional
    public void toggleSellStatus(Integer id) {
        Goods goods = getGoodsById(id);
        if (goods != null) {
            goodsRepository.updateSellStatus(id, !goods.getIsSell());
        }
    }

    @Override
    @Transactional
    public void updateGoodsPrice(Integer id, Integer price) {
        Goods goods = getGoodsById(id);
        if (goods != null) {
            goods.setDefaultPrice(price);
            goods.setPrice(price);
            goodsRepository.save(goods);
        }
    }

    @Override
    @Transactional
    public void updateGoodsImage(Integer id, String imageUrl) {
        goodsRepository.updateImage(id, imageUrl);
    }

    @Override
    @Transactional
    public void updateGoodsCategory(String oldCategory, String newCategory) {
        goodsRepository.updateCategory(oldCategory, newCategory);
    }

    @Override
    public GoodsProperty addGoodsProperty(GoodsProperty property) {
        if (property.getIsDefault()) {
            goodsPropertyRepository.resetDefaultStatusOfSameGoodsProperty(
                property.getGoodsId(), property.getCategory());
        }
        return goodsPropertyRepository.save(property);
    }

    @Override
    public void deleteGoodsProperty(Integer propertyId) {
        goodsPropertyRepository.deleteById(propertyId);
    }

    @Override
    public List<GoodsProperty> getGoodsProperties(Integer goodsId) {
        return goodsPropertyRepository.findByGoodsId(goodsId);
    }

    @Override
    @Transactional
    public void setDefaultProperty(Integer propertyId, GoodsProperty.PropertyCategory category) {
        GoodsProperty property = goodsPropertyRepository.findById(propertyId).orElse(null);
        if (property != null) {
            goodsPropertyRepository.resetDefaultStatusOfSameGoodsProperty(
                property.getGoodsId(), category);
            property.setIsDefault(true);
            goodsPropertyRepository.save(property);
        }
    }
}