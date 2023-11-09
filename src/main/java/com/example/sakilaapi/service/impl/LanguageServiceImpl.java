package com.example.sakilaapi.service.impl;

import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.dto.LanguageDto;
import com.example.sakilaapi.model.Language;
import com.example.sakilaapi.repository.LanguageRepository;
import com.example.sakilaapi.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.sakilaapi.utils.HelperFunctions.getApiResponse;

@Service
@RequiredArgsConstructor
public class LanguageServiceImpl implements LanguageService {
    private final ModelMapper modelMapper;
    private final LanguageRepository languageRepository;

    @Override
    public LanguageDto createLanguage(LanguageDto languageDto) {
        Language language = modelMapper.map(languageDto, Language.class);
        Language savedLanguage = languageRepository.save(language);
        return modelMapper.map(savedLanguage, LanguageDto.class);
    }

    @Override
    public ApiResponse<LanguageDto> getAllLanguages(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Language> languages = languageRepository.findAll(pageable);
        List<Language> listOfLanguages = languages.getContent();
        List<LanguageDto> content = listOfLanguages.stream().map(c -> modelMapper.map(c, LanguageDto.class)).toList();
        return getApiResponse(pageNo, pageSize, content, languages);
    }

    @Override
    public LanguageDto getLanguageById(Long id) {
        Language foundedLanguage = languageRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Language with id: " + id));
        return modelMapper.map(foundedLanguage, LanguageDto.class);
    }

    @Override
    public LanguageDto updateLanguage(Long id, LanguageDto languageDto) {
        Language foundedLanguage = languageRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Language with id: " + id));
        foundedLanguage.setName(languageDto.getName());
        Language updateLanguage = languageRepository.save(foundedLanguage);
        return modelMapper.map(updateLanguage, LanguageDto.class);
    }

    @Override
    public void deleteLanguage(Long id) {
        Language foundedLanguage = languageRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Language with id: " + id));
        languageRepository.delete(foundedLanguage);
    }
}
