package com.togrul.inventory.service.impl;

import com.togrul.inventory.dao.InventoryRepository;
import com.togrul.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    @Override
    public boolean isInStock(String skuCode) {
        boolean isPresent = inventoryRepository.findBySkuCode(skuCode).isPresent();
        log.info("Inventory with skuCode {} exist database: {}", skuCode, isPresent);

        return isPresent;
    }
}
