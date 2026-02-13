package com.mcconihay.nasamediaexplorer.controller;

import com.mcconihay.nasamediaexplorer.dto.UserCreateRequest;
import com.mcconihay.nasamediaexplorer.dto.UserResponse;
import com.mcconihay.nasamediaexplorer.entity.UserEntity;
import com.mcconihay.nasamediaexplorer.exception.ResourceNotFoundException;
import com.mcconihay.nasamediaexplorer.mapper.UserMapper;
import com.mcconihay.nasamediaexplorer.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Administrative controller for managing application users.
 *
 * All endpoints require ADMIN privileges.
 */
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UserController(UserService userService,
                          PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Creates a new user with an encrypted password.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public UserResponse create(@RequestBody UserCreateRequest request) {

        UserEntity user = UserMapper.toEntity(request);
        user.setPasswordHash(passwordEncoder.encode(request.password));

        return UserMapper.toResponse(userService.createUser(user));
    }

    /**
     * Retrieves all users.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<UserResponse> all() {
        return userService.findAll()
                .stream()
                .map(UserMapper::toResponse)
                .toList();
    }

    /**
     * Retrieves a user by ID.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public UserResponse byId(@PathVariable Long id) {

        UserEntity u = userService.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));

        return UserMapper.toResponse(u);
    }

    /**
     * Deletes a user.
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        userService.deleteUser(id);
    }
}