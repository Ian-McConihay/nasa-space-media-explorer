package com.mcconihay.nasamediaexplorer.service.impl;

import com.mcconihay.nasamediaexplorer.entity.ItemEntity;
import com.mcconihay.nasamediaexplorer.entity.NasaSourceApi;
import com.mcconihay.nasamediaexplorer.exception.ResourceNotFoundException;
import com.mcconihay.nasamediaexplorer.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ItemServiceImplTest {

    @Mock
    private ItemRepository itemRepository;

    @InjectMocks
    private ItemServiceImpl itemService;

    @Test
    void saveItem_ShouldSaveItem() {
        ItemEntity item = new ItemEntity();
        item.setTitle("Mars");

        when(itemRepository.save(item)).thenReturn(item);

        ItemEntity result = itemService.saveItem(item);

        assertNotNull(result);
        assertEquals("Mars", result.getTitle());
        verify(itemRepository).save(item);
    }

    @Test
    void findById_ShouldReturnItem_WhenFound() {
        ItemEntity item = new ItemEntity();
        item.setItemId(1L);

        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        Optional<ItemEntity> result = itemService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getItemId());
    }

    @Test
    void findAll_ShouldReturnPageOfItems() {
        Pageable pageable = PageRequest.of(0, 20);
        Page<ItemEntity> page = new PageImpl<>(java.util.List.of(new ItemEntity(), new ItemEntity()));

        when(itemRepository.findAll(pageable)).thenReturn(page);

        Page<ItemEntity> result = itemService.findAll(pageable);

        assertEquals(2, result.getContent().size());
    }

    @Test
    void deleteItem_ShouldDelete_WhenItemExists() {
        ItemEntity item = new ItemEntity();
        item.setItemId(1L);

        when(itemRepository.findById(1L)).thenReturn(Optional.of(item));

        itemService.deleteItem(1L);

        verify(itemRepository).delete(item);
    }

    @Test
    void deleteItem_ShouldThrow_WhenItemDoesNotExist() {
        when(itemRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> itemService.deleteItem(1L));
        verify(itemRepository, never()).delete(any());
    }

    @Test
    void searchByTitle_ShouldReturnMatchingItems() {
        Pageable pageable = PageRequest.of(0, 20);
        Page<ItemEntity> page = new PageImpl<>(java.util.List.of(new ItemEntity()));

        when(itemRepository.findByTitleContainingIgnoreCase("mars", pageable)).thenReturn(page);

        Page<ItemEntity> result = itemService.searchByTitle("mars", pageable);

        assertEquals(1, result.getContent().size());
    }

    @Test
    void searchItems_ShouldNormalizeBlankFiltersToNull() {
        Pageable pageable = PageRequest.of(0, 20);
        Page<ItemEntity> page = new PageImpl<>(java.util.List.of());

        when(itemRepository.searchItems(null, null, NasaSourceApi.APOD, pageable)).thenReturn(page);

        Page<ItemEntity> result = itemService.searchItems("   ", "", NasaSourceApi.APOD, pageable);

        assertNotNull(result);
        verify(itemRepository).searchItems(eq(null), eq(null), eq(NasaSourceApi.APOD), any(Pageable.class));    }
}