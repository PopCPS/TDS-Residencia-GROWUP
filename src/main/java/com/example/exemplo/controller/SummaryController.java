package com.example.exemplo.controller;

import com.example.exemplo.configuration.strateegia.StrateegiaInMemoryTokenStore;
import com.example.exemplo.controller.dto.response.PointsResponse;
import com.example.exemplo.controller.dto.response.SummaryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import static com.example.exemplo.configuration.strateegia.StrateegiaRestTemplateConfiguration.STRATEEGIA_REST_TEMPLATE;

@RestController
public class SummaryController {
    private final StrateegiaInMemoryTokenStore tokenStore;

    private static final Logger log = LoggerFactory.getLogger(JourneysController.class);

    private RestTemplate restTemplate;

    public SummaryController(@Qualifier(STRATEEGIA_REST_TEMPLATE) RestTemplate restTemplate) { // @Qualifier serve para especificar qual @Bean importar. Como se fosse, chamar ele por um nome
        this.restTemplate = restTemplate;
        this.tokenStore = StrateegiaInMemoryTokenStore.getInstance();
    }
    @GetMapping("/summary")
    public ResponseEntity<SummaryResponse> getSummary(
            @RequestParam(value = "divergencePointId") String divergencePointId,
            @RequestParam(value = "questionId") String questionId

    ){

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
