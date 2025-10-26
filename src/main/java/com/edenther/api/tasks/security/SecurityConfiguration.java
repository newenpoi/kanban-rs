package com.edenther.api.tasks.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    /**
     * - Configures the security filter chain to secure the application endpoints.
     * - Disables CSRF protection (suitable for stateless APIs using JWT).
     * - Sets session management to stateless.
     * - Permits all requests to test endpoints and requires authentication for all other requests.
     * - Configures the application as an OAuth2 Resource Server using JWT for authentication.
     * 
     * @param http the HttpSecurity to modify
     * @return the configured SecurityFilterChain
     * @throws Exception if an error occurs while configuring
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                // Disable CSRF since we're using JWT (stateless).
                .csrf(csrf -> csrf.disable())

                // Configure session management to be stateless.
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                // Configure authorization rules.
                .authorizeHttpRequests(auth -> auth.requestMatchers("/projects/test/**").permitAll().anyRequest().authenticated())

                // Configure OAuth2 Resource Server with JWT.
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));

        return http.build();
    }
}