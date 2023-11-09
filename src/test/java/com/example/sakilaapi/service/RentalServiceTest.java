package com.example.sakilaapi.service;

import com.example.sakilaapi.dto.*;
import com.example.sakilaapi.model.Customer;
import com.example.sakilaapi.model.Inventory;
import com.example.sakilaapi.model.Rental;
import com.example.sakilaapi.model.Staff;
import com.example.sakilaapi.repository.CustomerRepository;
import com.example.sakilaapi.repository.InventoryRepository;
import com.example.sakilaapi.repository.RentalRepository;
import com.example.sakilaapi.repository.StaffRepository;
import com.example.sakilaapi.service.impl.RentalServiceImpl;
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
class RentalServiceTest {

    private static final Long RENTAL_ID = 1L;
    @Mock
    private RentalRepository rentalRepository;
    @Mock
    private InventoryRepository inventoryRepository;
    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private StaffRepository staffRepository;

    @InjectMocks
    private RentalServiceImpl rentalService;

    private Rental rental;
    private RentalDto rentalDto;
    private Staff staff;
    private Customer customer;
    private Inventory inventory;


    @BeforeEach
    public void setup() {
        ModelMapper modelMapper = new ModelMapper();
        rentalService = new RentalServiceImpl(modelMapper, rentalRepository, inventoryRepository, staffRepository, customerRepository);
        inventory = Inventory.builder().inventory_id(1L).build();
        staff = Staff.builder().staff_id(1L).build();
        customer = Customer.builder().customer_id(1L).build();
        rental = Rental.builder().rental_id(1L).customer(customer).staff(staff).inventory(inventory).build();
        rentalDto = RentalDto.builder().rental_id(1L).customer(modelMapper.map(customer, CustomerDto.class)).staff(modelMapper.map(staff, StaffDto.class)).inventory(modelMapper.map(inventory, InventoryDto.class)).build();
    }

    @Test
    public void RentalService_CreateRental_ReturnRentalDto() {

        when(rentalRepository.save(Mockito.any(Rental.class))).thenReturn(rental);
        when(staffRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(staff));
        when(inventoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(inventory));
        when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(customer));

        RentalDto response = rentalService.createRental(rentalDto);

        Assertions.assertThat(response).isNotNull();
    }


    @Test
    public void RentalService_GetAllRentals_ReturnRentalDtos() {

        Page<Rental> rentals = Mockito.mock(Page.class);

        when(rentalRepository.findAll(Mockito.any(Pageable.class))).thenReturn(rentals);

        ApiResponse<RentalDto> response = rentalService.getAllRentals(0, 10);

        Assertions.assertThat(response).isNotNull();
    }

    @Test
    public void RentalService_GetRentalById_ReturnRentalDto() {

        when(rentalRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(rental));

        RentalDto foundedRental = rentalService.getRentalById(RENTAL_ID);

        Assertions.assertThat(foundedRental).isNotNull();
        Assertions.assertThat(foundedRental.getRental_id()).isEqualTo(RENTAL_ID);
    }

    @Test
    public void RentalService_UpdateRental_ReturnRentalDto() {

        when(rentalRepository.save(Mockito.any(Rental.class))).thenReturn(rental);
        when(rentalRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(rental));
        when(staffRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(staff));
        when(inventoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(inventory));
        when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(customer));

        RentalDto response = rentalService.updateRental(RENTAL_ID, rentalDto);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getCustomer().getCustomer_id()).isEqualTo(rentalDto.getCustomer().getCustomer_id());
        Assertions.assertThat(response.getInventory().getInventory_id()).isEqualTo(rentalDto.getInventory().getInventory_id());
        Assertions.assertThat(response.getStaff().getStaff_id()).isEqualTo(rentalDto.getStaff().getStaff_id());
    }

    @Test
    public void RentalService_DeleteRental_ReturnRentalDto() {

        when(rentalRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(rental));

        assertAll(() -> rentalService.deleteRental(RENTAL_ID));
    }

}