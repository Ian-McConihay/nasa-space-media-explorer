package com.mcconihay.nasamediaexplorer.repository;

import com.mcconihay.nasamediaexplorer.entity.RoleEntity;
import com.mcconihay.nasamediaexplorer.entity.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository for managing RoleEntity persistence.
 *
 * Provides lookup functionality for resolving roles
 * during user creation and authorization.
 */
public interface RoleRepository extends JpaRepository<RoleEntity, Long> {

    /**
     * Finds a role by its enum name.
     *
     * @param roleName role type
     * @return matching role if present
     */
    Optional<RoleEntity> findByRoleName(RoleType roleName);
}