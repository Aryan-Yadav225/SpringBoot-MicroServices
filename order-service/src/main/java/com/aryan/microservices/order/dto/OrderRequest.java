package com.aryan.microservices.order.dto;

import java.math.BigDecimal;

public record OrderRequest(Long id, String skuCode, Integer quantity, BigDecimal price) {
}
