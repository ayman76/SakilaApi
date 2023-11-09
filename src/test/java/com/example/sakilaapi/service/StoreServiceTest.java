package com.example.sakilaapi.service;

import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.dto.StoreDto;
import com.example.sakilaapi.model.Store;
import com.example.sakilaapi.repository.AddressRepository;
import com.example.sakilaapi.repository.StaffRepository;
import com.example.sakilaapi.repository.StoreRepository;
import com.example.sakilaapi.service.impl.StoreServiceImpl;
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

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class StoreServiceTest {


    private static final Long STORE_ID = 1L;
    @Mock
    private StoreRepository storeRepository;
    @Mock
    private StaffRepository staffRepository;
    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private StoreServiceImpl storeService;

    private Store store;


    @BeforeEach
    public void setup() {
        ModelMapper modelMapper = new ModelMapper();
        storeService = new StoreServiceImpl(modelMapper, storeRepository, addressRepository, staffRepository);
        store = Store.builder().store_id(1L).build();
    }


    @Test
    public void StoreService_GetAllStores_ReturnStoreDtos() {

        Page<Store> stores = Mockito.mock(Page.class);

        when(storeRepository.findAll(Mockito.any(Pageable.class))).thenReturn(stores);

        ApiResponse<StoreDto> response = storeService.getAllStores(0, 10);

        Assertions.assertThat(response).isNotNull();
    }

    @Test
    public void StoreService_GetStoreById_ReturnStoreDto() {

        when(storeRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(store));

        StoreDto foundedStore = storeService.getStoreById(STORE_ID);

        Assertions.assertThat(foundedStore).isNotNull();
        Assertions.assertThat(foundedStore.getStore_id()).isEqualTo(STORE_ID);
    }



}