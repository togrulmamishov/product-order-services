package com.togrul.order.dto;

import java.math.BigDecimal;

public record OrderLineItemsDto(String skuCode,
                                BigDecimal price,
                                Integer quantity) {
}
