package com.example.sakilaapi.service.impl;

import com.example.sakilaapi.dto.ApiResponse;
import com.example.sakilaapi.dto.CategoryDto;
import com.example.sakilaapi.model.Category;
import com.example.sakilaapi.repository.CategoryRepository;
import com.example.sakilaapi.service.CategoryService;
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
public class CategoryServiceImpl implements CategoryService {
    private final ModelMapper modelMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryDto createCategory(CategoryDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        Category savedCategory = categoryRepository.save(category);
        return modelMapper.map(savedCategory, CategoryDto.class);
    }

    @Override
    public ApiResponse<CategoryDto> getAllCategories(int pageNo, int pageSize) {
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<Category> categories = categoryRepository.findAll(pageable);
        List<Category> listOfCategories = categories.getContent();
        List<CategoryDto> content = listOfCategories.stream().map(c -> modelMapper.map(c, CategoryDto.class)).toList();
        return getApiResponse(pageNo, pageSize, content, categories);
    }

    @Override
    public CategoryDto getCategoryById(Long id) {
        Category foundedCategory = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Category with id: " + id));
        return modelMapper.map(foundedCategory, CategoryDto.class);
    }

    @Override
    public CategoryDto updateCategory(Long id, CategoryDto categoryDto) {
        Category foundedCategory = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Category with id: " + id));
        foundedCategory.setName(categoryDto.getName());
        Category updateCategory = categoryRepository.save(foundedCategory);
        return modelMapper.map(updateCategory, CategoryDto.class);
    }

    @Override
    public void deleteCategory(Long id) {
        Category foundedCategory = categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Not Founded Category with id: " + id));
        categoryRepository.delete(foundedCategory);
    }
}
