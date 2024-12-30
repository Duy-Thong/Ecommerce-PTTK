package com.javaBTL.BE_JAVA_BTL.model.order;

import com.javaBTL.BE_JAVA_BTL.model.payment.Payment;
import com.javaBTL.BE_JAVA_BTL.model.shipment.Shipment;
import com.javaBTL.BE_JAVA_BTL.model.user.User;
import com.javaBTL.BE_JAVA_BTL.model.discount.Discount;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.time.LocalDateTime;
import com.fasterxml.jackson.annotation.JsonManagedReference;

@Data
@Entity
@Table(name = "orders")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonManagedReference
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
    private List<OrderItem> orderItems;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Shipment shipment;

    @OneToOne(mappedBy = "order", cascade = CascadeType.ALL)
    private Payment payment;

    @ManyToOne
    @JoinColumn(name = "discount_id")
    private Discount discount;

    private Double subtotal;
    private Double discountAmount;
    private Double totalAmount;
    
    private String shippingAddress;
    private String phoneNumber;
    private String customerName;
    private String note;
    private String status = "PENDING";

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
}