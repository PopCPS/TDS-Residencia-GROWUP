package com.example.exemplo.controller.dto;

public class AccessSummaryDTO {
    private String id;
    private String summary;

    public AccessSummaryDTO() {
    }
    public AccessSummaryDTO(String id, String summary) {
        this.id = id;
        this.summary = summary;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

}
