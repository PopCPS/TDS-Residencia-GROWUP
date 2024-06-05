package com.example.exemplo.controller;

import com.example.exemplo.controller.dto.response.SummaryResponse;
import com.example.exemplo.services.SummaryService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
