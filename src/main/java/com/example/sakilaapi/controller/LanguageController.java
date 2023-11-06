package com.example.sakilaapi.controller;

import com.example.sakilaapi.dto.LanguageDto;
import com.example.sakilaapi.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/languages")
public class LanguageController {
    private final LanguageService languageService;

    @PostMapping("/create")
    public ResponseEntity<LanguageDto> createLanguage(@RequestBody LanguageDto languageDto) {
        return new ResponseEntity<>(languageService.createLanguage(languageDto), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<LanguageDto>> getAllCategories() {
        return new ResponseEntity<>(languageService.getAllLanguages(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<LanguageDto> getLanguageById(@PathVariable Long id) {
        return new ResponseEntity<>(languageService.getLanguageById(id), HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LanguageDto> updateLanguage(@PathVariable Long id, @RequestBody LanguageDto languageDto) {
        return new ResponseEntity<>(languageService.updateLanguage(id, languageDto), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<LanguageDto> deleteLanguage(@PathVariable Long id) {
        languageService.deleteLanguage(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
