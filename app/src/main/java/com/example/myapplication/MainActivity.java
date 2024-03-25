package com.example.myapplication;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Check if user is signed in (non-null) and update UI accordingly
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // User is already signed in, navigate to home activity
            startActivity(new Intent(MainActivity.this, HomeActivity.class));
            finish(); // Optional: Finish MainActivity to prevent going back to it
        } else {
            // User is not signed in, navigate to login or registration activity
            startActivity(new Intent(MainActivity.this, Login.class));
            // If you have separate registration and login screens, you can check registration state here
            // If not registered, navigate to RegisterActivity
            // If registered, navigate to LoginActivity
        }
    }
}
