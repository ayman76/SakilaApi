package com.example.sakilaapi.service;

import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.dto.CustomerDto;
import com.example.sakilaapi.dto.CustomerRequestDto;

public interface CustomerService {
    CustomerDto createCustomer(CustomerRequestDto customerDto);

    ApiResponse<CustomerDto> getAllCustomers(int pageNo, int pageSize);

    CustomerDto getCustomerById(Long id);

    CustomerDto getCustomerByName(String firstName);

    CustomerDto updateCustomer(Long id, CustomerRequestDto customerDto);

    void deleteCustomer(Long id);
}
