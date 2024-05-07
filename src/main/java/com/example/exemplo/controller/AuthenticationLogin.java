package com.example.exemplo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("/v1/auth")
public class AuthenticationLogin {

    private static final Logger log = LoggerFactory.getLogger(AuthenticationLogin.class);

    @PostMapping("/signin")
    @ResponseStatus(OK)
    public String signin(@AuthenticationPrincipal UserDetails principal) {
        log.info("current user is: {}", principal.getUsername());
        return "user Logado";
    }


}
