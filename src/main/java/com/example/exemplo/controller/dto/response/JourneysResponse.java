package com.example.exemplo.controller.dto.response;

import com.example.exemplo.controller.dto.AccessJourneyDTO;

import java.util.List;

public class JourneysResponse {

    private List<AccessJourneyDTO> content;
    private long totalElements;

    public JourneysResponse() {
    }

    public JourneysResponse(long totalElements, List<AccessJourneyDTO> content) {
        this.totalElements = totalElements;
        this.content = content;
    }

    public JourneysResponse(List<AccessJourneyDTO> content) {
        this.content = content;
    }

    public List<AccessJourneyDTO> getContent() {
        return content;
    }

    public void setContent(List<AccessJourneyDTO> content) {
        this.content = content;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }
}
