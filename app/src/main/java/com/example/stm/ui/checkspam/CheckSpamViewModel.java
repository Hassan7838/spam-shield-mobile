package com.example.stm.ui.checkspam;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.stm.api.ApiService;
import com.example.stm.api.RetrofitClient;
import com.example.stm.model.CheckSpamRequest;
import com.example.stm.model.CheckSpamResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CheckSpamViewModel extends ViewModel {

    // LiveData to hold the result string ("SPAM" or "NOT SPAM")
    private final MutableLiveData<String> spamResult = new MutableLiveData<>();
    private final MutableLiveData<String> error = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public LiveData<String> getSpamResult() {
        return spamResult;
    }

    public LiveData<String> getError() {
        return error;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void performSpamCheck(String text) {
        isLoading.setValue(true);
        spamResult.setValue(null); // Clear previous results
        error.setValue(null);      // Clear previous errors

        ApiService apiService = RetrofitClient.getApiService();
        CheckSpamRequest request = new CheckSpamRequest(text);

        apiService.checkSpam(request).enqueue(new Callback<CheckSpamResponse>() {
            @Override
            public void onResponse(Call<CheckSpamResponse> call, Response<CheckSpamResponse> response) {
                isLoading.setValue(false);
                if (response.isSuccessful() && response.body() != null) {
                    if (response.body().isSpam()) {
                        spamResult.setValue("SPAM");
                    } else {
                        spamResult.setValue("NOT SPAM");
                    }
                } else {
                    error.setValue("Failed to get a result from the server.");
                }
            }

            @Override
            public void onFailure(Call<CheckSpamResponse> call, Throwable t) {
                isLoading.setValue(false);
                error.setValue("Network Error: " + t.getMessage());
            }
        });
    }
}
