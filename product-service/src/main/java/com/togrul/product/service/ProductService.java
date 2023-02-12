package com.togrul.product.service;

import com.togrul.product.dto.ProductRequest;
import com.togrul.product.model.Product;

import java.util.List;

public interface ProductService {

    Product createProduct(ProductRequest productRequest);
    List<Product> getAllProducts();
}
