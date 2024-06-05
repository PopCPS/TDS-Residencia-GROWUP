package com.example.exemplo.services;

import com.example.exemplo.configuration.strateegia.StrateegiaInMemoryTokenStore;
import com.example.exemplo.controller.JourneysController;
import com.example.exemplo.controller.dto.response.SummaryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static com.example.exemplo.configuration.strateegia.StrateegiaRestTemplateConfiguration.STRATEEGIA_REST_TEMPLATE;

@Service
public class SummaryService {
    private final StrateegiaInMemoryTokenStore tokenStore;

    private static final Logger log = LoggerFactory.getLogger(JourneysController.class);

    private RestTemplate restTemplate;

    public SummaryService(@Qualifier(STRATEEGIA_REST_TEMPLATE) RestTemplate restTemplate) { // @Qualifier serve para especificar qual @Bean importar. Como se fosse, chamar ele por um nome
        this.restTemplate = restTemplate;
        this.tokenStore = StrateegiaInMemoryTokenStore.getInstance();
    }
    public ResponseEntity<SummaryResponse> getSummary(String divergencePointId, String questionId){

        String baseUrl = "/projects/v1/divergence-point/" + divergencePointId + "/question/" + questionId + "/gptsummary";

        // Construir a URL usando UriComponentsBuilder
        String url = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("page", 0)
                .queryParam("size", 999)
                .queryParam("sort", "string")
                .build()
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + tokenStore.getTokenStore().get(SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE); // define o formato de conteúdo da requisição
        HttpEntity httpEntity = new HttpEntity<>(headers);

        try{
            ResponseEntity<SummaryResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, SummaryResponse.class);
            // O certo é criar uma nova, mesmo que o tipo do objeto seja o mesmo
            return ResponseEntity.ok(responseEntity.getBody());
        }catch (Exception e){
            log.error("Erro na solicitação do resumo", e);
            return ResponseEntity.notFound().build();
        }

    }


}
