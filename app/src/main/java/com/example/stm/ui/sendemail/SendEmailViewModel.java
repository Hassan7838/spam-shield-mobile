package com.example.stm.ui.sendemail;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.stm.api.ApiService;
import com.example.stm.api.RetrofitClient;
import com.example.stm.model.SendEmailRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SendEmailViewModel extends AndroidViewModel {

    private final MutableLiveData<Boolean> emailSentResult = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public SendEmailViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<Boolean> getEmailSentResult() {
        return emailSentResult;
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void sendEmail(String recipient, String subject, String body) {
        isLoading.setValue(true);

        int userId = getUserId();
        if (userId == -1) {
            error.setValue("Error: User not logged in.");
            isLoading.setValue(false);
            return;
        }

        ApiService apiService = RetrofitClient.getApiService();
        SendEmailRequest request = new SendEmailRequest(userId, recipient, subject, body);

        apiService.sendEmail(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                isLoading.setValue(false);
                if (response.isSuccessful()) {
                    emailSentResult.setValue(true);
                } else {
                    error.setValue("Failed to send email. Server error.");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                isLoading.setValue(false);
                error.setValue("Network Error: " + t.getMessage());
            }
        });
    }

    private int getUserId() {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getInt("USER_ID", -1);
    }
}
