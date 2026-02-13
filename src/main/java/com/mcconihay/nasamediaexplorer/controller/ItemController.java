package com.mcconihay.nasamediaexplorer.controller;

import com.mcconihay.nasamediaexplorer.dto.ItemRequest;
import com.mcconihay.nasamediaexplorer.dto.ItemResponse;
import com.mcconihay.nasamediaexplorer.entity.ItemEntity;
import com.mcconihay.nasamediaexplorer.exception.ResourceNotFoundException;
import com.mcconihay.nasamediaexplorer.mapper.ItemMapper;
import com.mcconihay.nasamediaexplorer.service.ItemService;
import org.springframework.data.domain.Sort;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Provides endpoints for managing stored NASA media items.
 */
@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemService itemService;

    public ItemController(ItemService itemService) {
        this.itemService = itemService;
    }

    /**
     * Saves a new item.
     */
    @PreAuthorize("hasAnyRole('ADMIN','CONTRIBUTOR')")
    @PostMapping
    public ItemResponse save(@RequestBody ItemRequest request) {

        ItemEntity saved = itemService.saveItem(ItemMapper.toEntity(request));
        return ItemMapper.toResponse(saved);
    }

    /**
     * Retrieves an item by ID.
     */
    @PreAuthorize("hasAnyRole('ADMIN','CONTRIBUTOR','READONLY')")
    @GetMapping("/{id:\\d+}")
    public ItemResponse getById(@PathVariable Long id) {

        ItemEntity item = itemService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found: " + id));

        return ItemMapper.toResponse(item);
    }

    /**
     * Retrieves all items with pagination.
     */
    @PreAuthorize("hasAnyRole('ADMIN','CONTRIBUTOR','READONLY')")
    @GetMapping
    public Page<ItemResponse> getAll(
            @PageableDefault(size = 20, sort = "fetchedAt", direction = Sort.Direction.DESC)
            Pageable pageable) {

        return itemService.findAll(pageable)
                .map(ItemMapper::toResponse);
    }

    /**
     * Deletes an item.
     */
    @PreAuthorize("hasAnyRole('ADMIN','CONTRIBUTOR')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        itemService.deleteItem(id);
    }

    /**
     * Searches items by title.
     */
    @GetMapping("/search")
    public Page<ItemResponse> search(
            @RequestParam String q,
            @PageableDefault(size = 20, sort = "fetchedAt", direction = Sort.Direction.DESC)
            Pageable pageable) {

        return itemService.searchByTitle(q, pageable)
                .map(ItemMapper::toResponse);
    }
}