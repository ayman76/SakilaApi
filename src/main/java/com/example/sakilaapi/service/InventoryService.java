package com.example.sakilaapi.service;

import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.dto.InventoryDto;

public interface InventoryService {
    InventoryDto createInventory(InventoryDto inventoryDto);

    ApiResponse<InventoryDto> getAllInventories(int pageNo, int pageSize);

    InventoryDto getInventoryById(Long id);

    InventoryDto updateInventory(Long id, InventoryDto inventoryDto);

    void deleteInventory(Long id);
}
