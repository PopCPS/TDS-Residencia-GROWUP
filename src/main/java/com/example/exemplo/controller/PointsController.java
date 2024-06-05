package com.example.exemplo.controller;

import com.example.exemplo.controller.dto.response.PointsResponse;
import com.example.exemplo.services.PointsService;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PointsController {
    private final PointsService pointsService;

    public PointsController(PointsService pointsService) {
        this.pointsService = pointsService;
    }

    @GetMapping("/points")
    public ResponseEntity<PointsResponse> getPoints(@RequestParam(value = "mapId", defaultValue = "660572d9feb2310be9fd8c40") String mapid) {
        return pointsService.getPoints(mapid);

    }
}
