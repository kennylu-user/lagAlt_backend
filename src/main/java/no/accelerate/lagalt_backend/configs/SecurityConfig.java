package no.accelerate.lagalt_backend.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors().and()
                .sessionManagement().disable()
                .csrf().disable()
                // Enable security for http requests
                .authorizeHttpRequests(authorize -> authorize
                        // Everyone gets to access endpoints for fetching projects
                        .requestMatchers("/api/v1/project/**").permitAll()
                        // Allows access to overview of documented API endpoints through swagger
                        .requestMatchers(
                                "/v3/api-docs/**",
                                "/swagger-resources/configuration/ui",
                                "/swagger-resources",
                                "/swagger-resources/configuration/security",
                                "/swagger-ui/**",
                                "/webjars/**"
                        ).permitAll()
//                        .requestMatchers("/api/v1/comment").hasRole("USER")
                        // Other endpoints may only be accessed by authenticated users
                        .anyRequest().permitAll()
                )
                .oauth2ResourceServer()
                .jwt();
                //.jwtAuthenticationConverter(jwtAuthenticationConverter());
//                .headers()
//                .xssProtection()
//                .and()
//                .contentSecurityPolicy("script-src 'self'");
        return http.build();
    }

    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        JwtGrantedAuthoritiesConverter grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        grantedAuthoritiesConverter.setAuthoritiesClaimName("roles");
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
}