package com.example.sakilaapi.service.impl;

import com.example.sakilaapi.dto.LanguageDto;
import com.example.sakilaapi.model.Language;
import com.example.sakilaapi.repository.LanguageRepository;
import com.example.sakilaapi.service.LanguageService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
    public List<LanguageDto> getAllLanguages() {
        return languageRepository.findAll().stream().map(c -> modelMapper.map(c, LanguageDto.class)).collect(Collectors.toList());
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
