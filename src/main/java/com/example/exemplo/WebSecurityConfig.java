package com.example.exemplo;

import jakarta.servlet.Filter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {


    @Bean
  public SecurityFilterChain filterChain(HttpSecurity http, CustomBasicAuthenticationFilter customBasicAuthenticationFilter) throws Exception {
       http
            .httpBasic(withDefaults())
            .authorizeHttpRequests(requests -> requests
                    .anyRequest().authenticated())
            .addFilterBefore(customBasicAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .csrf(csrf -> csrf.disable());
        return http.build();

  }

}
