package com.togrul.product.service.impl;

import com.togrul.product.dao.ProductRepository;
import com.togrul.product.dto.ProductRequest;
import com.togrul.product.model.Product;
import com.togrul.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product createProduct(ProductRequest productRequest) {
        final Product product = Product.builder()
                .name(productRequest.name())
                .description(productRequest.description())
                .price(productRequest.price())
                .build();

        productRepository.save(product);
        log.info("Product {} is saved successfully", product);

        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        final List<Product> products = productRepository.findAll();
        log.info("All products retrieved successfully. Retrieved count: " + products.size());

        return products;
    }
}
