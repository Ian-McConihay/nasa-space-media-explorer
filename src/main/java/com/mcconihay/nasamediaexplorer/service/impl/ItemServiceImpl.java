package com.mcconihay.nasamediaexplorer.service.impl;

import com.mcconihay.nasamediaexplorer.entity.ItemEntity;
import com.mcconihay.nasamediaexplorer.entity.NasaSourceApi;
import com.mcconihay.nasamediaexplorer.exception.ResourceNotFoundException;
import com.mcconihay.nasamediaexplorer.repository.ItemRepository;
import com.mcconihay.nasamediaexplorer.service.ItemService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
/**
 * Default implementation of {@link ItemService}.
 *
 * Provides CRUD and search operations for NASA media items.
 */
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    /**
     * Constructs the service with repository dependency.
     */
    public ItemServiceImpl(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    /**
     * Saves an item.
     */
    @Override
    public ItemEntity saveItem(ItemEntity item) {
        return itemRepository.save(item);
    }

    /**
     * Finds an item by identifier.
     */
    @Override
    public Optional<ItemEntity> findById(Long id) {
        return itemRepository.findById(id);
    }

    /**
     * Retrieves all items using pagination.
     */
    @Override
    public Page<ItemEntity> findAll(Pageable pageable) {
        return itemRepository.findAll(pageable);
    }

    /**
     * Deletes an item if it exists.
     */
    @Override
    public void deleteItem(Long id) {
        ItemEntity existing = itemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found: " + id));
        itemRepository.delete(existing);
    }

    /**
     * Searches items by title.
     */
    @Override
    public Page<ItemEntity> searchByTitle(String query, Pageable pageable) {
        return itemRepository.findByTitleContainingIgnoreCase(query, pageable);
    }

    /**
     * Search items with optional filters.
     */
    @Override
    public Page<ItemEntity> searchItems(
            String title,
            String mediaType,
            NasaSourceApi sourceApi,
            Pageable pageable) {

        String titleFilter =
                (title == null || title.isBlank()) ? null : title;

        String mediaTypeFilter =
                (mediaType == null || mediaType.isBlank()) ? null : mediaType;

        return itemRepository.searchItems(
                titleFilter,
                mediaTypeFilter,
                sourceApi,
                pageable);
    }
}