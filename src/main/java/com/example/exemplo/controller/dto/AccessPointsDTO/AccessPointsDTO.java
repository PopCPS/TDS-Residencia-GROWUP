package com.example.exemplo.controller.dto.AccessPointsDTO;

public class AccessPointsDTO {
    private String id;
    private Tool tool;
    public AccessPointsDTO() {
    }
    public AccessPointsDTO(String id, Tool tool) {
        this.tool = tool;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Tool getTool() {
        return tool;
    }

    public void setTool(Tool tool) {
        this.tool = tool;
    }
}
