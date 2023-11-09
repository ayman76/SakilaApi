package com.example.sakilaapi.controller;

import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.dto.CountryDto;
import com.example.sakilaapi.service.CountryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/countries")
public class CountryController {
    private final CountryService countryService;

    @PostMapping("/create")
    public ResponseEntity<CountryDto> createCountry(@RequestBody CountryDto countryDto) {
        return new ResponseEntity<>(countryService.createCountry(countryDto), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<CountryDto>> getAllCategories(@RequestParam(value = "pageNo", defaultValue = "0", required = false) int pageNo,
                                                                    @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize) {
        return new ResponseEntity<>(countryService.getAllCountries(pageNo, pageSize), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryDto> getCountryById(@PathVariable Long id) {
        return new ResponseEntity<>(countryService.getCountryById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CountryDto> updateCountry(@PathVariable Long id, @RequestBody CountryDto countryDto) {
        return new ResponseEntity<>(countryService.updateCountry(id, countryDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CountryDto> deleteCountry(@PathVariable Long id) {
        countryService.deleteCountry(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
