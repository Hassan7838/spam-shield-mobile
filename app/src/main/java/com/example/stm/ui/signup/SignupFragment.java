package com.example.stm.ui.signup;

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
import com.example.stm.databinding.FragmentSignupBinding;

public class SignupFragment extends Fragment {

    private FragmentSignupBinding binding;
    private SignupViewModel signupViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Use View Binding to inflate the layout
        binding = FragmentSignupBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize the ViewModel
        signupViewModel = new ViewModelProvider(this).get(SignupViewModel.class);

        // Set up the signup button click listener
        binding.buttonSignup.setOnClickListener(v -> {
            String username = binding.editTextUsername.getText().toString().trim();
            String email = binding.editTextEmail.getText().toString().trim();
            String password = binding.editTextPassword.getText().toString().trim();

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            } else {
                signupViewModel.signupUser(username, email, password);
            }
        });

        // Observe the ViewModel for results
        observeViewModel();
    }

    private void observeViewModel() {
        // Observer for loading state
        signupViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            binding.buttonSignup.setEnabled(!isLoading);
            binding.buttonSignup.setText(isLoading ? "Signing up..." : "Signup");
        });

        // Observer for signup errors
        signupViewModel.getSignupError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
            }
        });

        // Observer for successful signup
        signupViewModel.getSignupResult().observe(getViewLifecycleOwner(), success -> {
            if (success != null && success) {
                Toast.makeText(getContext(), "Signup Successful! Please login.", Toast.LENGTH_LONG).show();

                // Navigate back to the Welcome screen so the user can log in
                // You could also navigate directly to login: R.id.action_signupFragment_to_loginFragment
                NavHostFragment.findNavController(SignupFragment.this)
                        .popBackStack(R.id.welcomeFragment, false);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null; // Clean up view binding
    }
}
