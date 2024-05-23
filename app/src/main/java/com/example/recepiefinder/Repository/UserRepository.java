package com.example.recepiefinder.Repository;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.recepiefinder.Model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserRepository {
    private FirebaseDatabase firebaseDatabase;
    private MutableLiveData<User> userMutableLiveData;

    public UserRepository(){
        firebaseDatabase = FirebaseDatabase.getInstance();
        userMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<User> getUserMutableLiveData(String userId){
        getUserData(userId);
        return userMutableLiveData;
    }

    private void getUserData(String userId){
        DatabaseReference userRef = firebaseDatabase.getReference().child("Users").child(userId);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                userMutableLiveData.setValue(user);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
