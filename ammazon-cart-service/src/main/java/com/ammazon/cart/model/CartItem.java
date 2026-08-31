package com.ammazon.cart.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Cart item model.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartItem implements Serializable {
    private String productId;
    private String productName;
    private int quantity;
    private BigDecimal price;
    private BigDecimal totalPrice;

    public void updateTotalPrice() {
        this.totalPrice = this.price.multiply(BigDecimal.valueOf(this.quantity));
    }
}