package com.example.sakilaapi.service;

import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.dto.FilmDto;
import com.example.sakilaapi.dto.InventoryDto;
import com.example.sakilaapi.dto.StoreDto;
import com.example.sakilaapi.model.Film;
import com.example.sakilaapi.model.Inventory;
import com.example.sakilaapi.model.Store;
import com.example.sakilaapi.repository.FilmRepository;
import com.example.sakilaapi.repository.InventoryRepository;
import com.example.sakilaapi.repository.StoreRepository;
import com.example.sakilaapi.service.impl.InventoryServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InventoryServiceTest {


    private static final Long INVENTORY_ID = 1L;
    @Mock
    private InventoryRepository inventoryRepository;
    @Mock
    private FilmRepository filmRepository;
    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    private Inventory inventory;
    private InventoryDto inventoryDto;
    private Film film;
    private Store store;


    @BeforeEach
    public void setup() {
        ModelMapper modelMapper = new ModelMapper();
        inventoryService = new InventoryServiceImpl(modelMapper, inventoryRepository, filmRepository, storeRepository);
        film = Film.builder().film_id(1L).build();
        store = Store.builder().store_id(1L).build();
        inventory = Inventory.builder().inventory_id(1L).film(film).store(store).build();
        inventoryDto = InventoryDto.builder().inventory_id(1L).film(modelMapper.map(film, FilmDto.class)).store(modelMapper.map(store, StoreDto.class)).build();

    }

    @Test
    public void InventoryService_CreateInventory_ReturnInventoryDto() {


        when(inventoryRepository.save(Mockito.any(Inventory.class))).thenReturn(inventory);
        when(filmRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(film));
        when(storeRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(store));

        InventoryDto response = inventoryService.createInventory(inventoryDto);

        Assertions.assertThat(response).isNotNull();
    }


    @Test
    public void InventoryService_GetAllInventories_ReturnInventoryDtos() {

        Page<Inventory> inventories = Mockito.mock(Page.class);

        when(inventoryRepository.findAll(Mockito.any(Pageable.class))).thenReturn(inventories);

        ApiResponse<InventoryDto> response = inventoryService.getAllInventories(0, 10);

        Assertions.assertThat(response).isNotNull();
    }

    @Test
    public void InventoryService_GetInventoryById_ReturnInventoryDto() {

        when(inventoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(inventory));

        InventoryDto foundedInventory = inventoryService.getInventoryById(INVENTORY_ID);

        Assertions.assertThat(foundedInventory).isNotNull();
        Assertions.assertThat(foundedInventory.getInventory_id()).isEqualTo(INVENTORY_ID);
    }

    @Test
    public void InventoryService_UpdateInventory_ReturnInventoryDto() {


        when(inventoryRepository.save(Mockito.any(Inventory.class))).thenReturn(inventory);
        when(inventoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(inventory));
        when(filmRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(film));
        when(storeRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(store));

        InventoryDto response = inventoryService.updateInventory(INVENTORY_ID, inventoryDto);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getInventory_id()).isEqualTo(inventoryDto.getInventory_id());
        Assertions.assertThat(response.getStore().getStore_id()).isEqualTo(inventoryDto.getStore().getStore_id());
        Assertions.assertThat(response.getFilm().getFilm_id()).isEqualTo(inventoryDto.getFilm().getFilm_id());
    }

    @Test
    public void InventoryService_DeleteInventory_ReturnNothing() {

        when(inventoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(inventory));

        assertAll(() -> inventoryService.deleteInventory(INVENTORY_ID));
    }


}