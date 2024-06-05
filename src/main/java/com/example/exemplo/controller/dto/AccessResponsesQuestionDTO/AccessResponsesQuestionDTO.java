package com.example.exemplo.controller.dto.AccessResponsesQuestionDTO;

import java.util.List;

public class AccessResponsesQuestionDTO {
    private List<Comments> comments;

    public AccessResponsesQuestionDTO() {
    }

    public AccessResponsesQuestionDTO(List<Comments> comments) {
        this.comments = comments;
    }
    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }

}