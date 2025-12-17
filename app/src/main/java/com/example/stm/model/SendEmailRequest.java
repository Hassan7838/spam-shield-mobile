package com.example.stm.model;

public class SendEmailRequest {
    private int user_id;
    private String recipient;private String subject;
    private String body;

    public SendEmailRequest(int userId, String recipient, String subject, String body) {
        this.user_id = userId;
        this.recipient = recipient;
        this.subject = subject;
        this.body = body;
    }
}
