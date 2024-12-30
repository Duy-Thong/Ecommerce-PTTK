package com.javaBTL.BE_JAVA_BTL.model.payment;

public enum PaymentStatus {
    PENDING,    // Chờ thanh toán
    COMPLETED,  // Đã thanh toán
    FAILED,     // Thanh toán thất bại
    REFUNDED    // Đã hoàn tiền
} 