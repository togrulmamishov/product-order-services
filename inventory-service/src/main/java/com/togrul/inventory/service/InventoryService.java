package com.togrul.inventory.service;

import com.togrul.inventory.dto.InventoryResponse;

import java.util.List;

public interface InventoryService {

    List<InventoryResponse> getAllBySkuCodeList(List<String> skuCode);
}
