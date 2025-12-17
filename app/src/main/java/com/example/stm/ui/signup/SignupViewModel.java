package com.example.stm.ui.signup;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.stm.api.ApiService;
import com.example.stm.api.RetrofitClient;
import com.example.stm.model.SignupRequest;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupViewModel extends ViewModel {

    // LiveData to hold the signup result. Boolean represents success.
    private final MutableLiveData<Boolean> signupResult = new MutableLiveData<>();
    private final MutableLiveData<String> signupError = new MutableLiveData<>();
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public LiveData<Boolean> getSignupResult() {
        return signupResult;
    }

    public LiveData<String> getSignupError() {
        return signupError;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void signupUser(String username, String email, String password) {
        isLoading.setValue(true);

        ApiService apiService = RetrofitClient.getApiService();
        SignupRequest signupRequest = new SignupRequest(username, email, password);

        // Your signup API call returns a message, so we can use Call<Void>
        Call<Void> call = apiService.signup(signupRequest);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                isLoading.setValue(false);
                if (response.isSuccessful()) {
                    // Signup was successful
                    signupResult.setValue(true);
                } else {
                    // Handle server errors (e.g., username already exists)
                    signupError.setValue("Signup failed. User might already exist.");
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                isLoading.setValue(false);
                signupError.setValue("Network error: " + t.getMessage());
            }
        });
    }
}
