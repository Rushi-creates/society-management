package com.example.myapplication;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class EventAddActivity extends AppCompatActivity {

    private EditText eventNameEditText;
    private Button saveButton;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_add);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Initialize Firestore
        db = FirebaseFirestore.getInstance();

        eventNameEditText = findViewById(R.id.eventNameEditText);
        saveButton = findViewById(R.id.saveButton);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveEvent();
            }
        });
    }

    private void saveEvent() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String eventName = eventNameEditText.getText().toString().trim();
            String userId = user.getUid();

            // Create a new event object with the event name and user ID
            Event event = new Event(eventName, userId);

            // Add a new document with a generated ID to the "events" collection
            db.collection("events")
                    .add(event)
                    .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                        @Override
                        public void onSuccess(DocumentReference documentReference) {
                            eventNameEditText.setText(""); // Clear the EditText after saving
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            // Handle errors
                        }
                    });
        }
    }
}


