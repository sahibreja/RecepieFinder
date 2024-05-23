package com.example.recepiefinder.Repository;

import android.app.Application;
import android.net.Uri;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.example.recepiefinder.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AuthenticationRepository {
    private Application application;
    private FirebaseAuth auth;
    private MutableLiveData<Boolean> userLoggedMutableLiveData;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    public AuthenticationRepository(Application application){
        this.application = application;
        userLoggedMutableLiveData = new MutableLiveData<>();
        auth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Users");

    }

    public MutableLiveData<Boolean> getUserLoggedMutableLiveData() {
        return userLoggedMutableLiveData;
    }

    public void login(String email , String pass){
        auth.signInWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if (task.isSuccessful()){
                    userLoggedMutableLiveData.postValue(true);
                }else{
                    userLoggedMutableLiveData.postValue(false);
                    Toast.makeText(application, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

        });
    }

    public void signUp(User user){
        auth.createUserWithEmailAndPassword(user.getUserEmail(), user.getUserPassword()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(Task<AuthResult> task) {
                if(task.isSuccessful()){
                    user.setUserId(auth.getCurrentUser().getUid());
                    addDataInFirebaseDatabase(user);
                    userLoggedMutableLiveData.postValue(true);
                }else{
                    userLoggedMutableLiveData.postValue(false);
                    Toast.makeText(application, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void addDataInFirebaseDatabase(User user){
        databaseReference.child(user.getUserId()).setValue(user);
    }
}
