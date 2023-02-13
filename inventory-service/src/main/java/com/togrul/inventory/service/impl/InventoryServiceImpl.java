package com.togrul.inventory.service.impl;

import com.togrul.inventory.dao.InventoryRepository;
import com.togrul.inventory.dto.InventoryResponse;
import com.togrul.inventory.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    @Transactional(readOnly = true)
    @Override
    public List<InventoryResponse> getAllBySkuCodeList(List<String> skuCodeList) {
        return inventoryRepository.findBySkuCodeIn(skuCodeList)
                .stream()
                .map(InventoryResponse::new)
                .toList();
    }
}
