package com.example.exemplo.controller.dto.response;

import com.example.exemplo.controller.dto.AccessJourneyDTO;

import java.util.List;

public class PaginatedResponse {

    private List<AccessJourneyDTO> content;
    private long totalElements;

    public PaginatedResponse() {
    }

    public PaginatedResponse(long totalElements, List<AccessJourneyDTO> content) {
        this.totalElements = totalElements;
        this.content = content;
    }

    public PaginatedResponse(List<AccessJourneyDTO> content) {
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
