package com.mcconihay.nasamediaexplorer.controller;

import com.mcconihay.nasamediaexplorer.entity.RoleEntity;
import com.mcconihay.nasamediaexplorer.entity.RoleType;
import com.mcconihay.nasamediaexplorer.entity.UserEntity;
import com.mcconihay.nasamediaexplorer.entity.UserRoleEntity;
import com.mcconihay.nasamediaexplorer.entity.UserRoleId;
import com.mcconihay.nasamediaexplorer.repository.RoleRepository;
import com.mcconihay.nasamediaexplorer.service.UserService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsible for handling user authentication actions
 * such as registration.
 */
@Controller
public class AuthController {

    private final UserService userService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor for AuthController.
     *
     * @param userService service used for user persistence
     * @param passwordEncoder encoder used to hash user passwords
     * @param roleRepository repository used to retrieve role data
     */
    public AuthController(UserService userService,
                          PasswordEncoder passwordEncoder,
                          RoleRepository roleRepository) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    /**
     * Handles user registration.
     * Creates a new user, assigns a default READONLY role,
     * and saves the user to the database.
     *
     * @param username the username entered by the user
     * @param email the email entered by the user
     * @param password the raw password entered by the user
     * @return redirect to login page after successful registration
     */
    @PostMapping("/register")
    public String register(
            @RequestParam String username,
            @RequestParam String email,
            @RequestParam String password) {

        UserEntity user = new UserEntity();

        user.setUsername(username);
        user.setEmail(email);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setEnabled(true);

        RoleEntity role = roleRepository
                .findByRoleName(RoleType.READONLY)
                .orElseThrow();

        UserRoleEntity userRole = new UserRoleEntity();
        userRole.setUser(user);
        userRole.setRole(role);
        userRole.setId(new UserRoleId());

        user.getUserRoles().add(userRole);

        userService.createUser(user);

        return "redirect:/login";
    }
}