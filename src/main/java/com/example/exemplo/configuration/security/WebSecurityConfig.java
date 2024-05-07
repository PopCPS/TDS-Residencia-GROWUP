package com.example.exemplo.configuration.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class WebSecurityConfig {

    private final ObjectMapper objectMapper;
    private final StrateegiaAuthenticationProvider strateegiaAuthenticationProvider;

    public WebSecurityConfig(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.strateegiaAuthenticationProvider = new StrateegiaAuthenticationProvider(objectMapper);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .httpBasic(withDefaults())
                .authorizeHttpRequests(requestMatcherRegistry -> requestMatcherRegistry
                        .anyRequest().authenticated()) // requisita autenticação para todas as uris do projeto
                .authenticationProvider(strateegiaAuthenticationProvider) // define o uso do AuthenticationProvider que nós criamos
                .build();

    }

}
