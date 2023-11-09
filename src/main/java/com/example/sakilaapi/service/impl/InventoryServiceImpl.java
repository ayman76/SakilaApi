package com.example.sakilaapi.service.impl;

import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.dto.InventoryDto;
import com.example.sakilaapi.model.Film;
import com.example.sakilaapi.model.Store;
import com.example.sakilaapi.model.Inventory;
import com.example.sakilaapi.repository.FilmRepository;
import com.example.sakilaapi.repository.StoreRepository;
import com.example.sakilaapi.repository.InventoryRepository;
import com.example.sakilaapi.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.sakilaapi.utils.HelperFunctions.getApiResponse;

@Service
@RequiredArgsConstructor
public class InventoryServiceImpl implements InventoryService {

    private final ModelMapper modelMapper;
    private final InventoryRepository inventoryRepository;
    private final FilmRepository filmRepository;
    private final StoreRepository storeRepository;

    @Override
    public InventoryDto createInventory(InventoryDto inventoryDto) {
        Film film = filmRepository.findById(inventoryDto.getFilm().getFilm_id()).orElseThrow(() -> new RuntimeException("Not Founded Film with id: " + inventoryDto.getFilm().getFilm_id()));
        Store store = storeRepository.findById(inventoryDto.getStore().getStore_id()).orElseThrow(() -> new RuntimeException("Not Founded Store with id: " + inventoryDto.getStore().getStore_id()));
        Inventory inventory = modelMapper.map(inventoryDto, Inventory.class);
        inventory.setFilm(film);
        inventory.setStore(store);
        Inventory savedInventory = inventoryRepository.save(inventory);
        return modelMapper.map(savedInventory, InventoryDto.class);
    }

    @Override
    public ApiResponse<InventoryDto> getAllInventories(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Inventory> inventories = inventoryRepository.findAll(pageable);
        List<Inventory> listOfInventories = inventories.getContent();
        List<InventoryDto> content = listOfInventories.stream().map(c -> modelMapper.map(c, InventoryDto.class)).toList();
        return getApiResponse(pageNo, pageSize, content, inventories);
    }

    @Override
    public InventoryDto getInventoryById(Long id) {
        Inventory inventory = inventoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Inventory with id: " + id));
        return modelMapper.map(inventory, InventoryDto.class);
    }

    @Override
    public InventoryDto updateInventory(Long id, InventoryDto inventoryDto) {
        Inventory foundedInventory = inventoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Inventory with id: " + id));
        Film film = filmRepository.findById(inventoryDto.getFilm().getFilm_id()).orElseThrow(() -> new RuntimeException("Not Founded Film with id: " + inventoryDto.getFilm().getFilm_id()));
        Store store = storeRepository.findById(inventoryDto.getStore().getStore_id()).orElseThrow(() -> new RuntimeException("Not Founded Store with id: " + inventoryDto.getStore().getStore_id()));

        foundedInventory.setStore(store);
        foundedInventory.setFilm(film);

        Inventory updatedInventory = inventoryRepository.save(foundedInventory);
        return modelMapper.map(updatedInventory, InventoryDto.class);

    }

    @Override
    public void deleteInventory(Long id) {
        Inventory foundedInventory = inventoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Inventory with id: " + id));
        inventoryRepository.delete(foundedInventory);

    }
}
