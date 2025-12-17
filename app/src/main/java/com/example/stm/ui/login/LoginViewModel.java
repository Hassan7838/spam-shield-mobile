package com.example.stm.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.stm.api.ApiService;
import com.example.stm.api.RetrofitClient;
import com.example.stm.model.LoginRequest;
import com.example.stm.model.LoginResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginViewModel extends ViewModel {

    // LiveData to hold the login result. Integer represents the user_id on success.
    private final MutableLiveData<Integer> loginResult = new MutableLiveData<>();
    // LiveData to hold error messages.
    private final MutableLiveData<String> loginError = new MutableLiveData<>();
    // LiveData to track the loading state.
    private final MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    // Public LiveData that the Fragment can observe.
    public LiveData<Integer> getLoginResult() {
        return loginResult;
    }

    public LiveData<String> getLoginError() {
        return loginError;
    }

    public LiveData<Boolean> getIsLoading() {
        return isLoading;
    }

    public void loginUser(String username, String password) {
        // Start loading
        isLoading.setValue(true);

        ApiService apiService = RetrofitClient.getApiService();
        LoginRequest loginRequest = new LoginRequest(username, password);

        Call<LoginResponse> call = apiService.login(loginRequest);

        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                // Stop loading
                isLoading.setValue(false);

                if (response.isSuccessful() && response.body() != null) {
                    // Post the user_id on successful login
                    loginResult.setValue(response.body().getUserId());
                } else {
                    // Post an error message on failure
                    loginError.setValue("Invalid credentials. Please try again.");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                // Stop loading
                isLoading.setValue(false);
                // Post a network error message
                loginError.setValue("Network error: " + t.getMessage());
            }
        });
    }
}
