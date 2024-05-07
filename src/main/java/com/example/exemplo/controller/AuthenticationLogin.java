package com.example.exemplo.controller;

import com.example.exemplo.configuration.security.CustomBasicAuthenticationFilter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
public class AuthenticationLogin {
    CustomBasicAuthenticationFilter customBasicAuthenticationFilter = new CustomBasicAuthenticationFilter();

    @PostMapping("/signin")
    public ResponseEntity<String> signin() {
        return ResponseEntity.ok("user Logado");
    }


}
