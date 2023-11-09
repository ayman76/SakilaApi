package com.example.sakilaapi.controller;

import com.example.sakilaapi.dto.AddressDto;
import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/address")
public class AddressController {
    private final AddressService addressService;

    @PostMapping("/create")
    public ResponseEntity<AddressDto> createAddress(@RequestBody AddressDto addressDto) {
        return new ResponseEntity<>(addressService.createAddress(addressDto), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<AddressDto>> getAllCategories(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                                                    @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return new ResponseEntity<>(addressService.getAllAddresses(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressDto> getAddressById(@PathVariable Long id) {
        return new ResponseEntity<>(addressService.getAddressById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDto> updateAddress(@PathVariable Long id, @RequestBody AddressDto addressDto) {
        return new ResponseEntity<>(addressService.updateAddress(id, addressDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<AddressDto> deleteAddress(@PathVariable Long id) {
        addressService.deleteAddress(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
