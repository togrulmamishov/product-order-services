package com.togrul.inventory.controller;

import com.togrul.inventory.dto.InventoryResponse;
import com.togrul.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> getInventoriesBySkuCodes(@RequestParam List<String> skuCode) {
        log.info("Retrieving inventories by sku-code list {}", skuCode);

        return inventoryService.getAllBySkuCodeList(skuCode);
    }
}
