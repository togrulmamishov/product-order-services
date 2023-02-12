package com.togrul.product.controller;

import com.togrul.product.service.ProductService;
import com.togrul.product.dto.ProductRequest;
import com.togrul.product.model.Product;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ResponseEntity<String> createProduct(@RequestBody ProductRequest productRequest) {
        log.info("Creating new product {}", productRequest);

        Product product = productService.createProduct(productRequest);
        return new ResponseEntity<>("Product created successfully " + product, CREATED);
    }

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        log.info("Retrieving all products");

        return new ResponseEntity<>(productService.getAllProducts(), OK);
    }
}
