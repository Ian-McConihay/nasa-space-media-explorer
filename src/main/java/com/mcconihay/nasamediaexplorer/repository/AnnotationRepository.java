package com.mcconihay.nasamediaexplorer.repository;

import com.mcconihay.nasamediaexplorer.entity.AnnotationEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Repository for managing AnnotationEntity persistence.
 *
 * Provides standard CRUD operations along with query methods
 * for retrieving annotations by item or user.
 */
public interface AnnotationRepository extends JpaRepository<AnnotationEntity, Long> {

    /**
     * Retrieves all annotations associated with a specific item.
     *
     * @param itemId item identifier
     * @return list of annotations
     */
    List<AnnotationEntity> findByItemItemId(Long itemId);

    /**
     * Retrieves all annotations created by a specific user.
     *
     * @param userId user identifier
     * @return list of annotations
     */
    List<AnnotationEntity> findByUserUserId(Long userId);
}