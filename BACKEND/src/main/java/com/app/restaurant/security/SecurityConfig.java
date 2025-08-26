package com.app.restaurant.security;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import com.app.restaurant.security.jwt.JwtAuthenticationEntryPoint;
import com.app.restaurant.security.jwt.JwtAuthorizationFilter;
import com.app.restaurant.security.jwt.JwtLogoutSuccessHandler;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

        @Value("${app.cors.allowed-origins}")
        private String allowedOrigins;

        private static final String[] WHITE_LIST_URL = {
                        "/auth/**",
                        "/uploads/**",
                        "/api/admin/users/verify",
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui/**",
                        "/swagger-ui.html"
        };

        private final JwtAuthorizationFilter jwtAuthorizationFilter;
        private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
        private final JwtLogoutSuccessHandler jwtLogoutSuccessHandler;

        @Bean
        PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
        }

        @Bean
        AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
                        throws Exception {
                return authenticationConfiguration.getAuthenticationManager();
        }

        @Bean
        SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                List<String> origins = Arrays.asList(allowedOrigins.split(","));
                http.csrf(csrf -> csrf.disable())
                                .cors(cors -> cors
                                                .configurationSource(request -> {
                                                        CorsConfiguration corsConfig = new CorsConfiguration();
                                                        corsConfig.setAllowedOrigins(origins);
                                                        corsConfig.setAllowedMethods(List.of("GET", "POST", "PUT",
                                                                        "DELETE", "PATCH"));
                                                        corsConfig.setAllowedHeaders(List.of("*"));
                                                        corsConfig.setAllowCredentials(true);
                                                        return corsConfig;
                                                }))
                                .headers(header -> {
                                        header
                                                        .contentSecurityPolicy(policy -> {
                                                                policy.policyDirectives(
                                                                                "frame-ancestors 'self' http://localhost:4200 http://localhost:8080;");
                                                        })
                                                        .frameOptions(frame -> frame.sameOrigin());
                                })
                                .authorizeHttpRequests(authz -> authz
                                                .requestMatchers(WHITE_LIST_URL).permitAll()
                                                .anyRequest().authenticated())
                                .exceptionHandling(ex -> ex
                                                .authenticationEntryPoint(jwtAuthenticationEntryPoint))
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .logout(logout -> logout
                                                .logoutUrl("/auth/logout")
                                                .logoutSuccessHandler(jwtLogoutSuccessHandler));

                http.addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

                return http.build();
        }

}
