package com.togrul.order.service.impl;

import com.togrul.order.dao.OrderRepository;
import com.togrul.order.dto.InventoryResponse;
import com.togrul.order.dto.OrderRequest;
import com.togrul.order.model.Order;
import com.togrul.order.model.OrderLineItems;
import com.togrul.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final WebClient.Builder webClientBuilder;

    @Override
    public String placeOrder(OrderRequest orderRequest) {
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

        List<String> skuCodeList = order.getOrderLineItems()
                .stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        InventoryResponse[] inventoryResponseArray = webClientBuilder.build().get()
                .uri("http://inventory-service/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodeList).build()
                ).retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        System.out.println(Arrays.toString(inventoryResponseArray));
        boolean allProductsInStock = Arrays.stream(inventoryResponseArray).allMatch(InventoryResponse::isInStock);

        if (!allProductsInStock) {
            throw new IllegalArgumentException("Product is not in stock, please try again later");
        }
        orderRepository.saveAndFlush(order);
        log.info("{} was saved successfully to database", order);

        return String.format("Order %s placed successfully", order);
    }
}
