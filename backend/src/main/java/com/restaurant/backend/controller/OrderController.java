package com.restaurant.backend.controller;

import com.restaurant.backend.entity.OrderInfo;
import com.restaurant.backend.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderInfo> createOrder(@RequestBody OrderInfo order) {
        return ResponseEntity.ok(orderService.createOrder(order));
    }

    @DeleteMapping("/{orderNo}")
    public ResponseEntity<Void> cancelOrder(
            @PathVariable String orderNo,
            @RequestParam(required = false) String reason) {
        orderService.cancelOrder(orderNo, reason);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{orderNo}")
    public ResponseEntity<OrderInfo> getOrder(@PathVariable String orderNo) {
        return ResponseEntity.ok(orderService.getOrderByNo(orderNo));
    }

    @GetMapping("/user/{wxOpenid}")
    public ResponseEntity<List<OrderInfo>> getUserOrders(@PathVariable String wxOpenid) {
        return ResponseEntity.ok(orderService.getUserOrders(wxOpenid));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<Page<OrderInfo>> getOrdersByStatus(
            @PathVariable OrderInfo.OrderStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(orderService.getOrdersByStatus(status, 
            org.springframework.data.domain.PageRequest.of(page, size)));
    }

    @PutMapping("/{orderNo}/status")
    public ResponseEntity<Void> updateOrderStatus(
            @PathVariable String orderNo,
            @RequestParam OrderInfo.OrderStatus status) {
        orderService.updateOrderStatus(orderNo, status);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/{orderNo}/payment")
    public ResponseEntity<Void> processPayment(
            @PathVariable String orderNo,
            @RequestParam String transactionId) {
        orderService.processPayment(orderNo, transactionId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/today/{status}")
    public ResponseEntity<List<OrderInfo>> getTodayOrdersByStatus(
            @PathVariable OrderInfo.OrderStatus status) {
        return ResponseEntity.ok(orderService.getTodayOrdersByStatus(status));
    }

    @DeleteMapping("/cleanup/unpaid")
    public ResponseEntity<Void> cleanupUnpaidOrders() {
        orderService.cleanupUnpaidOrders();
        return ResponseEntity.ok().build();
    }

    @GetMapping("/unpaid")
    public ResponseEntity<List<String>> getUnpaidOrders(
            @RequestParam(defaultValue = "10") int limit) {
        return ResponseEntity.ok(orderService.getUnpaidOrders(limit));
    }

    @GetMapping
    public ResponseEntity<Page<OrderInfo>> getOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) OrderInfo.OrderStatus status) {
        return ResponseEntity.ok(orderService.getOrders(page, size, status));
    }
}