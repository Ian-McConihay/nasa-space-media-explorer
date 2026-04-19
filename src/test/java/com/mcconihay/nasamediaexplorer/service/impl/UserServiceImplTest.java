package com.mcconihay.nasamediaexplorer.service.impl;

import com.mcconihay.nasamediaexplorer.entity.UserEntity;
import com.mcconihay.nasamediaexplorer.exception.DuplicateResourceException;
import com.mcconihay.nasamediaexplorer.exception.ResourceNotFoundException;
import com.mcconihay.nasamediaexplorer.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void createUser_ShouldSave_WhenUsernameAndEmailAreUnique() {
        UserEntity user = new UserEntity();
        user.setUsername("testuser");
        user.setEmail("test@email.com");

        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("test@email.com")).thenReturn(false);
        when(userRepository.save(user)).thenReturn(user);

        UserEntity result = userService.createUser(user);

        assertNotNull(result);
        assertEquals("testuser", result.getUsername());
        verify(userRepository).save(user);
    }

    @Test
    void createUser_ShouldThrow_WhenUsernameAlreadyExists() {
        UserEntity user = new UserEntity();
        user.setUsername("testuser");
        user.setEmail("test@email.com");

        when(userRepository.existsByUsername("testuser")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> userService.createUser(user));
        verify(userRepository, never()).save(any());
    }

    @Test
    void createUser_ShouldThrow_WhenEmailAlreadyExists() {
        UserEntity user = new UserEntity();
        user.setUsername("testuser");
        user.setEmail("test@email.com");

        when(userRepository.existsByUsername("testuser")).thenReturn(false);
        when(userRepository.existsByEmail("test@email.com")).thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> userService.createUser(user));
        verify(userRepository, never()).save(any());
    }

    @Test
    void findByUsername_ShouldReturnUser_WhenFound() {
        UserEntity user = new UserEntity();
        user.setUsername("testuser");

        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));

        Optional<UserEntity> result = userService.findByUsername("testuser");

        assertTrue(result.isPresent());
        assertEquals("testuser", result.get().getUsername());
    }

    @Test
    void findAll_ShouldReturnAllUsers() {
        UserEntity user1 = new UserEntity();
        UserEntity user2 = new UserEntity();

        when(userRepository.findAll()).thenReturn(List.of(user1, user2));

        List<UserEntity> result = userService.findAll();

        assertEquals(2, result.size());
    }

    @Test
    void deleteUser_ShouldDelete_WhenUserExists() {
        UserEntity user = new UserEntity();
        user.setUserId(1L);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository).delete(user);
    }

    @Test
    void deleteUser_ShouldThrow_WhenUserDoesNotExist() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> userService.deleteUser(1L));
        verify(userRepository, never()).delete(any());
    }
}