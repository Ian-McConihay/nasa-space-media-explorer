package com.mcconihay.nasamediaexplorer.repository;

import com.mcconihay.nasamediaexplorer.entity.CollectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository responsible for CollectionEntity persistence.
 *
 * Supports CRUD operations and retrieval of collections
 * belonging to a specific user.
 */
public interface CollectionRepository extends JpaRepository<CollectionEntity, Long> {

    /**
     * Finds all collections owned by the given user.
     *
     * @param userId user identifier
     * @return list of collections
     */
    List<CollectionEntity> findByUserUserId(Long userId);
    
    Optional<CollectionEntity> findByCollectionIdAndUserUserId(Long collectionId, Long userId);

}