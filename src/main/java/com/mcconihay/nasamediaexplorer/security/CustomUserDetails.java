package com.mcconihay.nasamediaexplorer.security;

import com.mcconihay.nasamediaexplorer.entity.UserEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

/**
 * Adapter that exposes UserEntity data in a format
 * compatible with Spring Security.
 *
 * Maps application roles to GrantedAuthority objects
 * used during authentication and authorization.
 */
public class CustomUserDetails implements UserDetails {

    private final UserEntity user;

    /**
     * Constructs a security wrapper around a UserEntity.
     *
     * @param user application user
     */
    public CustomUserDetails(UserEntity user) {
        this.user = user;
    }

    /**
     * Returns the authorities granted to the user.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return user.getUserRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority("ROLE_" +
                        role.getRole().getRoleName()))
                .collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPasswordHash();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }

    @Override public boolean isAccountNonExpired() { return true; }

    @Override public boolean isAccountNonLocked() { return true; }

    @Override public boolean isCredentialsNonExpired() { return true; }
}