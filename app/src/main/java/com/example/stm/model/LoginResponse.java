package com.example.stm.model;
import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("user_id")
    private int userId;

    public int getUserId() {
        return userId;
    }
}
