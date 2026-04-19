package com.mcconihay.nasamediaexplorer.service.impl;

import com.mcconihay.nasamediaexplorer.entity.*;
import com.mcconihay.nasamediaexplorer.exception.ResourceNotFoundException;
import com.mcconihay.nasamediaexplorer.repository.CollectionItemRepository;
import com.mcconihay.nasamediaexplorer.repository.CollectionRepository;
import com.mcconihay.nasamediaexplorer.repository.ItemRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CollectionServiceImplTest {

    @Mock
    private CollectionRepository collectionRepository;

    @Mock
    private ItemRepository itemRepository;

    @Mock
    private CollectionItemRepository collectionItemRepository;

    @InjectMocks
    private CollectionServiceImpl collectionService;

    @Test
    void createCollection_ShouldSaveCollection() {
        CollectionEntity collection = new CollectionEntity();
        collection.setName("Mars Research");

        when(collectionRepository.save(collection)).thenReturn(collection);

        CollectionEntity result = collectionService.createCollection(collection);

        assertNotNull(result);
        assertEquals("Mars Research", result.getName());
        verify(collectionRepository).save(collection);
    }

    @Test
    void findByUserId_ShouldReturnCollections() {
        when(collectionRepository.findByUserUserId(1L))
                .thenReturn(List.of(new CollectionEntity(), new CollectionEntity()));

        List<CollectionEntity> result = collectionService.findByUserId(1L);

        assertEquals(2, result.size());
    }

    @Test
    void deleteCollection_ShouldDelete_WhenCollectionExists() {
        CollectionEntity collection = new CollectionEntity();
        collection.setCollectionId(1L);

        when(collectionRepository.findById(1L)).thenReturn(Optional.of(collection));

        collectionService.deleteCollection(1L);

        verify(collectionRepository).delete(collection);
    }

    @Test
    void deleteCollection_ShouldThrow_WhenCollectionMissing() {
        when(collectionRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> collectionService.deleteCollection(1L));
        verify(collectionRepository, never()).delete(any());
    }

    @Test
    void addItemToCollection_ShouldSaveJoin_WhenCollectionAndItemExist() {
        CollectionEntity collection = new CollectionEntity();
        collection.setCollectionId(1L);

        ItemEntity item = new ItemEntity();
        item.setItemId(2L);

        when(collectionRepository.findById(1L)).thenReturn(Optional.of(collection));
        when(itemRepository.findById(2L)).thenReturn(Optional.of(item));
        when(collectionItemRepository.existsById(any(CollectionItemId.class))).thenReturn(false);

        collectionService.addItemToCollection(1L, 2L);

        verify(collectionItemRepository).save(any(CollectionItemEntity.class));
    }

    @Test
    void addItemToCollection_ShouldNotSaveDuplicateJoin() {
        CollectionEntity collection = new CollectionEntity();
        collection.setCollectionId(1L);

        ItemEntity item = new ItemEntity();
        item.setItemId(2L);

        when(collectionRepository.findById(1L)).thenReturn(Optional.of(collection));
        when(itemRepository.findById(2L)).thenReturn(Optional.of(item));
        when(collectionItemRepository.existsById(any(CollectionItemId.class))).thenReturn(true);

        collectionService.addItemToCollection(1L, 2L);

        verify(collectionItemRepository, never()).save(any(CollectionItemEntity.class));
    }

    @Test
    void removeItemFromCollection_ShouldDelete_WhenJoinExists() {
        when(collectionItemRepository.existsById(any(CollectionItemId.class))).thenReturn(true);

        collectionService.removeItemFromCollection(1L, 2L);

        verify(collectionItemRepository).deleteById(any(CollectionItemId.class));
    }

    @Test
    void removeItemFromCollection_ShouldDoNothing_WhenJoinDoesNotExist() {
        when(collectionItemRepository.existsById(any(CollectionItemId.class))).thenReturn(false);

        collectionService.removeItemFromCollection(1L, 2L);

        verify(collectionItemRepository, never()).deleteById(any(CollectionItemId.class));
    }

    @Test
    void getItemsInCollection_ShouldReturnMappedItems() {
        ItemEntity item1 = new ItemEntity();
        item1.setItemId(1L);

        ItemEntity item2 = new ItemEntity();
        item2.setItemId(2L);

        CollectionItemEntity join1 = new CollectionItemEntity();
        join1.setItem(item1);

        CollectionItemEntity join2 = new CollectionItemEntity();
        join2.setItem(item2);

        when(collectionRepository.existsById(1L)).thenReturn(true);
        when(collectionItemRepository.findByCollectionCollectionId(1L))
                .thenReturn(List.of(join1, join2));

        List<ItemEntity> result = collectionService.getItemsInCollection(1L);

        assertEquals(2, result.size());
        assertEquals(1L, result.get(0).getItemId());
        assertEquals(2L, result.get(1).getItemId());
    }

    @Test
    void getItemsInCollection_ShouldThrow_WhenCollectionMissing() {
        when(collectionRepository.existsById(1L)).thenReturn(false);

        assertThrows(ResourceNotFoundException.class, () -> collectionService.getItemsInCollection(1L));
    }
}