package com.example.exemplo.configuration.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.HashMap;

import static com.example.exemplo.configuration.strateegia.StrateegiaRestTemplateConfiguration.STRATEEGIA_REST_TEMPLATE_BEAN;

@Component
public class CustomBasicAuthenticationFilter extends OncePerRequestFilter {

    private final RestTemplateBuilder restTemplateBuilder;
    private final HashMap<String, String> tokenStore;

    @Autowired
    public CustomBasicAuthenticationFilter(@Qualifier(STRATEEGIA_REST_TEMPLATE_BEAN) RestTemplateBuilder restTemplateBuilder) {
        this.restTemplateBuilder = restTemplateBuilder;
        this.tokenStore = new HashMap<>(1);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        var headerAuthorization = request.getHeader("Authorization");
        if (headerAuthorization == null || !headerAuthorization.startsWith("Basic ")) {
            filterChain.doFilter(request, response);
            return;
        }
        String[] credentials = decodeBasicAuthHeader(headerAuthorization);
        String email = credentials[0];
        String password = credentials[1];
        String token = tokenStore.get(email);
        if (!StringUtils.hasText(token)) {
            StrateegiaSigninResponseDTO responseDTO = restTemplateBuilder.basicAuthentication(email, password).build().postForObject("/v1/auth/signin", null, StrateegiaSigninResponseDTO.class);
            if (responseDTO != null) {
                tokenStore.put(email, responseDTO.accessToken);
                SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(email, responseDTO.accessToken, null));
            }
        }
        filterChain.doFilter(request, response);
    }

    private String[] decodeBasicAuthHeader(String header) {
        String base64Credentials = header.substring("basic".length()).trim();
        byte[] decodeCredentials = Base64.getDecoder().decode(base64Credentials);
        String credentials = new String(decodeCredentials, StandardCharsets.UTF_8);
        return credentials.split(":");
    }
}