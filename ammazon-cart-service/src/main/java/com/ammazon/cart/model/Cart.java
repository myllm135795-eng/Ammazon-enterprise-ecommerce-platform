package com.ammazon.cart.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Shopping cart model.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cart implements Serializable {
    private String userId;
    private List<CartItem> items;
    private BigDecimal totalPrice;
    private long createdAt;
    private long updatedAt;

    public void addItem(CartItem item) {
        if (this.items == null) {
            this.items = new ArrayList<>();
        }
        this.items.removeIf(i -> i.getProductId().equals(item.getProductId()));
        item.updateTotalPrice();
        this.items.add(item);
        calculateTotal();
    }

    public void removeItem(String productId) {
        if (this.items != null) {
            this.items.removeIf(i -> i.getProductId().equals(productId));
            calculateTotal();
        }
    }

    public void calculateTotal() {
        this.totalPrice = this.items != null
                ? this.items.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                : BigDecimal.ZERO;
    }
}