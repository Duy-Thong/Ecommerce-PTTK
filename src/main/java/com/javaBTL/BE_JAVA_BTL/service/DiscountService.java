package com.javaBTL.BE_JAVA_BTL.service;

import com.javaBTL.BE_JAVA_BTL.model.discount.Discount;
import com.javaBTL.BE_JAVA_BTL.model.discount.DiscountType;
import com.javaBTL.BE_JAVA_BTL.repository.DiscountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class DiscountService {
    @Autowired
    private DiscountRepository discountRepository;

    public Discount createDiscount(Discount discount) {
        if (discountRepository.existsByCode(discount.getCode())) {
            throw new RuntimeException("Mã giảm giá đã tồn tại");
        }
        return discountRepository.save(discount);
    }

    public Discount getDiscountByCode(String code) {
        return discountRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy mã giảm giá"));
    }

    public List<Discount> getAllDiscounts() {
        return discountRepository.findAll();
    }

    public Discount updateDiscount(UUID id, Discount discountDetails) {
        Discount discount = discountRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy mã giảm giá"));
        
        discount.setCode(discountDetails.getCode());
        discount.setName(discountDetails.getName());
        discount.setDescription(discountDetails.getDescription());
        discount.setType(discountDetails.getType());
        discount.setValue(discountDetails.getValue());
        discount.setMaxDiscountAmount(discountDetails.getMaxDiscountAmount());
        discount.setMinOrderAmount(discountDetails.getMinOrderAmount());
        discount.setStartDate(discountDetails.getStartDate());
        discount.setEndDate(discountDetails.getEndDate());
        discount.setIsActive(discountDetails.getIsActive());
        discount.setMaxUsage(discountDetails.getMaxUsage());
        
        return discountRepository.save(discount);
    }

    public void deleteDiscount(UUID id) {
        discountRepository.deleteById(id);
    }

    public boolean validateDiscount(String code, Double orderAmount) {
        Discount discount = discountRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Mã giảm giá không hợp lệ"));

        LocalDateTime now = LocalDateTime.now();
        
        if (!discount.getIsActive()) {
            throw new RuntimeException("Mã giảm giá đã bị vô hiệu hóa");
        }
        
        if (now.isBefore(discount.getStartDate()) || now.isAfter(discount.getEndDate())) {
            throw new RuntimeException("Mã giảm giá đã hết hạn hoặc chưa có hiệu lực");
        }
        
        if (discount.getCurrentUsage() >= discount.getMaxUsage()) {
            throw new RuntimeException("Mã giảm giá đã hết lượt sử dụng");
        }
        
        if (orderAmount < discount.getMinOrderAmount()) {
            throw new RuntimeException("Giá trị đơn hàng chưa đạt mức tối thiểu");
        }
        
        return true;
    }

    public double calculateDiscountAmount(String code, double orderAmount) {
        Discount discount = getDiscountByCode(code);
        
        if (discount.getType() == DiscountType.FIXED_AMOUNT) {
            return discount.getValue();
        } else {
            double discountAmount = orderAmount * (discount.getValue() / 100);
            if (discount.getMaxDiscountAmount() != null) {
                return Math.min(discountAmount, discount.getMaxDiscountAmount());
            }
            return discountAmount;
        }
    }
} 