package com.example.stm.model;

import com.google.gson.annotations.SerializedName;

public class CheckSpamResponse {    @SerializedName("is_spam")
private boolean isSpam;

    public boolean isSpam() {
        return isSpam;
    }
}
