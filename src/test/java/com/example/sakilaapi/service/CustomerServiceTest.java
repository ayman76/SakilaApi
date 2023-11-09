package com.example.sakilaapi.service;

import com.example.sakilaapi.dto.*;
import com.example.sakilaapi.model.Address;
import com.example.sakilaapi.model.Customer;
import com.example.sakilaapi.model.Store;
import com.example.sakilaapi.repository.AddressRepository;
import com.example.sakilaapi.repository.CustomerRepository;
import com.example.sakilaapi.repository.StoreRepository;
import com.example.sakilaapi.service.impl.CustomerServiceImpl;
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
class CustomerServiceTest {


    private static final Long CUSTOMER_ID = 1L;
    @Mock
    private CustomerRepository customerRepository;
    @Mock
    private StoreRepository storeRepository;
    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    private Customer customer;
    private CustomerRequestDto customerRequestDto;
    private Address address;
    private Store store;


    @BeforeEach
    public void setup() {
        ModelMapper modelMapper = new ModelMapper();
        customerService = new CustomerServiceImpl(modelMapper, customerRepository, addressRepository, storeRepository);
        address = Address.builder().address_id(1L).build();
        store = Store.builder().store_id(1L).build();
        customer = Customer.builder().customer_id(1L).first_name("ayman").last_name("mohamed").active(true).email("email").build();
        customerRequestDto = CustomerRequestDto.builder().customer_id(1L).first_name("ayman").last_name("mohamed").active(true).email("email").address(modelMapper.map(address, AddressDto.class)).store(modelMapper.map(store, StoreDto.class)).build();

    }

    @Test
    public void CustomerService_CreateCustomer_ReturnCustomerDto() {


        when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(customer);
        when(addressRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(address));
        when(storeRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(store));

        CustomerDto response = customerService.createCustomer(customerRequestDto);

        Assertions.assertThat(response).isNotNull();
    }

    @Test
    public void CustomerService_GetAllCustomers_ReturnCustomerDtos() {

        Page<Customer> customers = Mockito.mock(Page.class);

        when(customerRepository.findAll(Mockito.any(Pageable.class))).thenReturn(customers);

        ApiResponse<CustomerDto> response = customerService.getAllCustomers(0, 10);

        Assertions.assertThat(response).isNotNull();
    }

    @Test
    public void CustomerService_GetCustomerById_ReturnCustomerDto() {

        when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(customer));

        CustomerDto foundedCustomer = customerService.getCustomerById(CUSTOMER_ID);

        Assertions.assertThat(foundedCustomer).isNotNull();
        Assertions.assertThat(foundedCustomer.getCustomer_id()).isEqualTo(CUSTOMER_ID);
    }

    @Test
    public void CustomerService_GetCustomerByName_ReturnCustomerDto() {

        when(customerRepository.findByFirstName(Mockito.anyString())).thenReturn(Optional.ofNullable(customer));

        CustomerDto foundedCustomer = customerService.getCustomerByName("customer");

        Assertions.assertThat(foundedCustomer).isNotNull();
        Assertions.assertThat(foundedCustomer.getCustomer_id()).isEqualTo(CUSTOMER_ID);
    }

    @Test
    public void CustomerService_UpdateCustomer_ReturnCustomerDto() {


        when(customerRepository.save(Mockito.any(Customer.class))).thenReturn(customer);
        when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(customer));
        when(addressRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(address));
        when(storeRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(store));

        CustomerDto response = customerService.updateCustomer(CUSTOMER_ID, customerRequestDto);

        Assertions.assertThat(response).isNotNull();
        Assertions.assertThat(response.getCustomer_id()).isEqualTo(customerRequestDto.getCustomer_id());
        Assertions.assertThat(response.getFirst_name()).isEqualTo(customerRequestDto.getFirst_name());
    }

    @Test
    public void CustomerService_DeleteCustomer_ReturnNothing() {

        when(customerRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(customer));

        assertAll(() -> customerService.deleteCustomer(CUSTOMER_ID));
    }


}