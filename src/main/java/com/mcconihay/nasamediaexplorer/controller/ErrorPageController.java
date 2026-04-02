package com.mcconihay.nasamediaexplorer.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller responsible for handling custom error page routing.
 * Maps HTTP error codes to corresponding Thymeleaf templates.
 */
@Controller
public class ErrorPageController {

    /**
     * Handles 403 Access Denied errors.
     *
     * @return view name for 403 error page
     */
    @GetMapping("/error/403")
    public String accessDenied() {
        return "error/403";
    }

    /**
     * Handles 404 Not Found errors.
     *
     * @return view name for 404 error page
     */
    @GetMapping("/error/404")
    public String notFound() {
        return "error/404";
    }

    /**
     * Handles 500 Internal Server errors.
     *
     * @return view name for 500 error page
     */
    @GetMapping("/error/500")
    public String serverError() {
        return "error/500";
    }
}