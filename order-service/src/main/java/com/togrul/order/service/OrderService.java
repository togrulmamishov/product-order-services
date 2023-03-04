package com.togrul.order.service;

import com.togrul.order.dto.OrderRequest;

public interface OrderService {

    String placeOrder(OrderRequest orderRequest);
}
