package com.mcconihay.nasamediaexplorer.controller.view;

import com.mcconihay.nasamediaexplorer.entity.*;
import com.mcconihay.nasamediaexplorer.service.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/items")
public class ItemViewController {

    private final ItemService itemService;
    private final UserService userService;
    private final CollectionService collectionService;
    private final AnnotationService annotationService;

    public ItemViewController(
            ItemService itemService,
            UserService userService,
            CollectionService collectionService,
            AnnotationService annotationService) {

        this.itemService = itemService;
        this.userService = userService;
        this.collectionService = collectionService;
        this.annotationService = annotationService;
    }

    /**
     * Displays items with search + filtering.
     */
    @GetMapping
    public String listItems(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) String mediaType,
            @RequestParam(required = false) NasaSourceApi sourceApi,
            @RequestParam(defaultValue = "0") int page,
            Model model) {

        Page<ItemEntity> items =
                itemService.searchItems(
                        title,
                        mediaType,
                        sourceApi,
                        PageRequest.of(page, 20));

        model.addAttribute("items", items);
        model.addAttribute("title", title);
        model.addAttribute("mediaType", mediaType);
        model.addAttribute("sourceApi", sourceApi);

        return "items/list";
    }

    /**
     * Displays item detail page.
     */
    @GetMapping("/{id}")
    public String viewItem(
            @PathVariable Long id,
            Authentication authentication,
            Model model) {

        ItemEntity item = itemService
                .findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found"));

        UserEntity user = userService
                .findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        model.addAttribute("item", item);

        model.addAttribute("collections",
                collectionService.findByUserId(user.getUserId()));

        model.addAttribute("annotations",
                annotationService.findByItemId(id));

        return "items/detail";
    }
}