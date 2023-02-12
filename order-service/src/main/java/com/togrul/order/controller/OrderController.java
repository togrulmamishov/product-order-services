package com.togrul.order.controller;

import com.togrul.order.dto.OrderRequest;
import com.togrul.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<String> placeOrder(@RequestBody OrderRequest orderRequest) {
        log.info("Placing order {}", orderRequest);
        var order = orderService.placeOrder(orderRequest);
        return new ResponseEntity<>(String.format("Order %s placed successfully", order), HttpStatus.OK);
    }
}
