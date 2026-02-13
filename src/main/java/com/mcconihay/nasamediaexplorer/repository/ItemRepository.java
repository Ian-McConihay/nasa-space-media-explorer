package com.mcconihay.nasamediaexplorer.repository;

import com.mcconihay.nasamediaexplorer.entity.ItemEntity;
import com.mcconihay.nasamediaexplorer.entity.NasaSourceApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;
import java.util.List;

/**
 * Repository for ItemEntity persistence.
 *
 * Includes query methods used during NASA data ingestion
 * to prevent duplicates and support search functionality.
 */
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    /**
     * Finds an item using the external NASA identifier
     * and source API.
     *
     * Used to enforce idempotent ingestion.
     *
     * @param sourceApi source system
     * @param nasaId external identifier
     * @return matching item if present
     */
    Optional<ItemEntity> findBySourceApiAndNasaId(
            NasaSourceApi sourceApi,
            String nasaId);

    /**
     * Retrieves all items from a specific NASA API.
     *
     * @param sourceApi source system
     * @return list of items
     */
    List<ItemEntity> findBySourceApi(NasaSourceApi sourceApi);

    /**
     * Checks if an item already exists.
     *
     * Used before saving newly ingested data.
     *
     * @param sourceApi source system
     * @param nasaId external identifier
     * @return true if the item exists
     */
    boolean existsBySourceApiAndNasaId(
            NasaSourceApi sourceApi,
            String nasaId);

    /**
     * Performs a case-insensitive title search.
     *
     * @param title search term
     * @return matching items
     */
    List<ItemEntity> findByTitleContainingIgnoreCase(String title);

    /**
     * Performs a paginated title search.
     *
     * @param title search term
     * @param pageable pagination configuration
     * @return page of matching items
     */
    Page<ItemEntity> findByTitleContainingIgnoreCase(
            String title,
            Pageable pageable);
}