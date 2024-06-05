package com.example.exemplo.controller.dto.AccessPointsDTO;

import java.util.List;

public class Tool {
    private String title;
    public List<Questions> questions;

    public Tool() {
    }

    public Tool(String title, List<Questions> questions) {
        this.title = title;
        this.questions = questions;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Questions> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Questions> questions) {
        this.questions = questions;
    }

}
