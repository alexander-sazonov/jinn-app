package com.example.jynn.viewmodel;

import android.app.Application;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;


import com.example.jynn.model.AuthAppRepository;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewModel extends AndroidViewModel {
    private AuthAppRepository authAppRepository;
    private MutableLiveData<FirebaseUser> userLiveData;

    public LoginViewModel(@NonNull Application application){
        super(application);
        authAppRepository = new AuthAppRepository(application);
        userLiveData = authAppRepository.getUserLiveData();

    }


    public void login(String email, String password){
        authAppRepository.login(email,password);
    }



    public MutableLiveData<FirebaseUser> getUserLiveData(){
        return userLiveData;
    }
}
