package com.mcconihay.nasamediaexplorer.controller.view;

import com.mcconihay.nasamediaexplorer.entity.AnnotationEntity;
import com.mcconihay.nasamediaexplorer.entity.ItemEntity;
import com.mcconihay.nasamediaexplorer.entity.UserEntity;
import com.mcconihay.nasamediaexplorer.service.AnnotationService;
import com.mcconihay.nasamediaexplorer.service.ItemService;
import com.mcconihay.nasamediaexplorer.service.UserService;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
/**
 * Controller responsible for handling annotation-related UI actions.
 * Supports viewing, creating, and deleting annotations.
 */
@Controller
@RequestMapping("/annotations")
public class AnnotationViewController {

    private final AnnotationService annotationService;
    private final UserService userService;
    private final ItemService itemService;

    /**
     * Constructor for AnnotationViewController.
     */
    public AnnotationViewController(
            AnnotationService annotationService,
            UserService userService,
            ItemService itemService) {

        this.annotationService = annotationService;
        this.userService = userService;
        this.itemService = itemService;
    }

    /**
     * Displays all annotations for a given item.
     *
     * @param itemId the ID of the item
     * @param model the UI model
     * @return view displaying annotations for the item
     */
    @GetMapping("/by-item")
    public String viewAnnotations(
            @RequestParam Long itemId,
            Model model) {

        List<AnnotationEntity> annotations =
                annotationService.findByItemId(itemId);

        model.addAttribute("annotations", annotations);
        model.addAttribute("itemId", itemId);

        return "annotations/by-item";
    }

    /**
     * Creates a new annotation for a given item.
     *
     * @param authentication current logged-in user
     * @param itemId the item ID
     * @param note the annotation text
     * @return redirect to item detail page
     */
    @PostMapping("/create")
    public String createAnnotation(
            Authentication authentication,
            @RequestParam Long itemId,
            @RequestParam String note) {

        String username = authentication.getName();

        UserEntity user =
                userService.findByUsername(username)
                        .orElseThrow();

        ItemEntity item =
                itemService.findById(itemId)
                        .orElseThrow();

        AnnotationEntity annotation = new AnnotationEntity();
        annotation.setUser(user);
        annotation.setItem(item);
        annotation.setNote(note);

        annotationService.saveAnnotation(annotation);

        return "redirect:/items/" + itemId;
    }

    /**
     * Deletes an annotation by ID.
     *
     * @param annotationId the annotation ID
     * @param itemId the item ID for redirect
     * @return redirect to item detail page
     */
    @PostMapping("/delete")
    public String deleteAnnotation(
            @RequestParam Long annotationId,
            @RequestParam Long itemId) {

        annotationService.deleteAnnotation(annotationId);

        return "redirect:/items/" + itemId;
    }
}