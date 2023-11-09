package com.example.sakilaapi.service.impl;

import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.dto.CustomerDto;
import com.example.sakilaapi.dto.CustomerRequestDto;
import com.example.sakilaapi.model.Address;
import com.example.sakilaapi.model.Customer;
import com.example.sakilaapi.model.Store;
import com.example.sakilaapi.repository.AddressRepository;
import com.example.sakilaapi.repository.CustomerRepository;
import com.example.sakilaapi.repository.StoreRepository;
import com.example.sakilaapi.service.CustomerService;
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
public class CustomerServiceImpl implements CustomerService {

    private final ModelMapper modelMapper;
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final StoreRepository storeRepository;

    @Override
    public CustomerDto createCustomer(CustomerRequestDto customerDto) {
        Address address = addressRepository.findById(customerDto.getAddress().getAddress_id()).orElseThrow(() -> new RuntimeException("Not Founded Address with id: " + customerDto.getAddress().getAddress_id()));
        Store store = storeRepository.findById(customerDto.getStore().getStore_id()).orElseThrow(() -> new RuntimeException("Not Founded Store with id: " + customerDto.getStore().getStore_id()));
        Customer customer = modelMapper.map(customerDto, Customer.class);
        customer.setAddress(address);
        customer.setStore(store);
        Customer savedCustomer = customerRepository.save(customer);
        return modelMapper.map(savedCustomer, CustomerDto.class);
    }

    @Override
    public ApiResponse<CustomerDto> getAllCustomers(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Customer> customers = customerRepository.findAll(pageable);
        List<Customer> listOfCustomers = customers.getContent();
        List<CustomerDto> content = listOfCustomers.stream().map(c -> modelMapper.map(c, CustomerDto.class)).toList();
        return getApiResponse(pageNo, pageSize, content, customers);
    }

    @Override
    public CustomerDto getCustomerById(Long id) {
        Customer customer = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Customer with id: " + id));
        return modelMapper.map(customer, CustomerDto.class);
    }

    @Override
    public CustomerDto getCustomerByName(String firstName) {
        Customer customer = customerRepository.findByFirstName(firstName).orElseThrow(() -> new RuntimeException("Not Founded Customer with Name: " + firstName));
        return modelMapper.map(customer, CustomerDto.class);
    }

    @Override
    public CustomerDto updateCustomer(Long id, CustomerRequestDto customerDto) {
        Customer foundedCustomer = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Customer with id: " + id));
        Address address = addressRepository.findById(customerDto.getAddress().getAddress_id()).orElseThrow(() -> new RuntimeException("Not Founded Address with id: " + customerDto.getAddress().getAddress_id()));
        Store store = storeRepository.findById(customerDto.getStore().getStore_id()).orElseThrow(() -> new RuntimeException("Not Founded Store with id: " + customerDto.getStore().getStore_id()));

        foundedCustomer.setFirst_name(customerDto.getFirst_name());
        foundedCustomer.setLast_name(customerDto.getLast_name());
        foundedCustomer.setEmail(customerDto.getEmail());
        foundedCustomer.setActive(customerDto.isActive());
        foundedCustomer.setStore(store);
        foundedCustomer.setAddress(address);

        Customer updatedCustomer = customerRepository.save(foundedCustomer);
        return modelMapper.map(updatedCustomer, CustomerDto.class);

    }

    @Override
    public void deleteCustomer(Long id) {
        Customer foundedCustomer = customerRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Customer with id: " + id));
        customerRepository.delete(foundedCustomer);

    }
}
