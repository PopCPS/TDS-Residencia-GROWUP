package com.example.exemplo.controller.dto.response;

import com.example.exemplo.controller.dto.AccessSummaryDTO;

import java.util.List;

public class SummaryResponse {

    private List<AccessSummaryDTO> content;

    public SummaryResponse() {
    }
    public SummaryResponse(List<AccessSummaryDTO> content) {
        this.content = content;
    }
    public List<AccessSummaryDTO> getContent() {
        return content;
    }

    public void setContent(List<AccessSummaryDTO> content) {
        this.content = content;
    }


}
