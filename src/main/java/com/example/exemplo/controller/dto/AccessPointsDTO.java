package com.example.exemplo.controller.dto;

import java.util.List;

public class AccessPointsDTO {
    private String id;
    private Tool tool = new Tool();
    public AccessPointsDTO() {
    }
    public AccessPointsDTO(String id,Tool tool) {
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
class Tool{
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

class Questions{
    private String id;
    private String question;

    public Questions() {
    }

    public Questions(String id, String question) {
        this.id = id;
        this.question = question;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

}
