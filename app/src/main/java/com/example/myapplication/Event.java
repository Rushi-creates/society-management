package com.example.myapplication;

public class Event {

    private String eventName;
    private String userId;

    public Event() {
        // Default constructor required for Firestore
    }

    public Event(String eventName, String userId) {
        this.eventName = eventName;
        this.userId = userId;
    }

    public String getEventName() {
        return eventName;
    }

    public String getUserId() {
        return userId;
    }
}
