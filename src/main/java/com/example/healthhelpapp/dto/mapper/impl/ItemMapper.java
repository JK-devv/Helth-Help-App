package com.example.healthhelpapp.dto.mapper.impl;

import com.example.healthhelpapp.dto.Item;
import com.example.healthhelpapp.dto.mapper.DtoMapper;
import com.example.healthhelpapp.dto.mapper.ResponseMapper;
import com.example.healthhelpapp.dto.request.ItemRequestDto;
import com.example.healthhelpapp.dto.response.ItemResponseDto;
import com.example.healthhelpapp.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ItemMapper implements ResponseMapper<ItemResponseDto, Item>,
        DtoMapper<Item, ItemRequestDto> {

    private final CategoryService categoryService;

    public ItemResponseDto mapToResponse(Item item) {
        return ItemResponseDto.builder()
                .id(item.getId())
                .categoryName(item.getCategory().getName())
                .count(item.getCount())
                .name(item.getName())
                .build();
    }

    @Override
    public Item mapToDto(ItemRequestDto value) {
        return Item.builder()
                .name(value.getName())
                .count(value.getCount())
                .category(categoryService.getById(2))
                .build();
    }
}
