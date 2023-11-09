package com.example.sakilaapi.service;

import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.dto.RentalDto;

public interface RentalService {
    RentalDto createRental(RentalDto rentalDto);

    ApiResponse<RentalDto> getAllRentals(int pageNo, int pageSize);

    RentalDto getRentalById(Long id);

    RentalDto updateRental(Long id, RentalDto rentalDto);

    void deleteRental(Long id);
}
