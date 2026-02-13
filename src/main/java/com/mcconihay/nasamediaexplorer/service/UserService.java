package com.mcconihay.nasamediaexplorer.service;

import com.mcconihay.nasamediaexplorer.entity.UserEntity;

import java.util.List;
import java.util.Optional;

/**
 * Service responsible for managing application users.
 *
 * Handles creation, lookup, and deletion operations.
 */
public interface UserService {

    /**
     * Creates a new user.
     *
     * @param user user to persist
     * @return saved user
     */
    UserEntity createUser(UserEntity user);

    /**
     * Finds a user by username.
     *
     * @param username login name
     * @return optional containing the user if found
     */
    Optional<UserEntity> findByUsername(String username);

    /**
     * Finds a user by identifier.
     *
     * @param id user identifier
     * @return optional containing the user if found
     */
    Optional<UserEntity> findById(Long id);

    /**
     * Retrieves all users.
     *
     * @return list of users
     */
    List<UserEntity> findAll();

    /**
     * Deletes a user by identifier.
     *
     * @param id user identifier
     */
    void deleteUser(Long id);
}