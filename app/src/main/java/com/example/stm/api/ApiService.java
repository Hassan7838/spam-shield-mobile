package com.example.stm.api;

import com.example.stm.model.CheckSpamRequest;
import com.example.stm.model.CheckSpamResponse;
import com.example.stm.model.Email;
import com.example.stm.model.LoginRequest;
import com.example.stm.model.LoginResponse;
import com.example.stm.model.SendEmailRequest;
import com.example.stm.model.SignupRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

// Note: I'm using your actual package 'com.example.stm.model' for the imports.

public interface ApiService {

    @POST("signup")
    Call<Void> signup(@Body SignupRequest signupRequest);

    @POST("login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("check-spam")
    Call<CheckSpamResponse> checkSpam(@Body CheckSpamRequest checkSpamRequest);

    @POST("send-email")
    Call<Void> sendEmail(@Body SendEmailRequest sendEmailRequest);

    @GET("emails")
    Call<List<Email>> getEmails(@Query("user_id") int userId);

}
