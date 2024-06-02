package com.example.exemplo.services;

import com.example.exemplo.configuration.strateegia.StrateegiaInMemoryTokenStore;
import com.example.exemplo.controller.JourneysController;
import com.example.exemplo.controller.dto.response.MapsResponse;
import lombok.NoArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static com.example.exemplo.configuration.strateegia.StrateegiaRestTemplateConfiguration.STRATEEGIA_REST_TEMPLATE;

@Service
public class MapsService {
    private final StrateegiaInMemoryTokenStore tokenStore;
    private final RestTemplate restTemplate;
    private static final Logger log = LoggerFactory.getLogger(MapsService.class);

    @Autowired
    public MapsService(@Qualifier(STRATEEGIA_REST_TEMPLATE) RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.tokenStore = StrateegiaInMemoryTokenStore.getInstance();
    }

    public ResponseEntity<MapsResponse> getMaps(String id) {
        String url = "/projects/v1/project/" + id;

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + tokenStore.getTokenStore().get(SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE); // define o formato de conteúdo da requisição
        HttpEntity<?> httpEntity = new HttpEntity<>(headers);

        try {
            ResponseEntity<MapsResponse> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, MapsResponse.class);
            return ResponseEntity.ok(responseEntity.getBody());
        } catch (Exception e) {
            log.error("Erro na solicitação dos mapas", e);
            return ResponseEntity.notFound().build();
        }
    }
}
