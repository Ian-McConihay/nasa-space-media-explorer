package com.mcconihay.nasamediaexplorer.service;

import com.mcconihay.nasamediaexplorer.entity.CollectionEntity;
import com.mcconihay.nasamediaexplorer.entity.ItemEntity;

import java.util.List;
import java.util.Optional;

/**
 * Service responsible for managing user collections.
 *
 * Handles creation, lookup, and deletion of collections
 * that group NASA items.
 */
public interface CollectionService {

    /**
     * Creates a new collection.
     *
     * @param collection collection to create
     * @return persisted collection
     */
    CollectionEntity createCollection(CollectionEntity collection);

    /**
     * Retrieves all collections owned by a user.
     *
     * @param userId user identifier
     * @return list of collections
     */
    List<CollectionEntity> findByUserId(Long userId);

    /**
     * Finds a collection by identifier.
     *
     * @param id collection identifier
     * @return optional containing the collection if found
     */
    Optional<CollectionEntity> findById(Long id);

    /**
     * Deletes a collection.
     *
     * @param id collection identifier
     */
    void deleteCollection(Long id);
    
    void addItemToCollection(Long collectionId, Long itemId);
    
    void removeItemFromCollection(Long collectionId, Long itemId);

    List<ItemEntity> getItemsInCollection(Long collectionId);
}