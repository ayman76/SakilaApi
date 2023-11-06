package com.example.sakilaapi.service;

import com.example.sakilaapi.dto.CategoryDto;
import com.example.sakilaapi.model.Category;
import com.example.sakilaapi.repository.CategoryRepository;
import com.example.sakilaapi.service.impl.CategoryServiceImpl;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {

    private static final Long ACTOR_ID = 1L;
    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    private Category category;
    private CategoryDto categoryDto;


    @BeforeEach
    public void setup() {
        ModelMapper modelMapper = new ModelMapper();
        categoryService = new CategoryServiceImpl(modelMapper, categoryRepository);
        category = Category.builder().category_id(1L).name("category").build();
        categoryDto = CategoryDto.builder().category_id(1L).name("category").build();
    }

    @Test
    public void CategoryService_CreateCategory_ReturnCategoryDto() {

        when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(category);

        CategoryDto savedCategory = categoryService.createCategory(categoryDto);

        Assertions.assertThat(savedCategory).isNotNull();
    }

    @Test
    public void CategoryService_GetAllCategorys_ReturnCategoryDtos() {

        when(categoryRepository.findAll()).thenReturn(Arrays.asList(category, category));

        List<CategoryDto> categorys = categoryService.getAllCategories();

        Assertions.assertThat(categorys).isNotEmpty();
    }

    @Test
    public void CategoryService_GetCategoryById_ReturnCategoryDto() {

        when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(category));

        CategoryDto foundedCategory = categoryService.getCategoryById(ACTOR_ID);

        Assertions.assertThat(foundedCategory).isNotNull();
        Assertions.assertThat(foundedCategory.getCategory_id()).isEqualTo(ACTOR_ID);
    }

    @Test
    public void CategoryService_UpdateCategory_ReturnCategoryDto() {

        when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(category));
        when(categoryRepository.save(Mockito.any(Category.class))).thenReturn(category);

        CategoryDto updatedCategory = categoryService.updateCategory(ACTOR_ID, categoryDto);

        Assertions.assertThat(updatedCategory).isNotNull();
        Assertions.assertThat(updatedCategory.getCategory_id()).isEqualTo(ACTOR_ID);
    }

    @Test
    public void CategoryService_DeleteCategory_ReturnCategoryDto() {

        when(categoryRepository.findById(Mockito.anyLong())).thenReturn(Optional.ofNullable(category));

        assertAll(() -> categoryService.deleteCategory(ACTOR_ID));
    }

}