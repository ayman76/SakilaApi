package com.example.sakilaapi.controller;

import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.dto.RentalDto;
import com.example.sakilaapi.service.RentalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rentals")
public class RentalController {
    private final RentalService rentalService;

    @PostMapping("/create")
    public ResponseEntity<RentalDto> createRental(@RequestBody RentalDto rentalDto) {
        return new ResponseEntity<>(rentalService.createRental(rentalDto), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<RentalDto>> getAllRentals(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                                                     @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return new ResponseEntity<>(rentalService.getAllRentals(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RentalDto> getRentalById(@PathVariable Long id) {
        return new ResponseEntity<>(rentalService.getRentalById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RentalDto> updateRental(@PathVariable Long id, @RequestBody RentalDto rentalDto) {
        return new ResponseEntity<>(rentalService.updateRental(id, rentalDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<RentalDto> deleteRental(@PathVariable Long id) {
        rentalService.deleteRental(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
