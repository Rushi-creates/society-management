package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private TextView displayNameTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mAuth = FirebaseAuth.getInstance();

        // Initialize views
        displayNameTextView = findViewById(R.id.textView);

        Button logoutButton = findViewById(R.id.btn_logout);
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logout();
            }
        });

        //
        //

        //
        //


        Button displayPostButton = findViewById(R.id.btn_display_event);
        displayPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start activity_add_event page
                startActivity(new Intent(HomeActivity.this, EventListActivity.class));
            }
        });


        //


        Button addPostButton = findViewById(R.id.btn_add_event);
        addPostButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Start activity_add_event page
                startActivity(new Intent(HomeActivity.this, EventAddActivity.class));
            }
        });


   //


        // Display user's display name
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
//            String displayName = user.getDisplayName();
            String displayEmail = user.getEmail();
            String displayUid = user.getUid();

            if (displayEmail != null && !displayEmail.isEmpty()) {
                displayNameTextView.setText(displayEmail +  "\n" +displayUid);
            } else {
                displayNameTextView.setText("Details not available");
            }
            //

        }
    }



    public void logout() {
        mAuth.signOut();
        startActivity(new Intent(HomeActivity.this, Login.class));
        finish();
    }
}
