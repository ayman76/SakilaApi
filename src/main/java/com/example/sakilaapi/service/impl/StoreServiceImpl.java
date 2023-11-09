package com.example.sakilaapi.service.impl;

import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.dto.StoreDto;
import com.example.sakilaapi.model.Address;
import com.example.sakilaapi.model.Staff;
import com.example.sakilaapi.model.Store;
import com.example.sakilaapi.repository.AddressRepository;
import com.example.sakilaapi.repository.StaffRepository;
import com.example.sakilaapi.repository.StoreRepository;
import com.example.sakilaapi.service.StoreService;
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
public class StoreServiceImpl implements StoreService {

    private final ModelMapper modelMapper;
    private final StoreRepository storeRepository;
    private final AddressRepository addressRepository;
    private final StaffRepository staffRepository;

    @Override
    public StoreDto createStore(StoreDto storeDto) {
        Address address = addressRepository.findById(storeDto.getAddress().getAddress_id()).orElseThrow(() -> new RuntimeException("Not Founded Address with id: " + storeDto.getAddress().getAddress_id()));
        Staff staff = staffRepository.findById(storeDto.getStaff().getStaff_id()).orElseThrow(() -> new RuntimeException("Not Founded Staff with id: " + storeDto.getStaff().getStaff_id()));
        Store store = modelMapper.map(storeDto, Store.class);
        store.setAddress(address);
        store.setStaff(staff);
        Store savedStore = storeRepository.save(store);
        return modelMapper.map(savedStore, StoreDto.class);
    }

    @Override
    public ApiResponse<StoreDto> getAllStores(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Store> stores = storeRepository.findAll(pageable);
        List<Store> listOfStores = stores.getContent();
        List<StoreDto> content = listOfStores.stream().map(c -> modelMapper.map(c, StoreDto.class)).toList();
        return getApiResponse(pageNo, pageSize, content, stores);
    }

    @Override
    public StoreDto getStoreById(Long id) {
        Store store = storeRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Store with id: " + id));
        return modelMapper.map(store, StoreDto.class);
    }

    @Override
    public StoreDto updateStore(Long id, StoreDto storeDto) {
        Store foundedStore = storeRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Store with id: " + id));
        Address address = addressRepository.findById(storeDto.getAddress().getAddress_id()).orElseThrow(() -> new RuntimeException("Not Founded Address with id: " + storeDto.getAddress().getAddress_id()));
        Staff staff = staffRepository.findById(storeDto.getStaff().getStaff_id()).orElseThrow(() -> new RuntimeException("Not Founded Staff with id: " + storeDto.getStaff().getStaff_id()));

        foundedStore.setStaff(staff);
        foundedStore.setAddress(address);

        Store updatedStore = storeRepository.save(foundedStore);
        return modelMapper.map(updatedStore, StoreDto.class);

    }

    @Override
    public void deleteStore(Long id) {
        Store foundedStore = storeRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Store with id: " + id));
        storeRepository.delete(foundedStore);

    }
}
