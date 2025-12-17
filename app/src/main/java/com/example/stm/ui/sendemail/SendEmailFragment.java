package com.example.stm.ui.sendemail;

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
import com.example.stm.databinding.FragmentSendEmailBinding;

public class SendEmailFragment extends Fragment {

    private FragmentSendEmailBinding binding;
    private SendEmailViewModel sendEmailViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentSendEmailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize ViewModel
        sendEmailViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(requireActivity().getApplication()))
                .get(SendEmailViewModel.class);

        // Setup the Send button
        binding.buttonSend.setOnClickListener(v -> handleSendEmail());

        // Setup Bottom Navigation to navigate between main screens
        setupBottomNavigation();

        // Observe ViewModel for updates
        observeViewModel();
    }

    private void handleSendEmail() {
        String recipient = binding.editTextRecipient.getText().toString().trim();
        String subject = binding.editTextSubject.getText().toString().trim();
        String body = binding.editTextBody.getText().toString().trim();

        if (recipient.isEmpty() || subject.isEmpty() || body.isEmpty()) {
            Toast.makeText(getContext(), "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        sendEmailViewModel.sendEmail(recipient, subject, body);
    }

    private void observeViewModel() {
        sendEmailViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            binding.buttonSend.setEnabled(!isLoading);
            binding.buttonSend.setText(isLoading ? "Sending..." : "Send");
        });

        sendEmailViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
            }
        });

        sendEmailViewModel.getEmailSentResult().observe(getViewLifecycleOwner(), success -> {
            if (success != null && success) {
                Toast.makeText(getContext(), "Email Sent Successfully!", Toast.LENGTH_SHORT).show();
                // Navigate back to the dashboard to see the updated list
                NavHostFragment.findNavController(this).navigate(R.id.dashboardFragment);
            }
        });
    }

    private void setupBottomNavigation() {
        binding.bottomNavigation.setSelectedItemId(R.id.nav_send_email);
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_dashboard) {
                NavHostFragment.findNavController(this).navigate(R.id.dashboardFragment);
                return true;
            } else if (itemId == R.id.nav_send_email) {
                // Already here
                return true;
            } else if (itemId == R.id.nav_check_spam) {
                NavHostFragment.findNavController(this).navigate(R.id.checkSpamFragment);
                return true;
            } else if (itemId == R.id.nav_logout) {
                // Example: inside a click listener in a fragment
                ((MainActivity) requireActivity()).logout();
            }
            return false;
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
