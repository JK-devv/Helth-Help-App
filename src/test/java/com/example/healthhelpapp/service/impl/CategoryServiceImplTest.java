package com.example.healthhelpapp.service.impl;

import com.example.healthhelpapp.dto.Category;
import com.example.healthhelpapp.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static com.example.healthhelpapp.TestConstant.TEST_CATEGORY;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest(classes = CategoryServiceImpl.class)
class CategoryServiceImplTest {
    @Autowired
    private CategoryServiceImpl categoryService;
    @MockBean
    private CategoryRepository categoryRepository;

    @Test
    void shouldGetCategoryById() {
        Category expected = Category.builder()
                .id(1)
                .name(TEST_CATEGORY)
                .build();

        when(categoryRepository.findById(expected.getId()))
                .thenReturn(Optional.of(expected));

        Category actual = categoryService.getById(expected.getId());
        assertEquals(expected, actual);

        verify(categoryRepository, times(1))
                .findById(expected.getId());
    }

    @Test
    void shouldNotGetCategoryByNotExistedId() {
        Category expected = Category.builder()
                .id(1)
                .name(TEST_CATEGORY)
                .build();

        when(categoryRepository.findById(expected.getId()))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                categoryService.getById(expected.getId()),
                String.format("There is no category by id: %s",
                expected.getId()));

        verify(categoryRepository, times(1))
                .findById(expected.getId());
    }

}