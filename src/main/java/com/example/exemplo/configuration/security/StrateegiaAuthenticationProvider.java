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

/**
 * AuthenticationProvider define uma forma de autenticação específica.
 * Neste caso, como se autenticar com o strateegia
 */
public class StrateegiaAuthenticationProvider implements AuthenticationProvider {

    // definição de log (mais recomentado que System.out.println)
    private static final Logger log = LoggerFactory.getLogger(StrateegiaAuthenticationProvider.class);
    private final RestTemplateBuilder restTemplateBuilder;
    private final ObjectMapper objectMapper;
    private final StrateegiaInMemoryTokenStore strateegiaInMemoryTokenStore;

    public StrateegiaAuthenticationProvider(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        // define a raiz da url para a api do strateegia
        this.restTemplateBuilder = new RestTemplateBuilder().rootUri("https://api.strateegia.digital/users");
        this.strateegiaInMemoryTokenStore = StrateegiaInMemoryTokenStore.getInstance();
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        // Como foi definido que a autenticação desta api é http basic,
        // o objeto authentication vai conter o usuário e senha já decodificados (não precisa mais fazer o decode do base64)
        log.info("attempt to authenticate user {}", authentication.getName());
        String email = authentication.getName();
        String password = authentication.getCredentials().toString();
        RestTemplate restTemplate = buildRestTemplate(email, password);

        // caso a tokenStore já tenha um token para o email, apenas retornar o objeto de autenticação
        if (strateegiaInMemoryTokenStore.getTokenStore().containsKey(email)) {
            String token = strateegiaInMemoryTokenStore.getTokenStore().get(email);
            return new UsernamePasswordAuthenticationToken(email, token, null);
        } else {
            // caso contrário, fazer o fluxo de autenticação, salvar o token na token store e retornar o objeto de autenticação
            StrateegiaSigninResponseDTO responseDTO = doSignin(restTemplate);
            if (responseDTO != null) {
                strateegiaInMemoryTokenStore.getTokenStore().put(email, responseDTO.accessToken);
                log.info("authentication successful");
                return new UsernamePasswordAuthenticationToken(email, responseDTO.accessToken, null);
            }
        }
        throw new BadCredentialsException("failed to authenticate");
    }

    /**
     * Executa o processo de signin
     * @param restTemplate
     * @return um objeto com o token
     */
    private static StrateegiaSigninResponseDTO doSignin(RestTemplate restTemplate) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE); // define o formato de conteúdo da requisição
        HttpEntity<String> httpEntity = new HttpEntity<>(null, httpHeaders);
        return restTemplate.postForObject("/v1/auth/signin", httpEntity, StrateegiaSigninResponseDTO.class);
    }

    /**
     * Constrói o resttemplate
     * @param email
     * @param password
     * @return
     */
    private RestTemplate buildRestTemplate(String email, String password) {
        return restTemplateBuilder
                .basicAuthentication(email, password, StandardCharsets.UTF_8) // define a autenticação por usuário e senha comm charset requisitado pelo strateegia
                .messageConverters(new MappingJackson2HttpMessageConverter(objectMapper)) // configura o resttemplate para mapear o json de retorno no formato snake_case
                .build();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
