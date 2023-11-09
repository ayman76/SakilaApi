package com.example.sakilaapi.service;

import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.dto.StaffDto;
import com.example.sakilaapi.model.Staff;
import com.example.sakilaapi.repository.AddressRepository;
import com.example.sakilaapi.repository.StaffRepository;
import com.example.sakilaapi.repository.StoreRepository;
import com.example.sakilaapi.service.impl.StaffServiceImpl;
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
class StaffServiceTest {


    private static final Long STAFF_ID = 1L;
    @Mock
    private StaffRepository staffRepository;
    @Mock
    private StoreRepository storeRepository;
    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private StaffServiceImpl staffService;

    private Staff staff;


    @BeforeEach
    public void setup() {
        ModelMapper modelMapper = new ModelMapper();
        staffService = new StaffServiceImpl(modelMapper, staffRepository, addressRepository, storeRepository);
        staff = Staff.builder().staff_id(1L).first_name("ayman").last_name("mohamed").active(true).email("email").password("password").username("userName").build();
    }


    @Test
    public void StaffService_GetAllStaffs_ReturnStaffDtos() {

        Page<Staff> staffs = Mockito.mock(Page.class);

        when(staffRepository.findAll(Mockito.any(Pageable.class))).thenReturn(staffs);

        ApiResponse<StaffDto> response = staffService.getAllStaffs(0, 10);

        Assertions.assertThat(response).isNotNull();
    }

    @Test
    public void StaffService_GetStaffById_ReturnStaffDto() {

        when(staffRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(staff));

        StaffDto foundedStaff = staffService.getStaffById(STAFF_ID);

        Assertions.assertThat(foundedStaff).isNotNull();
        Assertions.assertThat(foundedStaff.getStaff_id()).isEqualTo(STAFF_ID);
    }

    @Test
    public void StaffService_GetStaffByName_ReturnStaffDto() {

        when(staffRepository.findStaffByFirstName(Mockito.anyString())).thenReturn(Optional.ofNullable(staff));

        StaffDto foundedStaff = staffService.getStaffByName("staff");

        Assertions.assertThat(foundedStaff).isNotNull();
        Assertions.assertThat(foundedStaff.getStaff_id()).isEqualTo(STAFF_ID);
    }


}