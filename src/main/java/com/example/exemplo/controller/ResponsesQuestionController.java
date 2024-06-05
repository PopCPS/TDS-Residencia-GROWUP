package com.example.exemplo.controller;

import com.example.exemplo.controller.dto.AccessResponsesQuestionDTO.AccessResponsesQuestionDTO;
import com.example.exemplo.services.ResponsesQuestionService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ResponsesQuestionController {

    private final ResponsesQuestionService responsesQuestionService;

    public ResponsesQuestionController(ResponsesQuestionService responsesQuestionService1){
        this.responsesQuestionService = responsesQuestionService1;
    }


    @GetMapping("/responsesQuestion")
    public ResponseEntity<AccessResponsesQuestionDTO> getJourneys(
            @RequestParam(value = "divergenceId", defaultValue = "660eacebfeb2310be9fda476") String divergenceId,
            @RequestParam(value = "questionID", defaultValue = "0c79dc81-43a8-469f-9fce-910e38f711ba") String questionID) {

        return responsesQuestionService.getResponsesQuestion(divergenceId, questionID);
    }
}
