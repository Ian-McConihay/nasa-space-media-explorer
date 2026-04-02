package com.mcconihay.nasamediaexplorer.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller responsible for rendering authentication pages.
 */
@Controller
public class AuthViewController {

    /**
     * Displays the login page.
     *
     * @return login view
     */
    @GetMapping("/login")
    public String loginPage() {
        return "auth/login";
    }

    /**
     * Displays the registration page.
     *
     * @return registration view
     */
    @GetMapping("/register")
    public String registerPage() {
        return "auth/register";
    }
}