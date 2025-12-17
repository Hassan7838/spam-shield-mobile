package com.example.stm.ui.dashboard;

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
import androidx.navigation.ui.NavigationUI;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.stm.MainActivity;
import com.example.stm.R;
import com.example.stm.databinding.FragmentDashboardBinding;

public class DashboardFragment extends Fragment {

    private FragmentDashboardBinding binding;
    private DashboardViewModel dashboardViewModel;
    private EmailListAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentDashboardBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize ViewModel
        dashboardViewModel = new ViewModelProvider(this,
                ViewModelProvider.AndroidViewModelFactory.getInstance(getActivity().getApplication()))
                .get(DashboardViewModel.class);

        // Setup RecyclerView
        setupRecyclerView();

        // Setup Bottom Navigation
        setupBottomNavigation();

        // Observe ViewModel for data and updates
        observeViewModel();

        // Fetch the emails when the view is created
        dashboardViewModel.fetchEmails();
    }

    private void setupRecyclerView() {
        adapter = new EmailListAdapter();
        binding.recyclerViewEmails.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerViewEmails.setAdapter(adapter);
    }

    // Inside DashboardFragment.java
    private void setupBottomNavigation() {
        binding.bottomNavigation.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_dashboard) {
                return true; // Already here
            } else if (itemId == R.id.nav_send_email) {
                NavHostFragment.findNavController(this).navigate(R.id.action_dashboardFragment_to_sendEmailFragment);
                return true;
            } else if (itemId == R.id.nav_check_spam) {
                NavHostFragment.findNavController(this).navigate(R.id.action_dashboardFragment_to_checkSpamFragment);
                return true;
            }else if (itemId == R.id.nav_logout) {
                // Example: inside a click listener in a fragment
                ((MainActivity) requireActivity()).logout();
            }
            // ... (logout logic)
            return false;
        });
    }


//    private void setupBottomNavigation() {
//        // This line connects the BottomNavigationView with the NavController.
//        // It automatically handles clicks to navigate to the correct destination
//        // based on the menu item's ID (e.g., R.id.nav_send_email -> sendEmailFragment).
//        // For this to work, the menu item IDs MUST match the fragment IDs in nav_graph.xml
//        // We will do this for the other fragments later.
//
//        // Let's manually set up listeners for now to show a Toast.
//        binding.bottomNavigation.setOnItemSelectedListener(item -> {
//            int itemId = item.getItemId();
//            if (itemId == R.id.nav_dashboard) {
//                // Already here
//                return true;
//            } else if (itemId == R.id.nav_send_email) {
//                Toast.makeText(getContext(), "Navigate to Send Email", Toast.LENGTH_SHORT).show();
//                // NavHostFragment.findNavController(this).navigate(R.id.action_dashboard_to_send_email);
//                return true;
//            } else if (itemId == R.id.nav_check_spam) {
//                Toast.makeText(getContext(), "Navigate to Check Spam", Toast.LENGTH_SHORT).show();
//                // NavHostFragment.findNavController(this).navigate(R.id.action_dashboard_to_check_spam);
//                return true;
//            } else if (itemId == R.id.nav_logout) {
//                Toast.makeText(getContext(), "Logout clicked", Toast.LENGTH_SHORT).show();
//                // Implement logout logic here
//                return true;
//            }
//            return false;
//        });
//    }

    private void observeViewModel() {
        dashboardViewModel.getIsLoading().observe(getViewLifecycleOwner(), isLoading -> {
            // You can add a ProgressBar here and show/hide it.
            // For now, we'll just log it.
        });

        dashboardViewModel.getError().observe(getViewLifecycleOwner(), error -> {
            if (error != null) {
                Toast.makeText(getContext(), error, Toast.LENGTH_LONG).show();
            }
        });

        dashboardViewModel.getEmailList().observe(getViewLifecycleOwner(), emails -> {
            if (emails != null) {
                adapter.setEmails(emails);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
