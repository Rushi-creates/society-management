package com.example.myapplication;


import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;

public class EventListActivity extends AppCompatActivity {

    private ListView eventsListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> eventsList;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        db = FirebaseFirestore.getInstance();

        eventsListView = findViewById(R.id.eventsListView);
        eventsList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventsList);
        eventsListView.setAdapter(adapter);

        fetchEvents();
    }

    private void fetchEvents() {
        db.collection("events")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
                            // Get data from Firestore document
                            String eventName = documentSnapshot.getString("eventName");
                            String userId = documentSnapshot.getString("userId");
                            eventsList.add("Event: " + eventName + ", User ID: " + userId);
                        }
                        adapter.notifyDataSetChanged(); // Notify adapter about the change in data
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(EventListActivity.this, "Failed to fetch events: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
