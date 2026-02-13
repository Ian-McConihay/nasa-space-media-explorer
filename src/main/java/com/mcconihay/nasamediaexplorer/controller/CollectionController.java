package com.mcconihay.nasamediaexplorer.controller;

import com.mcconihay.nasamediaexplorer.dto.CollectionRequest;
import com.mcconihay.nasamediaexplorer.dto.CollectionResponse;
import com.mcconihay.nasamediaexplorer.entity.CollectionEntity;
import com.mcconihay.nasamediaexplorer.entity.UserEntity;
import com.mcconihay.nasamediaexplorer.exception.ResourceNotFoundException;
import com.mcconihay.nasamediaexplorer.mapper.CollectionMapper;
import com.mcconihay.nasamediaexplorer.service.CollectionService;
import com.mcconihay.nasamediaexplorer.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Manages user collections of saved items.
 *
 * Collections allow users to group media for later reference.
 */
@RestController
@RequestMapping("/api/collections")
public class CollectionController {

    private final CollectionService collectionService;
    private final UserService userService;

    public CollectionController(CollectionService collectionService,
                                UserService userService) {
        this.collectionService = collectionService;
        this.userService = userService;
    }

    /**
     * Creates a new collection for a user.
     */
    @PreAuthorize("hasAnyRole('ADMIN','CONTRIBUTOR')")
    @PostMapping
    public CollectionResponse create(@RequestBody CollectionRequest request) {

        UserEntity user = userService.findById(request.userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + request.userId));

        CollectionEntity created =
                collectionService.createCollection(CollectionMapper.toEntity(request, user));

        return CollectionMapper.toResponse(created);
    }

    /**
     * Retrieves collections belonging to a user.
     */
    @PreAuthorize("hasAnyRole('ADMIN','CONTRIBUTOR','READONLY')")
    @GetMapping("/by-user/{userId}")
    public List<CollectionResponse> byUser(@PathVariable Long userId) {
        return collectionService.findByUserId(userId)
                .stream()
                .map(CollectionMapper::toResponse)
                .toList();
    }

    /**
     * Retrieves a collection by ID.
     */
    @PreAuthorize("hasAnyRole('ADMIN','CONTRIBUTOR','READONLY')")
    @GetMapping("/{id}")
    public CollectionResponse getById(@PathVariable Long id) {

        CollectionEntity c = collectionService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Collection not found: " + id));

        return CollectionMapper.toResponse(c);
    }

    /**
     * Deletes a collection.
     */
    @PreAuthorize("hasAnyRole('ADMIN','CONTRIBUTOR')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        collectionService.deleteCollection(id);
    }
}