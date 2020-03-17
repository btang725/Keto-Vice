package com.example.test;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class userManager {
    private final FirebaseDatabase database = FirebaseDatabase.getInstance();

    private Map<String, Map<String, String>> courses;
    private ArrayList<String> depts;
    private ArrayList<String> userCourses;
    private Map<String, ArrayList<Map<String, String>>> chats;

    private String userId;
    private DatabaseReference userRef;

    public userManager(String userId) {
        depts = new ArrayList<String>();
        courses = new HashMap<String, Map<String, String>>();
        userCourses = new ArrayList<String>();
        chats = new HashMap<String, ArrayList<Map<String, String>>>();

        this.userId = userId.replace('.',',');

        // Get all chat data
        DatabaseReference chatsRef = database.getReference("chats");
        //chatsListeners(chatsRef);

        // Get reference to all users
        DatabaseReference AllUsersRef = database.getReference("Users");

        // Get reference to this specific user
        userRef = AllUsersRef.child(this.userId);

        // Grab data for this specific user from firebase
        //userCoursesListener(userRef);

        // Used for testing purposes
        DatabaseReference deptsRef= database.getReference("depts");
        //deptListeners(deptsRef);

    }

    public int getWeight() {
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("Weight is: " + dataSnapshot.child("weight"));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        return 1;
    }

    public int getHeight() {
        return 1;
    }

}
