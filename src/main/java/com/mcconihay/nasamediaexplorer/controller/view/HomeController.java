package com.mcconihay.nasamediaexplorer.controller.view;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Handles navigation to the main dashboard page.
 */
@Controller
public class HomeController {

    /**
     * Displays the main landing page.
     *
     * URL: /
     *
     * @return public index template
     */
    @GetMapping("/")
    public String home() {
        return "public/index";
    }
}
