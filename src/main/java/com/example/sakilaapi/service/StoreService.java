package com.example.sakilaapi.service;

import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.dto.StoreDto;

public interface StoreService {
    StoreDto createStore(StoreDto storeDto);

    ApiResponse<StoreDto> getAllStores(int pageNo, int pageSize);

    StoreDto getStoreById(Long id);

    StoreDto updateStore(Long id, StoreDto storeDto);

    void deleteStore(Long id);
}
