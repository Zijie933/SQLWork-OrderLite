package com.restaurant.backend.service.impl;

import com.restaurant.backend.entity.OrderInfo;
import com.restaurant.backend.repository.OrderInfoRepository;
import com.restaurant.backend.service.OrderService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderInfoRepository orderRepository;

    @Override
    public OrderInfo createOrder(OrderInfo order) {
        order.setCreateTime(LocalDateTime.now());
        order.setOrderStatus(OrderInfo.OrderStatus.UNPAID);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void cancelOrder(String orderNo, String reason) {
        OrderInfo order = getOrderByNo(orderNo);
        if (order != null && order.getOrderStatus() != OrderInfo.OrderStatus.COMPLETED) {
            order.setOrderStatus(OrderInfo.OrderStatus.CANCELLED);
            order.setExtraInfo(reason);
            orderRepository.save(order);
        }
    }

    @Override
    public OrderInfo getOrderByNo(String orderNo) {
        return orderRepository.findById(orderNo).orElse(null);
    }

    @Override
    public List<OrderInfo> getUserOrders(String wxOpenid) {
        return orderRepository.findByWxOpenidOrderByCreateTimeDesc(wxOpenid);
    }

    @Override
    public Page<OrderInfo> getOrdersByStatus(OrderInfo.OrderStatus status, Pageable pageable) {
        return orderRepository.findByOrderStatusOrderByCreateTimeDesc(status, pageable);
    }

    @Override
    @Transactional
    public void updateOrderStatus(String orderNo, OrderInfo.OrderStatus status) {
        OrderInfo order = getOrderByNo(orderNo);
        if (order != null) {
            orderRepository.updateOrderStatus(orderNo, status);
            if (status == OrderInfo.OrderStatus.COMPLETED) {
                order.setFinishTime(LocalDateTime.now());
                orderRepository.save(order);
            }
        }
    }

    @Override
    @Transactional
    public void processPayment(String orderNo, String transactionId) {
        OrderInfo order = getOrderByNo(orderNo);
        if (order != null && order.getOrderStatus() == OrderInfo.OrderStatus.UNPAID) {
            order.setWxPayTransactionId(transactionId);
            order.setOrderStatus(OrderInfo.OrderStatus.PAID);
            order.setPayTime(LocalDateTime.now());
            orderRepository.save(order);
        }
    }

    @Override
    public List<OrderInfo> getTodayOrdersByStatus(OrderInfo.OrderStatus status) {
        return orderRepository.findTodayOrdersByStatus(status);
    }

    @Override
    @Transactional
    public void cleanupUnpaidOrders() {
        orderRepository.deleteUnpaidOrders();
    }

    @Override
    public List<String> getUnpaidOrders(int limit) {
        return orderRepository.findUnpaidOrders(PageRequest.of(0, limit));
    }

    @Override
    public Page<OrderInfo> getOrders(int page, int size, OrderInfo.OrderStatus status) {
        Pageable pageable = PageRequest.of(page, size);
        if (status != null) {
            return orderRepository.findByOrderStatusOrderByCreateTimeDesc(status, pageable);
        }
        return orderRepository.findAllByOrderByCreateTimeDesc(pageable);
    }
}