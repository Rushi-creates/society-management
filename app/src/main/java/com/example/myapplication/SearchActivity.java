package com.example.myapplication;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {

    private SearchView searchView;
    private ListView eventsListView;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> eventsList;

    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        db = FirebaseFirestore.getInstance();

        searchView = findViewById(R.id.searchView);
        eventsListView = findViewById(R.id.eventsListView);
        eventsList = new ArrayList<>();
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, eventsList);
        eventsListView.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchEvents(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // You can implement live search here if needed
                return false;
            }
        });
    }

    private void searchEvents(String query) {
        db.collection("events")
                .whereEqualTo("eventName", query)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        eventsList.clear(); // Clear previous search results
                        for (QueryDocumentSnapshot documentSnapshot : queryDocumentSnapshots) {
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
                        Toast.makeText(SearchActivity.this, "Failed to search events: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
