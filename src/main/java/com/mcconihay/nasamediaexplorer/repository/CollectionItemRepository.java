package com.mcconihay.nasamediaexplorer.repository;

import com.mcconihay.nasamediaexplorer.entity.CollectionItemEntity;
import com.mcconihay.nasamediaexplorer.entity.CollectionItemId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for managing item membership within collections.
 *
 * Provides CRUD access to the collection_items join table.
 */
public interface CollectionItemRepository
        extends JpaRepository<CollectionItemEntity, CollectionItemId> {

    /**
     * Finds all join records for a given collection.
     */
    List<CollectionItemEntity> findByCollectionCollectionId(Long collectionId);

    /**
     * Finds all join records for a given item.
     */
    List<CollectionItemEntity> findByItemItemId(Long itemId);
    
    void deleteById(CollectionItemId id);
}