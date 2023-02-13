package com.togrul.inventory.dto;

import com.togrul.inventory.model.Inventory;
import lombok.Data;

@Data
public class InventoryResponse {

    private String skuCode;
    private boolean isInStock;

    public InventoryResponse(Inventory inventory) {
        this.skuCode = inventory.getSkuCode();
        this.isInStock = inventory.getQuantity() > 0;
    }
}
