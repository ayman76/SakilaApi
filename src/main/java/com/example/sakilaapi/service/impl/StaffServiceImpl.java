package com.example.sakilaapi.service.impl;

import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.dto.StaffDto;
import com.example.sakilaapi.dto.StaffRequestDto;
import com.example.sakilaapi.model.Address;
import com.example.sakilaapi.model.Staff;
import com.example.sakilaapi.model.Store;
import com.example.sakilaapi.repository.AddressRepository;
import com.example.sakilaapi.repository.StaffRepository;
import com.example.sakilaapi.repository.StoreRepository;
import com.example.sakilaapi.service.StaffService;
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
public class StaffServiceImpl implements StaffService {

    private final ModelMapper modelMapper;
    private final StaffRepository staffRepository;
    private final AddressRepository addressRepository;
    private final StoreRepository storeRepository;

    @Override
    public StaffDto createStaff(StaffRequestDto staffDto) {
        Address address = addressRepository.findById(staffDto.getAddress().getAddress_id()).orElseThrow(() -> new RuntimeException("Not Founded Address with id: " + staffDto.getAddress().getAddress_id()));
        Store store = storeRepository.findById(staffDto.getStore().getStore_id()).orElseThrow(() -> new RuntimeException("Not Founded Store with id: " + staffDto.getStore().getStore_id()));
        Staff staff = modelMapper.map(staffDto, Staff.class);
        staff.setAddress(address);
        staff.setStore(store);
        Staff savedStaff = staffRepository.save(staff);
        return modelMapper.map(savedStaff, StaffDto.class);
    }

    @Override
    public ApiResponse<StaffDto> getAllStaffs(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Staff> staffs = staffRepository.findAll(pageable);
        List<Staff> listOfStaffs = staffs.getContent();
        List<StaffDto> content = listOfStaffs.stream().map(c -> modelMapper.map(c, StaffDto.class)).toList();
        return getApiResponse(pageNo, pageSize, content, staffs);
    }

    @Override
    public StaffDto getStaffById(Long id) {
        Staff staff = staffRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Staff with id: " + id));
        return modelMapper.map(staff, StaffDto.class);
    }

    @Override
    public StaffDto getStaffByName(String firstName) {
        Staff staff = staffRepository.findStaffByFirstName(firstName).orElseThrow(() -> new RuntimeException("Not Founded Staff with Name: " + firstName));
        return modelMapper.map(staff, StaffDto.class);
    }

    @Override
    public StaffDto updateStaff(Long id, StaffRequestDto staffDto) {
        Staff foundedStaff = staffRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Staff with id: " + id));
        Address address = addressRepository.findById(staffDto.getAddress().getAddress_id()).orElseThrow(() -> new RuntimeException("Not Founded Address with id: " + staffDto.getAddress().getAddress_id()));
        Store store = storeRepository.findById(staffDto.getStore().getStore_id()).orElseThrow(() -> new RuntimeException("Not Founded Store with id: " + staffDto.getStore().getStore_id()));

        foundedStaff.setFirst_name(staffDto.getFirst_name());
        foundedStaff.setLast_name(staffDto.getLast_name());
        foundedStaff.setEmail(staffDto.getEmail());
        foundedStaff.setActive(staffDto.isActive());
        foundedStaff.setPassword(staffDto.getPassword());
        foundedStaff.setUsername(staffDto.getUsername());
        foundedStaff.setStore(store);
        foundedStaff.setAddress(address);

        Staff updatedStaff = staffRepository.save(foundedStaff);
        return modelMapper.map(updatedStaff, StaffDto.class);

    }

    @Override
    public void deleteStaff(Long id) {
        Staff foundedStaff = staffRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Staff with id: " + id));
        staffRepository.delete(foundedStaff);

    }
}
