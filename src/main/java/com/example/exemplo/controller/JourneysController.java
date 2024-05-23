package com.example.exemplo.controller;
import com.example.exemplo.configuration.strateegia.StrateegiaInMemoryTokenStore;
import com.example.exemplo.controller.DTO.AccessJourneyDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
public class JourneysController {
    private final StrateegiaInMemoryTokenStore tokenStore;

    private static final Logger log = LoggerFactory.getLogger(AuthenticationLogin.class);


    private final RestTemplate restTemplate;
    public JourneysController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        this.tokenStore = StrateegiaInMemoryTokenStore.getInstance();
    }

    @GetMapping("/journeys")
    public ResponseEntity<AccessJourneyDTO> getJourneys(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "sort", defaultValue = "title,DESC") String sort) {

        // Construindo a URL com UriComponentsBuilder
        String baseUrl = "https://api.strateegia.digital/projects/v1/project/summary";
        String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
                .queryParam("page", page)
                .queryParam("size", size)
                .queryParam("sort", sort)
                .toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + tokenStore.getTokenStore().get(SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE); // define o formato de conteúdo da requisição
        HttpEntity<String> httpEntity = new HttpEntity<>(null, headers);

        try {
            ResponseEntity<AccessJourneyDTO> response = restTemplate.exchange(url, HttpMethod.GET, httpEntity, AccessJourneyDTO.class);
            return response;
        } catch (Exception e) {
            log.info("Erro na solicitação das Jornadas" + e);
            return (ResponseEntity<AccessJourneyDTO>) ResponseEntity.notFound();
        }
    }
}
