package com.example.sakilaapi.service;

import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.dto.LanguageDto;

public interface LanguageService {
    LanguageDto createLanguage(LanguageDto languageDto);

    ApiResponse<LanguageDto> getAllLanguages(int pageNo, int pageSize);

    LanguageDto getLanguageById(Long id);

    LanguageDto updateLanguage(Long id, LanguageDto languageDto);

    void deleteLanguage(Long id);
}
