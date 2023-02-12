package com.togrul.inventory;

import com.togrul.inventory.dao.InventoryRepository;
import com.togrul.inventory.model.Inventory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class InventoryServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(InventoryServiceApplication.class, args);
    }

    /*
    * This should be commented after run
    * */
    @Bean
    public CommandLineRunner loadData(InventoryRepository repository) {
        return args -> {
            repository.save(Inventory.builder()
                    .skuCode("iphone_13")
                    .quantity(100)
                    .build());

            repository.save(Inventory.builder()
                    .skuCode("iphone_13_red")
                    .quantity(0)
                    .build());
        };
    }
}
