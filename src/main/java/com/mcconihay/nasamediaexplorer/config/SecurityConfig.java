package com.mcconihay.nasamediaexplorer.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configures application security settings.
 *
 * This class defines HTTP security rules, enables method-level security,
 * and registers password encoding for user authentication.
 */
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    /**
     * Defines the security filter chain for HTTP requests.
     *
     * - Disables CSRF protection (suitable for stateless REST APIs)
     * - Allows public access to endpoints under /api/public/**
     * - Requires authentication for all other endpoints
     * - Enables HTTP Basic authentication
     *
     * @param http the HttpSecurity configuration object
     * @return the configured SecurityFilterChain
     * @throws Exception if security configuration fails
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {

        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/api/public/**").permitAll()
                    .anyRequest().authenticated()
            )
            .httpBasic(basic -> {});

        return http.build();
    }

    /**
     * Provides the password encoder used for hashing user passwords.
     *
     * BCrypt is used to securely hash and salt passwords before storage.
     *
     * @return a BCryptPasswordEncoder instance
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}