package com.example.exemplo.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
public class AuthenticationLogin {

    @PostMapping("/signin")
    public ResponseEntity<String> signin() {
        return ResponseEntity.ok("user Logado");
    }


}
