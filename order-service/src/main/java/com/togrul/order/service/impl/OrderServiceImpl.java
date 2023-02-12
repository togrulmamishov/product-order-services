package com.togrul.order.service.impl;

import com.togrul.order.dao.OrderRepository;
import com.togrul.order.dto.OrderRequest;
import com.togrul.order.model.Order;
import com.togrul.order.model.OrderLineItems;
import com.togrul.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public Order placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        var orderLineItemsList = orderRequest.orderLineItemsDtoList()
                .stream()
                .map(dto -> OrderLineItems.builder()
                        .price(dto.price())
                        .quantity(dto.quantity())
                        .skuCode(dto.skuCode())
                        .build()
                ).toList();

        order.setOrderLineItems(orderLineItemsList);
        orderRepository.saveAndFlush(order);
        log.info("{} was saved successfully to database", order);

        return order;
    }
}
