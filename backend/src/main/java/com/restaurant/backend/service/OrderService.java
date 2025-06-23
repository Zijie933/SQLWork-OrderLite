package com.restaurant.backend.service;

import com.restaurant.backend.entity.OrderInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface OrderService {
    OrderInfo createOrder(OrderInfo order);
    
    void cancelOrder(String orderNo, String reason);
    
    OrderInfo getOrderByNo(String orderNo);
    
    List<OrderInfo> getUserOrders(String wxOpenid);
    
    Page<OrderInfo> getOrdersByStatus(OrderInfo.OrderStatus status, Pageable pageable);
    
    void updateOrderStatus(String orderNo, OrderInfo.OrderStatus status);
    
    void processPayment(String orderNo, String transactionId);
    
    List<OrderInfo> getTodayOrdersByStatus(OrderInfo.OrderStatus status);
    
    void cleanupUnpaidOrders();
    
    List<String> getUnpaidOrders(int limit);

    Page<OrderInfo> getOrders(int page, int size, OrderInfo.OrderStatus status);
}