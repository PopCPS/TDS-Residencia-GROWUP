package com.example.exemplo.controller.dto.response;

import com.example.exemplo.controller.dto.AccessPointsDTO;

import java.util.List;

public class PointsResponse {


    private List<AccessPointsDTO> content;


    public PointsResponse() {
    }

    public PointsResponse(List<AccessPointsDTO> content) {
        this.content = content;
    }
    public List<AccessPointsDTO> getContent() {
        return content;
    }

    public void setContent(List<AccessPointsDTO> content) {
        this.content = content;
    }

}
