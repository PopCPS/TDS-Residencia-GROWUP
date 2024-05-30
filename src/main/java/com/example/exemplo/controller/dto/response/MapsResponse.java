package com.example.exemplo.controller.dto.response;

import com.example.exemplo.controller.dto.AccessMapsDTO;

import java.util.List;

public class MapsResponse {
    private String title;

    private List<AccessMapsDTO> maps;

    public MapsResponse() {
    }
    public MapsResponse(String title, List<AccessMapsDTO> maps) {
        this.title = title;
        this.maps = maps;
    }

    public MapsResponse(List<AccessMapsDTO> maps) {
        this.maps = maps;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<AccessMapsDTO> getMaps() {
        return maps;
    }
    public void setMaps(List<AccessMapsDTO> maps) {
        this.maps = maps;
    }




}
