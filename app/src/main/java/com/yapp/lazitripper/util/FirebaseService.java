package com.yapp.lazitripper.util;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.yapp.lazitripper.dto.AllTravelInfo;

import java.util.ArrayList;
import java.util.List;

public class FirebaseService {

    private static volatile FirebaseService firebaseManager;
    private DatabaseReference mDatabase;
    private FirebaseUser user;
    private static ArrayList<AllTravelInfo> travelList = new ArrayList<>();
    private AllTravelInfo info = new AllTravelInfo();

    public static FirebaseService getInstance() {
        if (firebaseManager == null) {
            synchronized (FirebaseService.class) {
                if (firebaseManager == null) {
                    firebaseManager = new FirebaseService();
                }
            }
        }

        return firebaseManager;
    }

    public List<AllTravelInfo> getTravelList() {

        if (travelList.size() == 0)
            setFirebase();
        return travelList;
    }


    private FirebaseService() {
        mDatabase = FirebaseDatabase.getInstance().getReference("lazitripper");
        user = FirebaseAuth.getInstance().getCurrentUser();

    }

    public void setFirebase() {

        travelList.clear();

        mDatabase.child("user").child(user.getUid()).child("Travel")
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                        info = dataSnapshot.getValue(AllTravelInfo.class);

                        if (!travelList.contains(info) && info.getTravelTitle() != null)
                            if(info.getTravelTitle().length() > 0)
                                travelList.add(info);
                    }

                    @Override
                    public void onChildChanged(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onChildRemoved(DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(DataSnapshot dataSnapshot, String s) {

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

    }

    public FirebaseUser getUser() {

        return user;
    }

    public String getUserId() {

        return user.getUid();
    }
}
