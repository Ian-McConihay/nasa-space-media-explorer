package com.mcconihay.nasamediaexplorer.controller.view;

import com.mcconihay.nasamediaexplorer.entity.CollectionEntity;
import com.mcconihay.nasamediaexplorer.entity.UserEntity;
import com.mcconihay.nasamediaexplorer.exception.ResourceNotFoundException;
import com.mcconihay.nasamediaexplorer.repository.CollectionRepository;
import com.mcconihay.nasamediaexplorer.service.CollectionService;
import com.mcconihay.nasamediaexplorer.service.UserService;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller responsible for managing user collections.
 * Supports creating, viewing, deleting, and modifying collections.
 */
@Controller
@RequestMapping("/collections")
public class CollectionViewController {

    private final CollectionService collectionService;
    private final UserService userService;
    private final CollectionRepository collectionRepository;

    /**
     * Constructor for CollectionViewController.
     */
    public CollectionViewController(
            CollectionService collectionService,
            UserService userService,
            CollectionRepository collectionRepository) {

        this.collectionService = collectionService;
        this.userService = userService;
        this.collectionRepository = collectionRepository;
    }

    /**
     * Displays all collections for the authenticated user.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public String viewCollections(Authentication authentication, Model model) {

        UserEntity user = userService
                .findByUsername(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + authentication.getName()));

        List<CollectionEntity> collections =
                collectionService.findByUserId(user.getUserId());

        model.addAttribute("collections", collections);

        return "collections/list";
    }

    /**
     * Creates a new collection for the authenticated user.
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String createCollection(
            Authentication authentication,
            @RequestParam String name,
            @RequestParam(required = false) String description) {

        UserEntity user = userService
                .findByUsername(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + authentication.getName()));

        CollectionEntity collection = new CollectionEntity();
        collection.setUser(user);
        collection.setName(name);
        collection.setDescription(description);

        collectionService.createCollection(collection);

        return "redirect:/collections";
    }

    /**
     * Deletes a collection owned by the authenticated user.
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/delete")
    public String deleteCollection(Authentication authentication,
                                   @RequestParam Long collectionId) {

        UserEntity user = userService
                .findByUsername(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + authentication.getName()));

        collectionRepository.findByCollectionIdAndUserUserId(collectionId, user.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Collection not found or not owned by user"));

        collectionService.deleteCollection(collectionId);

        return "redirect:/collections";
    }

    /**
     * Adds an item to a user collection.
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/add-item")
    public String addItem(Authentication authentication,
                          @RequestParam Long collectionId,
                          @RequestParam Long itemId) {

        UserEntity user = userService
                .findByUsername(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + authentication.getName()));

        collectionRepository.findByCollectionIdAndUserUserId(collectionId, user.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Collection not found or not owned by user"));

        collectionService.addItemToCollection(collectionId, itemId);

        return "redirect:/items/" + itemId;
    }

    /**
     * Removes an item from a user collection.
     */
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/remove-item")
    public String removeItem(Authentication authentication,
                             @RequestParam Long collectionId,
                             @RequestParam Long itemId) {

        UserEntity user = userService
                .findByUsername(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + authentication.getName()));

        collectionRepository.findByCollectionIdAndUserUserId(collectionId, user.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Collection not found or not owned by user"));

        collectionService.removeItemFromCollection(collectionId, itemId);

        return "redirect:/collections/" + collectionId;
    }

    /**
     * Displays a specific collection and its items.
     */
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/{id}")
    public String viewCollection(@PathVariable Long id,
                                 Authentication authentication,
                                 Model model) {

        UserEntity user = userService
                .findByUsername(authentication.getName())
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + authentication.getName()));

        CollectionEntity collection = collectionRepository
                .findByCollectionIdAndUserUserId(id, user.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Collection not found or not owned by user"));

        model.addAttribute("collection", collection);
        model.addAttribute("items", collectionService.getItemsInCollection(id));

        return "collections/detail";
    }
}