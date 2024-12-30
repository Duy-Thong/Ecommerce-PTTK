package com.javaBTL.BE_JAVA_BTL.model.discount;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "discounts")
public class Discount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(unique = true)
    private String code;
    
    private String name;
    private String description;
    
    @Enumerated(EnumType.STRING)
    private DiscountType type; // FIXED_AMOUNT hoặc PERCENTAGE
    
    private Double value; // Giá trị giảm (số tiền cố định hoặc phần trăm)
    private Double maxDiscountAmount; // Số tiền giảm tối đa (cho giảm theo %)
    private Double minOrderAmount; // Giá trị đơn hàng tối thiểu
    
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    
    private Boolean isActive = true;
    private Integer maxUsage; // Số lần sử dụng tối đa
    private Integer currentUsage = 0; // Số lần đã sử dụng

    @Column(name = "created_at")
    private LocalDateTime createdAt = LocalDateTime.now();
} 