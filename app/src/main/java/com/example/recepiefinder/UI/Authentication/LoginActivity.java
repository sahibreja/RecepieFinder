package com.example.recepiefinder.UI.Authentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.example.recepiefinder.MainActivity;
import com.example.recepiefinder.R;
import com.example.recepiefinder.ViewModel.AuthViewModel;
import com.example.recepiefinder.databinding.ActivityLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;
    private AuthViewModel viewModel;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        init();
        observer();
        onButtonClick();

    }

    private void init(){
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getApplication())).get(AuthViewModel.class);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Login");
        progressDialog.setMessage("Please wait .. \nWe are logging you into you account");
        progressDialog.setCancelable(false);
        progressDialog.setInverseBackgroundForced(false);
    }

    private void onButtonClick(){
        binding.signupHereBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
            }
        });

        binding.signInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = binding.emailEditTxt.getText().toString().trim();
                String pass = binding.passwordEditTxt.getText().toString().trim();
                if(checkEntryField(email,pass)){
                    login(email,pass);
                }
            }
        });
    }

    private boolean checkEntryField(String email, String pass){
        if(email.isEmpty()){
            binding.emailEditTxt.setError("Please enter your email");
            return false;
        }else if(pass.isEmpty()){
            binding.passwordEditTxt.setError("Please enter your password");
            return false;
        }else{
            return true;
        }
    }

    private void login(String email,String password){
        progressDialog.show();
        viewModel.logIn(email,password);
    }

    private void observer(){
        viewModel.getLoggedStatus().observe(LoginActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    finish();
                }
                progressDialog.dismiss();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            finish();
        }
    }
}