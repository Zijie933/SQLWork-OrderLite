package com.restaurant.backend.repository;

import com.restaurant.backend.entity.OrderInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OrderInfoRepository extends JpaRepository<OrderInfo, String> {
    Page<OrderInfo> findAllByOrderByCreateTimeDesc(Pageable pageable);
    Page<OrderInfo> findByOrderStatusOrderByCreateTimeDesc(OrderInfo.OrderStatus status, Pageable pageable);
    
    List<OrderInfo> findByWxOpenidOrderByCreateTimeDesc(String wxOpenid);
    
    @Query("SELECT o.orderNo FROM OrderInfo o WHERE o.wxPayTransactionId IS NULL AND o.orderStatus = 'UNPAID' ORDER BY o.createTime DESC")
    List<String> findUnpaidOrders(Pageable pageable);
    
    @Modifying
    @Query("UPDATE OrderInfo o SET o.orderStatus = ?2 WHERE o.orderNo = ?1")
    void updateOrderStatus(String orderNo, OrderInfo.OrderStatus status);
    
    @Modifying
    @Query("DELETE FROM OrderInfo o WHERE o.orderStatus = 'UNPAID' AND o.wxPayTransactionId IS NULL")
    void deleteUnpaidOrders();
    
    @Query("SELECT o FROM OrderInfo o WHERE o.orderStatus = ?1 AND o.createTime >= CURRENT_DATE")
    List<OrderInfo> findTodayOrdersByStatus(OrderInfo.OrderStatus status);
}