package com.example.sakilaapi.service;

import com.example.sakilaapi.dto.LanguageDto;

import java.util.List;

public interface LanguageService {
    LanguageDto createLanguage(LanguageDto languageDto);

    List<LanguageDto> getAllLanguages();

    LanguageDto getLanguageById(Long id);

    LanguageDto updateLanguage(Long id, LanguageDto languageDto);

    void deleteLanguage(Long id);
}
