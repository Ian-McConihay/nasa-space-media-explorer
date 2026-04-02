package com.mcconihay.nasamediaexplorer.repository;

import com.mcconihay.nasamediaexplorer.entity.ItemEntity;
import com.mcconihay.nasamediaexplorer.entity.NasaSourceApi;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.List;

/**
 * Repository for ItemEntity persistence.
 *
 * Includes query methods used during NASA data ingestion
 * and flexible search/filter operations.
 */
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    Optional<ItemEntity> findBySourceApiAndNasaId(
            NasaSourceApi sourceApi,
            String nasaId);

    List<ItemEntity> findBySourceApi(NasaSourceApi sourceApi);

    boolean existsBySourceApiAndNasaId(
            NasaSourceApi sourceApi,
            String nasaId);

    List<ItemEntity> findByTitleContainingIgnoreCase(String title);

    Page<ItemEntity> findByTitleContainingIgnoreCase(
            String title,
            Pageable pageable);

    /**
     * Flexible search query supporting optional filters.
     */
    @Query("""
        SELECT i FROM ItemEntity i
        WHERE (:title IS NULL OR LOWER(i.title) LIKE LOWER(CONCAT('%', :title, '%')))
        AND (:mediaType IS NULL OR LOWER(i.mediaType) = LOWER(:mediaType))
        AND (:sourceApi IS NULL OR i.sourceApi = :sourceApi)
        """)
    Page<ItemEntity> searchItems(
            @Param("title") String title,
            @Param("mediaType") String mediaType,
            @Param("sourceApi") NasaSourceApi sourceApi,
            Pageable pageable);
}