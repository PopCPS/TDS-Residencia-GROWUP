package com.example.exemplo.controller;
import com.example.exemplo.controller.dto.response.MapsResponse;
import com.example.exemplo.services.MapsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class MapsController {
    private final MapsService mapsService;

    @Autowired
    public MapsController(MapsService mapsService) {
        this.mapsService = mapsService;
    }
    @GetMapping("/maps")
    public ResponseEntity<MapsResponse> getMaps(@RequestParam(value = "journeyId", defaultValue = "660572d9feb2310be9fd8c3f") String id) {
        return mapsService.getMaps(id);
    }
}

