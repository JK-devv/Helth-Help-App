package com.example.healthhelpapp.service.impl;

import com.example.healthhelpapp.dto.Item;
import com.example.healthhelpapp.dto.mapper.DtoMapper;
import com.example.healthhelpapp.dto.mapper.ResponseMapper;
import com.example.healthhelpapp.dto.request.ItemRequestDto;
import com.example.healthhelpapp.dto.response.ItemResponseDto;
import com.example.healthhelpapp.repository.ItemRepository;
import com.example.healthhelpapp.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final ResponseMapper<ItemResponseDto, Item> responseMapper;
    private final DtoMapper<Item, ItemRequestDto> dtoMapper;

    @Override
    public List<ItemResponseDto> getItemsByCategory(String categoryName) {
        return itemRepository.findByCategoryName(categoryName)
                .stream()
                .map(responseMapper::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public ItemResponseDto getById(Integer id) {
        return responseMapper.mapToResponse(itemRepository.findById(id)
                .orElseThrow(
                        () -> new RuntimeException(
                                String.format("There is no item by id: %s", id))));
    }

    @Override
    public ItemResponseDto create(ItemRequestDto itemRequestDto) {
        return responseMapper.mapToResponse(
                itemRepository.save(
                        dtoMapper.mapToDto(itemRequestDto)));
    }
}
