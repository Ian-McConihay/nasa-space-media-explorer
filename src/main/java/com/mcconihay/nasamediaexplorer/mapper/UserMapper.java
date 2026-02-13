package com.mcconihay.nasamediaexplorer.mapper;

import com.mcconihay.nasamediaexplorer.dto.UserCreateRequest;
import com.mcconihay.nasamediaexplorer.dto.UserResponse;
import com.mcconihay.nasamediaexplorer.entity.UserEntity;

/**
 * Mapper responsible for converting User DTOs
 * into entity models and response objects.
 */
public final class UserMapper {

    private UserMapper() {}

    /**
     * Converts a user creation request into a UserEntity.
     * Password encoding is handled separately.
     *
     * @param req user creation request
     * @return UserEntity
     */
    public static UserEntity toEntity(UserCreateRequest req) {
        UserEntity e = new UserEntity();
        e.setUsername(req.username);
        e.setEmail(req.email);
        e.setEnabled(req.enabled);
        return e;
    }

    /**
     * Converts a UserEntity into a response DTO.
     *
     * @param e user entity
     * @return UserResponse DTO
     */
    public static UserResponse toResponse(UserEntity e) {
        UserResponse r = new UserResponse();
        r.userId = e.getUserId();
        r.username = e.getUsername();
        r.email = e.getEmail();
        r.enabled = e.isEnabled();
        r.createdAt = e.getCreatedAt();
        return r;
    }
}