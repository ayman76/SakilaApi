package com.example.sakilaapi.controller;

import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.dto.CustomerDto;
import com.example.sakilaapi.dto.CustomerRequestDto;
import com.example.sakilaapi.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/customers")
public class CustomerController {
    private final CustomerService customerService;

    @PostMapping("/create")
    public ResponseEntity<CustomerDto> createCustomer(@RequestBody CustomerRequestDto customerDto) {
        return new ResponseEntity<>(customerService.createCustomer(customerDto), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<CustomerDto>> getAllCustomers(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                                                    @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return new ResponseEntity<>(customerService.getAllCustomers(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{identifier}")
    public ResponseEntity<CustomerDto> getCustomerByIdentifier(@PathVariable String identifier) {
        if (identifier.matches("\\d+")) {
            return ResponseEntity.ok(customerService.getCustomerById(Long.valueOf(identifier)));
        } else {
            return ResponseEntity.ok(customerService.getCustomerByName(identifier));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerDto> updateCustomer(@PathVariable Long id, @RequestBody CustomerRequestDto customerDto) {
        return new ResponseEntity<>(customerService.updateCustomer(id, customerDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomerDto> deleteCustomer(@PathVariable Long id) {
        customerService.deleteCustomer(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
