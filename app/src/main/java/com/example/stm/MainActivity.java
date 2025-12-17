package com.example.stm;

import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.example.stm.databinding.ActivityMainBinding;

import android.content.Context;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Use ViewBinding to set the content view for this activity
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // That's it!
        // The NavHostFragment in activity_main.xml takes over from here
        // and displays the start destination from your nav_graph.
    }

    public void logout() {
        // Step 1: Clear the saved user token
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE);
        sharedPreferences.edit().remove("jwt_token").apply();

        // Step 2: Go back to the very first screen and clear all other screens
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish(); // Close the current screen
    }
}
