package com.mcconihay.nasamediaexplorer.controller;

import com.mcconihay.nasamediaexplorer.dto.AnnotationRequest;
import com.mcconihay.nasamediaexplorer.dto.AnnotationResponse;
import com.mcconihay.nasamediaexplorer.entity.AnnotationEntity;
import com.mcconihay.nasamediaexplorer.entity.ItemEntity;
import com.mcconihay.nasamediaexplorer.entity.UserEntity;
import com.mcconihay.nasamediaexplorer.exception.ResourceNotFoundException;
import com.mcconihay.nasamediaexplorer.mapper.AnnotationMapper;
import com.mcconihay.nasamediaexplorer.service.AnnotationService;
import com.mcconihay.nasamediaexplorer.service.ItemService;
import com.mcconihay.nasamediaexplorer.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Handles annotation operations for items.
 *
 * Annotations allow users to attach notes to stored NASA media.
 */
@RestController
@RequestMapping("/api/annotations")
public class AnnotationController {

    private final AnnotationService annotationService;
    private final UserService userService;
    private final ItemService itemService;

    public AnnotationController(AnnotationService annotationService,
                                UserService userService,
                                ItemService itemService) {
        this.annotationService = annotationService;
        this.userService = userService;
        this.itemService = itemService;
    }

    /**
     * Creates a new annotation for an item.
     */
    @PreAuthorize("hasAnyRole('ADMIN','CONTRIBUTOR')")
    @PostMapping
    public AnnotationResponse create(@RequestBody AnnotationRequest request) {

        UserEntity user = userService.findById(request.userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + request.userId));

        ItemEntity item = itemService.findById(request.itemId)
                .orElseThrow(() -> new ResourceNotFoundException("Item not found: " + request.itemId));

        AnnotationEntity saved =
                annotationService.saveAnnotation(AnnotationMapper.toEntity(request, user, item));

        return AnnotationMapper.toResponse(saved);
    }

    /**
     * Retrieves annotations associated with a specific item.
     */
    @PreAuthorize("hasAnyRole('ADMIN','CONTRIBUTOR','READONLY')")
    @GetMapping("/by-item/{itemId}")
    public List<AnnotationResponse> byItem(@PathVariable Long itemId) {
        return annotationService.findByItemId(itemId)
                .stream()
                .map(AnnotationMapper::toResponse)
                .toList();
    }

    /**
     * Deletes an annotation.
     */
    @PreAuthorize("hasAnyRole('ADMIN','CONTRIBUTOR')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        annotationService.deleteAnnotation(id);
    }
}