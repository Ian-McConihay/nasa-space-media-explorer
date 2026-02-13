package com.mcconihay.nasamediaexplorer.repository;

import com.mcconihay.nasamediaexplorer.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

/**
 * Repository responsible for UserEntity persistence.
 *
 * Includes optimized queries used by the security layer
 * to eagerly load roles during authentication.
 */
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    /**
     * Retrieves a user by username while eagerly loading roles.
     *
     * Prevents lazy loading issues during authentication.
     *
     * @param username login username
     * @return user with roles if found
     */
    @Query("""
            SELECT u
            FROM UserEntity u
            LEFT JOIN FETCH u.userRoles ur
            LEFT JOIN FETCH ur.role
            WHERE u.username = :username
            """)
    Optional<UserEntity> findByUsername(String username);

    /**
     * Finds a user by email address.
     *
     * @param email user email
     * @return matching user if present
     */
    Optional<UserEntity> findByEmail(String email);

    /**
     * Checks if a username already exists.
     *
     * @param username username
     * @return true if taken
     */
    boolean existsByUsername(String username);

    /**
     * Checks if an email is already registered.
     *
     * @param email email address
     * @return true if taken
     */
    boolean existsByEmail(String email);
}