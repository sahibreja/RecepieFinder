package com.example.recepiefinder.ViewModel;

import android.app.Application;
import android.net.Uri;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.recepiefinder.Model.User;
import com.example.recepiefinder.Repository.AuthenticationRepository;
import com.google.firebase.auth.FirebaseUser;


public class AuthViewModel extends AndroidViewModel {

    private AuthenticationRepository repository;
    private MutableLiveData<Boolean> loggedStatus;

    public AuthViewModel(Application application){
        super(application);
        repository = new AuthenticationRepository(application);
        loggedStatus = repository.getUserLoggedMutableLiveData();
    }
    public void signUp(User user){
        repository.signUp(user);
    }

    public void logIn(String email , String pass){
        repository.login(email, pass);
    }


    public MutableLiveData<Boolean> getLoggedStatus() {
        return loggedStatus;
    }
}
