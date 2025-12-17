package com.example.stm.ui.login;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.stm.R;
import com.example.stm.databinding.FragmentLoginBinding;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private LoginViewModel loginViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout using View Binding
        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the ViewModel
        loginViewModel = new ViewModelProvider(this).get(LoginViewModel.class);

        // Set up the login button click listener
        binding.buttonLogin.setOnClickListener(v -> {
            String username = binding.editTextEmail.getText().toString().trim();
            String password = binding.editTextPassword.getText().toString().trim();

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(getContext(), "Please enter username and password", Toast.LENGTH_SHORT).show();
            } else {
                loginViewModel.loginUser(username, password);
            }
        });

        // Observe the ViewModel for results
        observeViewModel();
    }

    private void observeViewModel() {
        // Observer for loading state
        loginViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            binding.buttonLogin.setEnabled(!isLoading);
            binding.buttonLogin.setText(isLoading ? "Logging in..." : "Login");
        });

        // Observer for login errors
        loginViewModel.getLoginError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
            }
        });

        // Observer for successful login
        loginViewModel.getLoginResult().observe(getViewLifecycleOwner(), userId -> {
            if (userId != null) {
                Toast.makeText(getContext(), "Login Successful!", Toast.LENGTH_SHORT).show();

                // Save the user_id for future API calls
                saveUserId(userId);

                // Navigate to the dashboard
                NavHostFragment.findNavController(LoginFragment.this)
                        .navigate(R.id.action_loginFragment_to_dashboardFragment);
            }
        });
    }

    private void saveUserId(int userId) {
        // Use SharedPreferences to store the user ID persistently
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("USER_ID", userId);
        editor.apply();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Clean up binding to prevent memory leaks
    }
}
