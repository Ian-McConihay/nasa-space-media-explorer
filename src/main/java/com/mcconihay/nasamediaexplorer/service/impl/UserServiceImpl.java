package com.mcconihay.nasamediaexplorer.service.impl;

import com.mcconihay.nasamediaexplorer.entity.UserEntity;
import com.mcconihay.nasamediaexplorer.exception.DuplicateResourceException;
import com.mcconihay.nasamediaexplorer.exception.ResourceNotFoundException;
import com.mcconihay.nasamediaexplorer.repository.UserRepository;
import com.mcconihay.nasamediaexplorer.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
/**
 * Default implementation of {@link UserService}.
 *
 * Handles user creation, validation, retrieval,
 * and deletion.
 */
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    /**
     * Constructs the service with repository dependency.
     */
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Creates a user after validating uniqueness.
     */
    @Override
    public UserEntity createUser(UserEntity user) {

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new DuplicateResourceException("Username already exists: " + user.getUsername());
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new DuplicateResourceException("Email already exists: " + user.getEmail());
        }

        return userRepository.save(user);
    }

    /**
     * Finds a user by username.
     */
    @Override
    public Optional<UserEntity> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    /**
     * Finds a user by identifier.
     */
    @Override
    public Optional<UserEntity> findById(Long id) {
        return userRepository.findById(id);
    }

    /**
     * Retrieves all users.
     */
    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    /**
     * Deletes a user if present.
     */
    @Override
    public void deleteUser(Long id) {
        UserEntity existing = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found: " + id));
        userRepository.delete(existing);
    }
}