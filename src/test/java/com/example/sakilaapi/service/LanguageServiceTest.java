package com.example.sakilaapi.service;

import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.dto.LanguageDto;
import com.example.sakilaapi.model.Language;
import com.example.sakilaapi.repository.LanguageRepository;
import com.example.sakilaapi.service.impl.LanguageServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LanguageServiceTest {


    private static final Long LANGUAGE_ID = 1L;
    @Mock
    private LanguageRepository languageRepository;

    @InjectMocks
    private LanguageServiceImpl languageService;

    private Language language;
    private LanguageDto languageDto;


    @BeforeEach
    public void setup() {
        ModelMapper modelMapper = new ModelMapper();
        languageService = new LanguageServiceImpl(modelMapper, languageRepository);
        language = Language.builder().language_id(1L).name("language").build();
        languageDto = LanguageDto.builder().language_id(1L).name("language").build();
    }

    @Test
    public void LanguageService_CreateLanguage_ReturnLanguageDto() {

        when(languageRepository.save(Mockito.any(Language.class))).thenReturn(language);

        LanguageDto savedLanguage = languageService.createLanguage(languageDto);

        Assertions.assertThat(savedLanguage).isNotNull();
    }

    @Test
    public void LanguageService_GetAllLanguages_ReturnLanguageDtos() {

        Page<Language> languages = Mockito.mock(Page.class);

        when(languageRepository.findAll(Mockito.any(Pageable.class))).thenReturn(languages);

        ApiResponse<LanguageDto> response = languageService.getAllLanguages(0, 10);

        Assertions.assertThat(response).isNotNull();
    }

    @Test
    public void LanguageService_GetLanguageById_ReturnLanguageDto() {

        when(languageRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(language));

        LanguageDto foundedLanguage = languageService.getLanguageById(LANGUAGE_ID);

        Assertions.assertThat(foundedLanguage).isNotNull();
        Assertions.assertThat(foundedLanguage.getLanguage_id()).isEqualTo(LANGUAGE_ID);
    }

    @Test
    public void LanguageService_UpdateLanguage_ReturnLanguageDto() {

        when(languageRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(language));
        when(languageRepository.save(Mockito.any(Language.class))).thenReturn(language);

        LanguageDto updatedLanguage = languageService.updateLanguage(LANGUAGE_ID, languageDto);

        Assertions.assertThat(updatedLanguage).isNotNull();
        Assertions.assertThat(updatedLanguage.getLanguage_id()).isEqualTo(LANGUAGE_ID);
    }

    @Test
    public void LanguageService_DeleteLanguage_ReturnLanguageDto() {

        when(languageRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(language));

        assertAll(() -> languageService.deleteLanguage(LANGUAGE_ID));
    }

}