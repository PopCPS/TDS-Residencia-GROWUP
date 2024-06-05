package com.example.exemplo.controller.dto.AccessResponsesQuestionDTO;

import java.util.List;

public class Comments {
    public String text;
    public List<Replies> replies;

    public Comments() {
    }

    public Comments(String text, List<Replies> replies) {
        this.text = text;
        this.replies = replies;
    }
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Replies> getReplies() {
        return replies;
    }

    public void setReplies(List<Replies> replies) {
        this.replies = replies;
    }


}
