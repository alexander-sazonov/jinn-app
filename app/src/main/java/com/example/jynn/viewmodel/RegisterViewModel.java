package com.example.jynn.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.jynn.model.AuthAppRepository;
import com.google.firebase.auth.FirebaseUser;

public class RegisterViewModel extends AndroidViewModel {
    private AuthAppRepository authAppRepository;
    private MutableLiveData<FirebaseUser> userLiveData;
    public RegisterViewModel(@NonNull Application application) {
        super(application);
        authAppRepository = new AuthAppRepository(application);
        userLiveData = authAppRepository.getUserLiveData();

    }

    public void register(String name, String email, String password){
        authAppRepository.register(name, email, password);
    }

    public MutableLiveData<FirebaseUser> getUserLiveData() {
        return userLiveData;
    }
}

