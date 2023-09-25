package com.example.healthhelpapp.service.impl;

import com.example.healthhelpapp.dto.Category;
import com.example.healthhelpapp.dto.Item;
import com.example.healthhelpapp.dto.mapper.DtoMapper;
import com.example.healthhelpapp.dto.mapper.ResponseMapper;
import com.example.healthhelpapp.dto.request.ItemRequestDto;
import com.example.healthhelpapp.dto.response.ItemResponseDto;
import com.example.healthhelpapp.repository.ItemRepository;
import com.example.healthhelpapp.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static com.example.healthhelpapp.TestConstant.TEST_CATEGORY;
import static com.example.healthhelpapp.TestConstant.TEST_NAME;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@SpringBootTest(classes = {ItemServiceImpl.class})
class ItemServiceImplTest {
    @Autowired
    private ItemServiceImpl itemService;
    @MockBean
    private ResponseMapper<ItemResponseDto, Item> responseMapper;
    @MockBean
    private DtoMapper<Item, ItemRequestDto> dtoMapper;
    @MockBean
    private ItemRepository itemRepository;
    @MockBean
    private CategoryService categoryService;

    @Test
    void shouldGetItemsByCategory() {
        Category category = Category.builder()
                .name(TEST_CATEGORY)
                .build();

        Item item = Item.builder()
                .category(category)
                .count(1)
                .name(TEST_NAME)
                .build();
        ItemResponseDto itemResponseDto = ItemResponseDto.builder()
                .name(TEST_NAME)
                .count(1)
                .categoryName(category.getName())
                .build();

        when(itemRepository.findByCategoryName(category.getName()))
                .thenReturn(List.of(item));
        when(responseMapper.mapToResponse(item)).thenReturn(itemResponseDto);

        List<ItemResponseDto> actual = itemService.getItemsByCategory(TEST_CATEGORY);

        assertEquals(1, actual.size());
        assertEquals(item.getName(), actual.get(0).getName());

        verify(responseMapper, times(1)).mapToResponse(item);
        verify(itemRepository, times(1)).findByCategoryName(category.getName());
    }

    @Test
    void shouldNotReturnItemByNotExistedId() {
        when(itemRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () ->
                itemService.getById(1),
                String.format("There is no item by id: %s", 1));

        verify(itemRepository, times(1)).findById(1);
    }

    @Test
    void shouldReturnItemById() {
        Category category = Category.builder()
                .id(1)
                .name(TEST_CATEGORY)
                .build();
        Item item = Item.builder()
                .id(1)
                .name(TEST_NAME)
                .category(category)
                .count(1)
                .build();
        ItemResponseDto itemResponseDto = ItemResponseDto.builder()
                .name(TEST_NAME)
                .count(1)
                .categoryName(category.getName())
                .build();
        when(itemRepository.findById(item.getId()))
                .thenReturn(Optional.of(item));
        when(responseMapper.mapToResponse(item)).thenReturn(itemResponseDto);

        ItemResponseDto actual = itemService.getById(item.getId());

        assertEquals(item.getName(), actual.getName());
        assertEquals(item.getCount(), actual.getCount());
        assertEquals(item.getCategory().getName(), actual.getCategoryName());
        verify(responseMapper, times(1)).mapToResponse(item);
        verify(itemRepository, times(1)).findById(item.getId());
    }

    @Test
    void shouldCreateItem() {
        ItemRequestDto requestDto = ItemRequestDto.builder()
                .name(TEST_NAME)
                .count(1)
                .build();
        Category category = Category.builder()
                .id(2)
                .name(TEST_CATEGORY)
                .build();

        Item item = Item.builder()
                .count(1)
                .name(TEST_NAME)
                .category(category)
                .build();
        ItemResponseDto itemResponseDto = ItemResponseDto.builder()
                .name(TEST_NAME)
                .count(1)
                .categoryName(category.getName())
                .build();

        when(itemRepository.save(any()))
                .thenReturn(item);
        when(responseMapper.mapToResponse(item)).thenReturn(itemResponseDto);

        ItemResponseDto actual = itemService.create(requestDto);

        assertEquals(item.getName(), actual.getName());
        assertEquals(item.getCategory().getName(), actual.getCategoryName());

        verify(itemRepository, times(1)).save(any());
        verify(responseMapper, times(1)).mapToResponse(item);
    }
}