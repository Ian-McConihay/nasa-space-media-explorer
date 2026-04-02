package com.mcconihay.nasamediaexplorer.service.impl;

import com.mcconihay.nasamediaexplorer.entity.*;
import com.mcconihay.nasamediaexplorer.exception.ResourceNotFoundException;
import com.mcconihay.nasamediaexplorer.repository.CollectionItemRepository;
import com.mcconihay.nasamediaexplorer.repository.CollectionRepository;
import com.mcconihay.nasamediaexplorer.repository.ItemRepository;
import com.mcconihay.nasamediaexplorer.service.CollectionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service implementation for managing user collections.
 * Handles business logic for collection creation, deletion,
 * and item association.
 */
@Service
@Transactional
public class CollectionServiceImpl implements CollectionService {

    private final CollectionRepository collectionRepository;
    private final ItemRepository itemRepository;
    private final CollectionItemRepository collectionItemRepository;

    /**
     * Constructor for CollectionServiceImpl.
     */
    public CollectionServiceImpl(CollectionRepository collectionRepository,
                                 ItemRepository itemRepository,
                                 CollectionItemRepository collectionItemRepository) {
        this.collectionRepository = collectionRepository;
        this.itemRepository = itemRepository;
        this.collectionItemRepository = collectionItemRepository;
    }

    /**
     * Creates and persists a new collection.
     */
    @Override
    public CollectionEntity createCollection(CollectionEntity collection) {
        return collectionRepository.save(collection);
    }

    /**
     * Retrieves all collections for a given user.
     */
    @Override
    public List<CollectionEntity> findByUserId(Long userId) {
        return collectionRepository.findByUserUserId(userId);
    }

    /**
     * Finds a collection by ID.
     */
    @Override
    public java.util.Optional<CollectionEntity> findById(Long id) {
        return collectionRepository.findById(id);
    }

    /**
     * Deletes a collection if it exists.
     */
    @Override
    public void deleteCollection(Long id) {
        CollectionEntity existing = collectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Collection not found: " + id));
        collectionRepository.delete(existing);
    }

    /**
     * Adds an item to a collection.
     * Prevents duplicate relationships.
     */
    @Override
    public void addItemToCollection(Long collectionId, Long itemId) {

        CollectionEntity collection =
                collectionRepository.findById(collectionId)
                        .orElseThrow(() -> new ResourceNotFoundException("Collection not found: " + collectionId));

        ItemEntity item =
                itemRepository.findById(itemId)
                        .orElseThrow(() -> new ResourceNotFoundException("Item not found: " + itemId));

        CollectionItemId id = new CollectionItemId(collectionId, itemId);

        // Prevent duplicates
        if (collectionItemRepository.existsById(id)) {
            return;
        }

        CollectionItemEntity join = new CollectionItemEntity();
        join.setId(id);
        join.setCollection(collection);
        join.setItem(item);

        collectionItemRepository.save(join);
    }

    /**
     * Removes an item from a collection.
     */
    @Override
    public void removeItemFromCollection(Long collectionId, Long itemId) {
        CollectionItemId id = new CollectionItemId(collectionId, itemId);

        if (!collectionItemRepository.existsById(id)) {
            return;
        }

        collectionItemRepository.deleteById(id);
    }

    /**
     * Retrieves all items associated with a collection.
     */
    @Override
    public List<ItemEntity> getItemsInCollection(Long collectionId) {

        if (!collectionRepository.existsById(collectionId)) {
            throw new ResourceNotFoundException("Collection not found: " + collectionId);
        }

        return collectionItemRepository
                .findByCollectionCollectionId(collectionId)
                .stream()
                .map(CollectionItemEntity::getItem)
                .toList();
    }
}