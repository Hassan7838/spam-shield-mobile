package com.example.stm.ui.checkspam;

import android.graphics.Color;
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

import com.example.stm.MainActivity;
import com.example.stm.R;
import com.example.stm.databinding.FragmentCheckSpamBinding;

public class CheckSpamFragment extends Fragment {

    private FragmentCheckSpamBinding binding;
    private CheckSpamViewModel checkSpamViewModel;
    private Object logout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCheckSpamBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize ViewModel
        checkSpamViewModel = new ViewModelProvider(this).get(CheckSpamViewModel.class);

        // Setup the Check button
        binding.buttonCheck.setOnClickListener(v -> handleSpamCheck());

        // Setup Bottom Navigation
        setupBottomNavigation();

        // Observe ViewModel for updates
        observeViewModel();
    }

    private void handleSpamCheck() {
        String textToCheck = binding.editTextSpamText.getText().toString().trim();
        if (textToCheck.isEmpty()) {
            Toast.makeText(getContext(), "Please paste some text to check", Toast.LENGTH_SHORT).show();
            return;
        }
        checkSpamViewModel.performSpamCheck(textToCheck);
    }

    private void observeViewModel() {
        checkSpamViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            binding.buttonCheck.setEnabled(!isLoading);
            binding.buttonCheck.setText(isLoading ? "Checking..." : "Check");
            if (isLoading) {
                binding.cardResult.setVisibility(View.GONE); // Hide result card while checking
            }
        });

        checkSpamViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
            }
        });

        checkSpamViewModel.getSpamResult().observe(getViewLifecycleOwner(), result -> {
            if (result != null) {
                binding.cardResult.setVisibility(View.VISIBLE);
                binding.textViewResult.setText(result);
                // Change text color based on result for better UX
                if (result.equals("SPAM")) {
                    binding.textViewResult.setTextColor(Color.RED);
                } else {
                    binding.textViewResult.setTextColor(Color.parseColor("#006400")); // Dark Green
                }
            }
        });
    }

    private void setupBottomNavigation() {
        binding.bottomNavigation.setSelectedItemId(R.id.nav_check_spam);
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_dashboard) {
                NavHostFragment.findNavController(this).navigate(R.id.action_checkSpamFragment_to_dashboardFragment);
                return true;
            } else if (itemId == R.id.nav_send_email) {
                NavHostFragment.findNavController(this).navigate(R.id.action_checkSpamFragment_to_sendEmailFragment);
                return true;
            } else if (itemId == R.id.nav_check_spam) {
                // Already here
                return true;
            } else if (itemId == R.id.nav_logout) {
                // Example: inside a click listener in a fragment
                ((MainActivity) requireActivity()).logout();
            }
            // Add logout logic here later if needed
            return false;
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
