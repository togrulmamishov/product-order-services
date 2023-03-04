package com.togrul.inventory.service.impl;

import com.togrul.inventory.dao.InventoryRepository;
import com.togrul.inventory.dto.InventoryResponse;
import com.togrul.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @SneakyThrows
    @Transactional(readOnly = true)
    @Override
    public List<InventoryResponse> getAllBySkuCodeList(List<String> skuCodeList) {
        log.info("Wait started");
        Thread.sleep(10000);
        log.info("Wait ended");

        return inventoryRepository.findBySkuCodeIn(skuCodeList)
                .stream()
                .map(InventoryResponse::new)
                .toList();
    }
}
