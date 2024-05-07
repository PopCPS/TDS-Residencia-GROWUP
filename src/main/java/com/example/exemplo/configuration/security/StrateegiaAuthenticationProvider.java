package com.example.exemplo.configuration.security;

import com.example.exemplo.configuration.strateegia.StrateegiaInMemoryTokenStore;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;

public class StrateegiaAuthenticationProvider implements AuthenticationProvider {

    private static final Logger log = LoggerFactory.getLogger(StrateegiaAuthenticationProvider.class);
    private final RestTemplateBuilder restTemplateBuilder;
    private final ObjectMapper objectMapper;
    private final StrateegiaInMemoryTokenStore strateegiaInMemoryTokenStore;

    public StrateegiaAuthenticationProvider(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.restTemplateBuilder = new RestTemplateBuilder().rootUri("https://api.strateegia.digital/users");
        this.strateegiaInMemoryTokenStore = StrateegiaInMemoryTokenStore.getInstance();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.info("attempt to authenticate user {}", authentication.getName());
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        RestTemplate restTemplate = buildRestTemplate(email, password);

        if (strateegiaInMemoryTokenStore.getTokenStore().containsKey(email)) {
            String token = strateegiaInMemoryTokenStore.getTokenStore().get(email);
            return new UsernamePasswordAuthenticationToken(email, token, null);
        } else {
            StrateegiaSigninResponseDTO responseDTO = doSignin(restTemplate);
            if (responseDTO != null) {
                strateegiaInMemoryTokenStore.getTokenStore().put(email, responseDTO.accessToken);
                log.info("authentication successful");
                return new UsernamePasswordAuthenticationToken(email, responseDTO.accessToken, null);
            }
        }
        throw new BadCredentialsException("failed to authenticate");
    }

    private static StrateegiaSigninResponseDTO doSignin(RestTemplate restTemplate) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<String> httpEntity = new HttpEntity<>(null, httpHeaders);
        return restTemplate.postForObject("/v1/auth/signin", httpEntity, StrateegiaSigninResponseDTO.class);
    }

    private RestTemplate buildRestTemplate(String email, String password) {
        return restTemplateBuilder.basicAuthentication(email, password, StandardCharsets.UTF_8)
                .messageConverters(new MappingJackson2HttpMessageConverter(objectMapper))
                .build();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
