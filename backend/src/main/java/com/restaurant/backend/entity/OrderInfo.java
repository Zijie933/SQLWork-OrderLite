package com.restaurant.backend.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "order_info")
public class OrderInfo {
    @Id
    @Column(name = "order_no")
    private String orderNo;

    @Column(name = "wx_openid", nullable = false, length = 100)
    private String wxOpenid;

    @Column(name = "order_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Column(name = "payjs_order_id")
    private String payjsOrderId;

    @Column(name = "wx_pay_transaction_id")
    private String wxPayTransactionId;

    @Column(name = "take_type")
    @Enumerated(EnumType.STRING)
    private TakeType takeType;

    @Column(name = "address_detail")
    private String addressDetail;

    @Column(name = "goods_preview")
    private String goodsPreview;

    @Column(name = "goods_total_num")
    private Integer goodsTotalNum;

    @Column(name = "total_price")
    private Integer totalPrice;

    @Column(name = "pay_price")
    private Integer payPrice;

    @Column(name = "verify_num")
    private Integer verifyNum;

    @Column(name = "extra_info")
    private String extraInfo;

    @Column(name = "create_time")
    private LocalDateTime createTime;

    @Column(name = "pay_time")
    private LocalDateTime payTime;

    @Column(name = "finish_time")
    private LocalDateTime finishTime;

    @Column(name = "user_phone")
    private String userPhone;

    private String receiver;

    public enum OrderStatus {
        UNPAID,
        PAID,
        PREPARING,
        DELIVERING,
        COMPLETED,
        CANCELLED
    }

    public enum TakeType {
        PICKUP,
        DELIVERY
    }
}