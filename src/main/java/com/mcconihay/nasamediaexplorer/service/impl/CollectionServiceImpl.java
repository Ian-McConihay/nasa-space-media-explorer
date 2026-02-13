package com.mcconihay.nasamediaexplorer.service.impl;

import com.mcconihay.nasamediaexplorer.entity.CollectionEntity;
import com.mcconihay.nasamediaexplorer.exception.ResourceNotFoundException;
import com.mcconihay.nasamediaexplorer.repository.CollectionRepository;
import com.mcconihay.nasamediaexplorer.service.CollectionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
/**
 * Default implementation of {@link CollectionService}.
 *
 * Manages user-defined collections that group NASA items.
 */
public class CollectionServiceImpl implements CollectionService {

    private final CollectionRepository collectionRepository;

    /**
     * Constructs the service with repository dependency.
     */
    public CollectionServiceImpl(CollectionRepository collectionRepository) {
        this.collectionRepository = collectionRepository;
    }

    /**
     * Creates and persists a collection.
     */
    @Override
    public CollectionEntity createCollection(CollectionEntity collection) {
        return collectionRepository.save(collection);
    }

    /**
     * Retrieves collections owned by a user.
     */
    @Override
    public List<CollectionEntity> findByUserId(Long userId) {
        return collectionRepository.findByUserUserId(userId);
    }

    /**
     * Finds a collection by identifier.
     */
    @Override
    public Optional<CollectionEntity> findById(Long id) {
        return collectionRepository.findById(id);
    }

    /**
     * Deletes a collection if present.
     */
    @Override
    public void deleteCollection(Long id) {
        CollectionEntity existing = collectionRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Collection not found: " + id));
        collectionRepository.delete(existing);
    }
}