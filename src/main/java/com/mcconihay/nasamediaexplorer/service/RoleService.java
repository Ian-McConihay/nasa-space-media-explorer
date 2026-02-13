package com.mcconihay.nasamediaexplorer.service;

import com.mcconihay.nasamediaexplorer.entity.RoleEntity;

import java.util.Optional;

/**
 * Service responsible for resolving application roles.
 *
 * Typically used during user creation and authorization.
 */
public interface RoleService {

    /**
     * Finds a role by name.
     *
     * @param roleName role identifier
     * @return optional containing the role if found
     */
    Optional<RoleEntity> findByRoleName(String roleName);
}