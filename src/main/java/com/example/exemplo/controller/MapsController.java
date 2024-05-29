package com.example.exemplo.controller;

import com.example.exemplo.configuration.strateegia.StrateegiaInMemoryTokenStore;
import com.example.exemplo.controller.dto.MapsResponse;
import com.example.exemplo.controller.dto.PaginatedResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import static com.example.exemplo.configuration.strateegia.StrateegiaRestTemplateConfiguration.STRATEEGIA_REST_TEMPLATE;


@RestController
public class MapsController{
    private final StrateegiaInMemoryTokenStore tokenStore;

    private static final Logger log = LoggerFactory.getLogger(JourneysController.class);

    private RestTemplate restTemplate;

    public MapsController(@Qualifier(STRATEEGIA_REST_TEMPLATE) RestTemplate restTemplate) { // @Qualifier serve para especificar qual @Bean importar. Como se fosse, chamar ele por um nome
        this.restTemplate = restTemplate;
        this.tokenStore = StrateegiaInMemoryTokenStore.getInstance();
    }
    @GetMapping("/maps")
    public ResponseEntity<MapsResponse> getJourneys(){
        String url = "/projects/v1/project/660572d9feb2310be9fd8c3f";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + tokenStore.getTokenStore().get(SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE); // define o formato de conteúdo da requisição
        HttpEntity httpEntity = new HttpEntity<>(headers);

        ResponseEntity<MapsResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, MapsResponse.class);
        // O certo é criar uma nova, mesmo que o tipo do objeto seja o mesmo
        return ResponseEntity.ok(responseEntity.getBody());


    }


}
