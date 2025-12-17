package com.example.stm.model;
import com.google.gson.annotations.SerializedName;

public class Email {
    private int id;
    private String recipient;
    private String subject;

    @SerializedName("sent_at")
    private String sentAt;

    @SerializedName("opened_at")
    private String openedAt;

    public int getId() {
        return id;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getSubject() {
        return subject;
    }

    public String getSentAt() {
        return sentAt;
    }

    public String getOpenedAt() {
        return openedAt;
    }

    public String getStatus() {
        // We derive the status based on whether opened_at is present
        return (openedAt != null && !openedAt.isEmpty()) ? "OPENED" : "SENT";
    }
}
