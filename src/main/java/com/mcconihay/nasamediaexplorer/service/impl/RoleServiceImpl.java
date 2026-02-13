package com.mcconihay.nasamediaexplorer.service.impl;

import com.mcconihay.nasamediaexplorer.entity.RoleEntity;
import com.mcconihay.nasamediaexplorer.entity.RoleType;
import com.mcconihay.nasamediaexplorer.repository.RoleRepository;
import com.mcconihay.nasamediaexplorer.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
/**
 * Default implementation of {@link RoleService}.
 *
 * Provides lookup operations for application roles.
 */
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    /**
     * Constructs the service with repository dependency.
     */
    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    /**
     * Finds a role by name.
     */
    @Override
    public Optional<RoleEntity> findByRoleName(String roleName) {
        return roleRepository.findByRoleName(RoleType.valueOf(roleName));
    }
}