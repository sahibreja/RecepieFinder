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
import com.example.recepiefinder.Model.User;
import com.example.recepiefinder.R;
import com.example.recepiefinder.ViewModel.AuthViewModel;
import com.example.recepiefinder.databinding.ActivitySignUpBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {
    private ActivitySignUpBinding binding;
    private AuthViewModel viewModel;
    private ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivitySignUpBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        init();
        observer();
        onButtonClick();

    }

    private void init(){
        viewModel = new ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory
                .getInstance(getApplication())).get(AuthViewModel.class);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Sign Up");
        progressDialog.setMessage("Please wait .. \nWe are creating your account");
        progressDialog.setCancelable(false);
        progressDialog.setInverseBackgroundForced(false);
    }
    private void onButtonClick(){
        binding.signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = binding.nameEditText.getText().toString().trim();
                String email = binding.emailEditTxt.getText().toString().trim();
                String pass = binding.passwordEditTxt.getText().toString().trim();
                String confirmPass = binding.confirmPasswordEditTxt.getText().toString().trim();
                if(checkEntryField(name,email,pass,confirmPass)){
                    User user = new User(name,email,pass);
                    signUp(user);
                }
            }
        });

        binding.signInHereBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void observer(){
        viewModel.getLoggedStatus().observe(SignUpActivity.this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    startActivity(new Intent(SignUpActivity.this, MainActivity.class));
                    finishAffinity();
                }
                progressDialog.dismiss();
            }
        });
    }

    private boolean checkEntryField(String name,String email,String pass,String confirmPass){

        if(name.isEmpty()){
            binding.nameEditText.setError("Please Enter Your Name");
            return false;
        }else if(email.isEmpty()){
            binding.emailEditTxt.setError("Please Enter Your Email");
            return false;
        }else if(pass.isEmpty()){
            binding.passwordEditTxt.setError("Please Enter Your Password");
            return false;
        }else if(confirmPass.isEmpty()){
            binding.confirmPasswordEditTxt.setError("Confirm Your Password");
            return false;
        }else{
            if(pass.equals(confirmPass)){
                return true;
            }else{
                binding.confirmPasswordEditTxt.setError("Your Password is not Matching");
                return false;
            }
        }
    }

    private void signUp(User user){
        progressDialog.show();
        viewModel.signUp(user);
    }
}