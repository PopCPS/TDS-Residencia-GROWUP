package com.example.exemplo.controller;

import com.example.exemplo.controller.dto.response.JourneysResponse;
import com.example.exemplo.services.JourneysService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class JourneysController {

    private final JourneysService journeysService;
    @Autowired
    public JourneysController(JourneysService journeysService) {
        this.journeysService = journeysService;
    }


    @GetMapping("/journeys")
    public ResponseEntity<JourneysResponse> getJourneys(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "5") int size,
            @RequestParam(value = "sort", defaultValue = "title,DESC") String sort) {

        return journeysService.getJourneys(page, size, sort);

    }
}
