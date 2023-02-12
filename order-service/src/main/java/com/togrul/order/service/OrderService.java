package com.togrul.order.service;

import com.togrul.order.dto.OrderRequest;
import com.togrul.order.model.Order;

public interface OrderService {

    Order placeOrder(OrderRequest orderRequest);
}
