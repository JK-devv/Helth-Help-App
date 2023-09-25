package com.example.healthhelpapp.service.impl;

import com.example.healthhelpapp.dto.Category;
import com.example.healthhelpapp.repository.CategoryRepository;
import com.example.healthhelpapp.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    @Override
    public Category getById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException(
                        String.format("There is no category by id: %s", id)));
    }
}
