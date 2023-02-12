package com.togrul.product;

import com.google.gson.Gson;
import com.togrul.product.dao.ProductRepository;
import com.togrul.product.dto.ProductRequest;
import com.togrul.product.model.Product;
import com.togrul.product.service.ProductService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Testcontainers
@AutoConfigureMockMvc
public class ProductServiceApplicationTests {

    @Container
    static MongoDBContainer mongoDBContainer = new MongoDBContainer("mongo:latest");

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductRepository productRepository;

    @MockBean
    private ProductService productService;

    private final Gson gson = new Gson();


    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.data.mongodb.uri", mongoDBContainer::getReplicaSetUrl);
    }

    @Test
    void shouldCreateProduct() throws Exception {
        ProductRequest request = getProductRequest();
        String json = gson.toJson(request);
        mockMvc.perform(post("/api/product")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated());
        assertEquals(1, productRepository.findAll().size());
        Assertions.assertEquals("Iphone 13", productRepository.findAll().get(0).getName());
    }

    @Test
    void shouldReturnProducts() throws Exception {

        Mockito.when(productService.getAllProducts()).thenReturn(
                List.of(
                        Product.builder()
                                .id("63e81606cb762659b972c693")
                                .name("Test product")
                                .price(BigDecimal.TEN)
                                .description("Test")
                                .build(),
                        Product.builder()
                                .id("63e81606cb712659b972c69a")
                                .name("Test product 1")
                                .price(BigDecimal.ONE)
                                .description("Test 1")
                                .build()
                )
        );
        mockMvc.perform(MockMvcRequestBuilders.get("/api/product")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is("Test product")))
                .andExpect(jsonPath("$[1].name", is("Test product 1")));

    }

    private ProductRequest getProductRequest() {
        return new ProductRequest("Iphone 13", "Iphone 13", BigDecimal.valueOf(1300));
    }

}
