package com.example.exemplo.configuration.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Base64;

import static com.example.exemplo.configuration.strateegia.StrateegiaRestTemplateConfiguration.STRATEEGIA_REST_TEMPLATE_BEAN;

@Component
public class CustomBasicAuthenticationFilter extends OncePerRequestFilter {

    private static final int BASIC_LENGTH = 6;

    private final RestTemplate restTemplate;

    String resultado;

    @Autowired
    public CustomBasicAuthenticationFilter(@Qualifier(STRATEEGIA_REST_TEMPLATE_BEAN) RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Basic dXN1YXJpbzoxMjM0NTY=
        var headerAuthorization = request.getHeader("Authorization");

        if (headerAuthorization == null || !headerAuthorization.startsWith("Basic ")) {
            filterChain.doFilter(request, response);
            return;
        }
        RestTemplate restTemplate = new RestTemplate();
        String url = "https://api.strateegia.digital/users/v1/auth/signin";
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", headerAuthorization);
        headers.setContentType(MediaType.APPLICATION_JSON); // tá definindo o corpo com um json
        String requestBody = "{\"keep_connected\": true}";
        HttpEntity<String> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<ResultDTO> responseEntity = restTemplate.exchange(url, HttpMethod.POST, requestEntity, ResultDTO.class);
        resultado = responseEntity.getBody().accessToken;
        System.out.println("TESTANDO RESULTADO");
        System.out.println(resultado);

        // dXN1YXJpbzoxMjM0NTY=
        var basicToken = headerAuthorization.substring(BASIC_LENGTH);
        byte[] basicTokenDecoded = Base64.getDecoder().decode(basicToken);

        // user:senha
        String basicTokenValue = new String(basicTokenDecoded);

        // ["user", "senha"]
        String[] basicAuthsSplit = basicTokenValue.split(":");

        if (resultado != null) {
            var authToken = new UsernamePasswordAuthenticationToken(basicAuthsSplit[0], null, null);
            SecurityContextHolder.getContext().setAuthentication(authToken);
        }


        // envia para o próximo ou se não tiver, envia para a resposta final(o que foi requisitado)
        filterChain.doFilter(request, response);
    }
    public String getResultado(){
        return resultado;
    }
}