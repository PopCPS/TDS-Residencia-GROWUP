package com.example.exemplo.controller;


import com.example.exemplo.configuration.security.StrateegiaAuthenticationProvider;
import com.example.exemplo.configuration.strateegia.StrateegiaInMemoryTokenStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public String signin(UsernamePasswordAuthenticationToken authentication) {
        log.info("current user is: {}", SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        return "O usuário logado é "
                + authentication.getName()
                + "\n accessToken: " +  StrateegiaInMemoryTokenStore.getInstance().getTokenStore();
    }


}
