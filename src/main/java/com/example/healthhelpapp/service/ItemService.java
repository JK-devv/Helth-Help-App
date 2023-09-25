package com.example.healthhelpapp.service;

import com.example.healthhelpapp.dto.request.ItemRequestDto;
import com.example.healthhelpapp.dto.response.ItemResponseDto;

import java.util.List;

public interface ItemService {

    List<ItemResponseDto> getItemsByCategory(String categoryName);

    ItemResponseDto getById(Integer id);

    ItemResponseDto create(ItemRequestDto itemRequestDto);
}
