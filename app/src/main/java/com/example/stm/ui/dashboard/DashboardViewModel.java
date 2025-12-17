package com.example.stm.ui.dashboard;

import android.app.Application;
import android.content.Context;import android.content.SharedPreferences;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.stm.api.ApiService;
import com.example.stm.api.RetrofitClient;
import com.example.stm.model.Email;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DashboardViewModel extends AndroidViewModel {

    private final MutableLiveData<List<Email>> emailList = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public DashboardViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<List<Email>> getEmailList() {
        return emailList;
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void fetchEmails() {
        isLoading.setValue(true);

        // Retrieve the saved user ID
        int userId = getUserId();
        if (userId == -1) {
            error.setValue("User not logged in.");
            isLoading.setValue(false);
            return;
        }

        ApiService apiService = RetrofitClient.getApiService();
        Call<List<Email>> call = apiService.getEmails(userId);

        call.enqueue(new Callback<List<Email>>() {
            @Override
            public void onResponse(Call<List<Email>> call, Response<List<Email>> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    emailList.setValue(response.body());
                } else {
                    error.setValue("Failed to fetch emails.");
                }
            }

            @Override
            public void onFailure(Call<List<Email>> call, Throwable t) {
                isLoading.setValue(false);
                error.setValue("Network error: " + t.getMessage());
            }
        });
    }

    private int getUserId() {
        SharedPreferences sharedPreferences = getApplication().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        // Return -1 if the user ID is not found
        return sharedPreferences.getInt("USER_ID", -1);
    }
}
