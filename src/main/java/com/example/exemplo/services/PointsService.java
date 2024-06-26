package com.example.exemplo.services;
import com.example.exemplo.configuration.strateegia.StrateegiaInMemoryTokenStore;
import com.example.exemplo.controller.JourneysController;
import com.example.exemplo.controller.dto.response.PointsResponse;
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
public class PointsService {
    private final StrateegiaInMemoryTokenStore tokenStore;

    private static final Logger log = LoggerFactory.getLogger(JourneysController.class);

    private final RestTemplate restTemplate;

    public PointsService(@Qualifier(STRATEEGIA_REST_TEMPLATE) RestTemplate restTemplate) { // @Qualifier serve para especificar qual @Bean importar. Como se fosse, chamar ele por um nome
        this.restTemplate = restTemplate;
        this.tokenStore = StrateegiaInMemoryTokenStore.getInstance();
    }

    public ResponseEntity<PointsResponse> getPoints(String mapid){

        String baseUrl = "/projects/v1/map/" + mapid + "/divergence-point";

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
            ResponseEntity<PointsResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, PointsResponse.class);
            return ResponseEntity.ok(responseEntity.getBody());
        }catch (Exception e){
            log.error("Erro na solicitação dos pontos", e);
            return ResponseEntity.notFound().build();
        }

    }


}
