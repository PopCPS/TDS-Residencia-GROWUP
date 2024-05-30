package com.example.exemplo.controller.dto.response;

import com.example.exemplo.controller.dto.AccessSummaryDTO;

import java.util.List;

public class SummaryResponse {
    public List<AccessSummaryDTO> getContent() {
        return content;
    }

    public void setContent(List<AccessSummaryDTO> content) {
        this.content = content;
    }

    private List<AccessSummaryDTO> content;


    public SummaryResponse() {
    }

    public SummaryResponse(List<AccessSummaryDTO> content) {
        this.content = content;
    }
}
