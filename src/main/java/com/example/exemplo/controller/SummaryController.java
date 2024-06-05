package com.example.exemplo.controller;

import com.example.exemplo.configuration.strateegia.StrateegiaInMemoryTokenStore;
import com.example.exemplo.controller.dto.response.PointsResponse;
import com.example.exemplo.controller.dto.response.SummaryResponse;
import com.example.exemplo.services.SummaryService;
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

    public final SummaryService summaryService;

    public SummaryController(SummaryService summaryService) {
        this.summaryService = summaryService;
    }

    @GetMapping("/summary")
    public ResponseEntity<SummaryResponse> getSummary(
            @RequestParam(value = "divergencePointId", defaultValue = "660eacebfeb2310be9fda476") String divergencePointId,
            @RequestParam(value = "questionId", defaultValue = "ff0bc0c4-bd28-4fcb-b312-1e592c184f16") String questionId) {
        return summaryService.getSummary(divergencePointId, questionId);
    }
}
