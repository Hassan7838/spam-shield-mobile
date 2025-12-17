package com.example.stm.ui.welcome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.stm.R;
import com.example.stm.databinding.FragmentWelcomeBinding;

public class WelcomeFragment extends Fragment {

    private FragmentWelcomeBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment using View Binding
        binding = FragmentWelcomeBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set the click listener for the Login button
        binding.buttonLogin.setOnClickListener(v -> {
            // Use the NavController to navigate to the LoginFragment.
            // This ID comes from the <action> tag in your nav_graph.xml.
            NavHostFragment.findNavController(WelcomeFragment.this)
                    .navigate(R.id.action_welcomeFragment_to_loginFragment);
        });

        // Set the click listener for the Sign Up button
        binding.buttonSignup.setOnClickListener(v -> {
            // Use the NavController to navigate to the SignupFragment.
            // This ID also comes from the <action> tag in your nav_graph.xml.
            NavHostFragment.findNavController(WelcomeFragment.this)
                    .navigate(R.id.action_welcomeFragment_to_signupFragment);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Clean up the binding object to prevent memory leaks
        binding = null;
    }
}
