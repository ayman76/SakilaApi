package com.example.sakilaapi.controller;

import com.example.sakilaapi.dto.CityDto;
import com.example.sakilaapi.service.CityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/cities")
public class CityController {
    private final CityService cityService;

    @PostMapping("/create")
    public ResponseEntity<CityDto> createCity(@RequestBody CityDto cityDto) {
        return new ResponseEntity<>(cityService.createCity(cityDto), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<CityDto>> getAllCategories() {
        return new ResponseEntity<>(cityService.getAllCities(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CityDto> getCityById(@PathVariable Long id) {
        return new ResponseEntity<>(cityService.getCityById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CityDto> updateCity(@PathVariable Long id, @RequestBody CityDto cityDto) {
        return new ResponseEntity<>(cityService.updateCity(id, cityDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CityDto> deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
