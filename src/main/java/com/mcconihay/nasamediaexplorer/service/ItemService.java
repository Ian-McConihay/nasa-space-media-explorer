package com.mcconihay.nasamediaexplorer.service;

import com.mcconihay.nasamediaexplorer.entity.ItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service responsible for managing NASA media items.
 *
 * Supports persistence, retrieval, deletion,
 * and paginated search operations.
 */
public interface ItemService {

    /**
     * Saves an item to the database.
     *
     * @param item item to persist
     * @return saved item
     */
    ItemEntity saveItem(ItemEntity item);

    /**
     * Finds an item by identifier.
     *
     * @param id item identifier
     * @return optional containing the item if found
     */
    Optional<ItemEntity> findById(Long id);

    /**
     * Retrieves all items using pagination.
     *
     * @param pageable paging configuration
     * @return page of items
     */
    Page<ItemEntity> findAll(Pageable pageable);

    /**
     * Deletes an item by identifier.
     *
     * @param id item identifier
     */
    void deleteItem(Long id);

    /**
     * Searches items by title.
     *
     * @param query search term
     * @param pageable paging configuration
     * @return page of matching items
     */
    Page<ItemEntity> searchByTitle(String query, Pageable pageable);
}