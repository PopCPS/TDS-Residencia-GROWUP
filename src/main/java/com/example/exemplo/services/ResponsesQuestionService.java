package com.example.exemplo.services;

import com.example.exemplo.configuration.strateegia.StrateegiaInMemoryTokenStore;
import com.example.exemplo.controller.JourneysController;
import com.example.exemplo.controller.dto.AccessResponsesQuestionDTO.AccessResponsesQuestionDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import static com.example.exemplo.configuration.strateegia.StrateegiaRestTemplateConfiguration.STRATEEGIA_REST_TEMPLATE;

@Service
public class ResponsesQuestionService {
    private final StrateegiaInMemoryTokenStore tokenStore;

    private static final Logger log = LoggerFactory.getLogger(JourneysController.class);


    private final RestTemplate restTemplate;

    public ResponsesQuestionService(@Qualifier(STRATEEGIA_REST_TEMPLATE) RestTemplate restTemplate) { // @Qualifier serve para especificar qual @Bean importar. Como se fosse, chamar ele por um nome
        this.restTemplate = restTemplate;
        this.tokenStore = StrateegiaInMemoryTokenStore.getInstance();
    }

    @GetMapping("/responsesQuestion")
    public ResponseEntity<AccessResponsesQuestionDTO> getResponsesQuestion(String divergenceId, String questionID){
        String url = "/projects/v1/divergence-point/" + divergenceId+ "/question/" + questionID + "/comment/report";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + tokenStore.getTokenStore().get(SecurityContextHolder.getContext().getAuthentication().getPrincipal()));
        headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE); // define o formato de conteúdo da requisição
        HttpEntity httpEntity = new HttpEntity<>(headers);

        try {
            // Não da para retornar diretamente este responseEntity como retorno do método. Este ResponseEntity é a resposta de outra requisição http.
            ResponseEntity<AccessResponsesQuestionDTO> responseEntity = restTemplate.exchange(url, HttpMethod.GET, httpEntity, AccessResponsesQuestionDTO.class);
            // O certo é criar uma nova, mesmo que o tipo do objeto seja o mesmo
            return ResponseEntity.ok(responseEntity.getBody());
        } catch (Exception e) {
            log.error("Erro na solicitação das respostas das questões", e);
            return ResponseEntity.notFound().build();
        }
    }
}
