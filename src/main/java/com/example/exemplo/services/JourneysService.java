package com.example.exemplo.services;

import com.example.exemplo.configuration.strateegia.StrateegiaInMemoryTokenStore;
import com.example.exemplo.controller.JourneysController;
import com.example.exemplo.controller.dto.response.JourneysResponse;
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
public class JourneysService {
    private final StrateegiaInMemoryTokenStore tokenStore;

    private static final Logger log = LoggerFactory.getLogger(JourneysController.class);


    private final RestTemplate restTemplate;

    public JourneysService(@Qualifier(STRATEEGIA_REST_TEMPLATE) RestTemplate restTemplate) { // @Qualifier serve para especificar qual @Bean importar. Como se fosse, chamar ele por um nome
        this.tokenStore = StrateegiaInMemoryTokenStore.getInstance();
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<JourneysResponse> getJourneys(int page, int size, String sort) {

        // Construindo a URI com UriComponentsBuilder
        String baseUrl = "/projects/v1/project/summary";
        String url = UriComponentsBuilder.fromUriString(baseUrl)
                .queryParam("page", page)
                .queryParam("size", size)
                .queryParam("sort", sort)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + tokenStore.getTokenStore().get(SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE); // define o formato de conteúdo da requisição
        HttpEntity httpEntity = new HttpEntity<>(headers);

        try {
            // Não da para retornar diretamente este responseEntity como retorno do método. Este ResponseEntity é a resposta de outra requisição http.
            ResponseEntity<JourneysResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, JourneysResponse.class);
            // O certo é criar uma nova, mesmo que o tipo do objeto seja o mesmo
            return ResponseEntity.ok(responseEntity.getBody());
        } catch (Exception e) {
            log.error("Erro na solicitação das Jornadas", e);
            return ResponseEntity.notFound().build();
        }


    }
}
